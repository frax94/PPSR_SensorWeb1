package presentation.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import business.entita.Impianto;
import business.entita.Sensore;
import business.entita.Tipo;
import business.servizi.ServizioSensori;
import business.servizi.ServizioTipi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author PPSR
 *
 */

public class ListaSensoriModificaController {

	private final ServizioSensori servizioS = new ServizioSensori();
	private final ServizioTipi servizioT = new ServizioTipi();

	private ObservableList<Sensore> tableSensoriData = FXCollections.observableArrayList();
	private ObservableList<Tipo> tableTipiData = FXCollections.observableArrayList();

	private Sensore sensoreSelezionato;

	@FXML
	private Button nuovoButton;
	private AnchorPane newLoadedPane;
	@FXML
	private AnchorPane content;
	@FXML
	private TableView<Sensore> tableSensori;
	@FXML
	private MenuButton menuFiltro;
	@FXML
	private Label nomeImpianto;
	@FXML
	private Button eliminaButton;

	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {

		// INSERISCI SENSORE
		nuovoButton.setOnAction((event) -> {
			goScene("presentation/view/NuovoSensore.fxml");
		});

		// ELIMINA SENSORE
		eliminaButton.setDisable(true);
		eliminaButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Avviso Eliminazione");
			alert.setHeaderText(null);
			alert.setContentText("Eliminare il sensore " + sensoreSelezionato.getModello() + " dell'impianto "
					+ ListaImpiantiController.impiantoSelezionato.getNomeI() + " del cliente "
					+ ListaImpiantiController.impiantoSelezionato.getNomeU() + " "
					+ ListaImpiantiController.impiantoSelezionato.getCognomeU() + " ?");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				System.out.println("Elimina: " + ListaImpiantiController.impiantoSelezionato.getIdI());
				boolean resQ = servizioS.elimina(sensoreSelezionato);

				Alert alert2;
				String aContent;

				if (resQ) {
					alert2 = new Alert(AlertType.INFORMATION);
					aContent = "Eliminazione sensore per l'impianto selezionato effettuata con successo!";

				} else {
					alert2 = new Alert(AlertType.ERROR);
					aContent = "Eliminazione sensore fallita!";
				}

				goScene("presentation/view/ListaSensoriModifica.fxml");

				alert2.setTitle("Avviso Eliminazione");
				alert2.setHeaderText(null);
				alert2.setContentText(aContent);
				alert2.showAndWait();

			}

		});

		tableSensori.setRowFactory(tv -> {
			TableRow<Sensore> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty()) {
					Sensore rowData = row.getItem();
					System.out.println(rowData.getModello());

					sensoreSelezionato = rowData;
					eliminaButton.setDisable(false);

				}
			});

			return row;
		});

		TableColumn<Sensore, String> modelloCol = new TableColumn<Sensore, String>("Modello");
		modelloCol.setMinWidth(30);
		modelloCol.setCellValueFactory(new PropertyValueFactory<>("modello"));

		TableColumn<Sensore, String> impiantoCol = new TableColumn<Sensore, String>("Tipo");
		impiantoCol.setMinWidth(50);
		impiantoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		TableColumn<Sensore, String> rilevazioneCol = new TableColumn<Sensore, String>("Rilevazione");
		rilevazioneCol.setMinWidth(150);
		rilevazioneCol.setCellValueFactory(new PropertyValueFactory<>("rilevazione"));

		tableSensori.getColumns().addAll(modelloCol, impiantoCol, rilevazioneCol);

		riempiTable(ListaImpiantiController.impiantoSelezionato.getI());

		nomeImpianto.setText(ListaImpiantiController.impiantoSelezionato.getI().getNome());

		tableTipiData = stampaListaT(servizioT.visualizzaTutti());

		Iterator<Tipo> it = tableTipiData.iterator();

		// FILTRI
		while (it.hasNext()) {
			MenuItem item = new MenuItem(it.next().getTipo());
			item.setOnAction(a -> { // lambda expression
				menuFiltro.setText(item.getText());

				tableSensori.getItems().clear();
				tableSensoriData = stampaListaS(
						servizioS.cerca(item.getText(), ListaImpiantiController.impiantoSelezionato.getI().getID()));
				eliminaButton.setDisable(true);
			});
			menuFiltro.getItems().add(item);
		}

		MenuItem tutti = new MenuItem("Tutti");
		tutti.setOnAction(a -> { // lambda expression
			menuFiltro.setText(tutti.getText());
			riempiTable(ListaImpiantiController.impiantoSelezionato.getI());
			eliminaButton.setDisable(true);
		});
		menuFiltro.getItems().add(tutti);

	}

	// METODI SERVIZIO
	private void riempiTable(Impianto i) {
		tableSensori.getItems().clear();

		tableSensoriData = stampaListaS(servizioS.cerca("", i.getID()));
		tableSensori.setItems(tableSensoriData);

	}

	private ObservableList<Sensore> stampaListaS(List<Sensore> lista) {
		tableSensoriData.clear();
		for (Sensore i : lista) {
			tableSensoriData.add(i);
		}
		return tableSensoriData;
	}

	private ObservableList<Tipo> stampaListaT(List<Tipo> lista) {
		tableTipiData.clear();
		for (Tipo i : lista) {
			tableTipiData.add(i);
		}
		return tableTipiData;
	}

	public void goScene(String scene) {
		System.out.println("Andrea Past " + scene);
		try {
			newLoadedPane = FXMLLoader.load(getClass().getClassLoader().getResource(scene));
			content.getChildren().clear();
			content.getChildren().add(newLoadedPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
