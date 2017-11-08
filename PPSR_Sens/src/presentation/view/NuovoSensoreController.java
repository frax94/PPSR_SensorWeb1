package presentation.view;

import java.util.Iterator;
import java.util.List;

import business.entita.ModelloSensore;
import business.entita.Sensore;
import business.entita.Tipo;
import business.servizi.ServizioModelli;
import business.servizi.ServizioSensori;
import business.servizi.ServizioTipi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
/**
 * 
 * @author PPSR
 *
 */

public class NuovoSensoreController {

	private final ServizioTipi servizioT = new ServizioTipi();
	private final ServizioModelli servizioM = new ServizioModelli();
	private final ServizioSensori servizioS = new ServizioSensori();

	private ObservableList<Tipo> tableTipiData = FXCollections.observableArrayList();
	private ObservableList<ModelloSensore> tableModelliData = FXCollections.observableArrayList();

	@FXML
	private Button okButton;
	@FXML
	private ComboBox<String> menuModello;
	@FXML
	private ComboBox<String> menuTipo;

	@FXML
	private void initialize() {

		okButton.setDisable(true);

		menuModello.setStyle("-fx-font-size : 14 pt;");
		menuTipo.setStyle("-fx-font-size : 14 pt;");

		tableTipiData = stampaListaT(servizioT.visualizzaTutti());

		Iterator<Tipo> itT = tableTipiData.iterator();

		while (itT.hasNext()) {
			String item = itT.next().getTipo();

			menuTipo.getItems().add(item);
		}

		menuModello.setDisable(true);

		menuTipo.setOnHidden((event) -> {

			if (!menuTipo.getSelectionModel().isEmpty()) {
				menuModello.getItems().clear();

				String tipo = menuTipo.getSelectionModel().getSelectedItem();
				System.out.println(tipo);
				tableModelliData = stampaListaM(servizioM.cerca("tipo", tipo));
				Iterator<ModelloSensore> itM = tableModelliData.iterator();

				while (itM.hasNext()) {
					String item = itM.next().getCodice();
					menuModello.getItems().add(item);
				}
			}

			menuModello.setDisable(false);
		});

		menuModello.setOnHidden((event) -> {
			if (checkSelection())
				okButton.setDisable(false);
			else
				okButton.setDisable(true);
		});

		okButton.setOnAction((event) -> {

			String modello = menuModello.getSelectionModel().getSelectedItem();
			String tipo = menuTipo.getSelectionModel().getSelectedItem();

			Sensore s = new Sensore(modello, ListaImpiantiController.impiantoSelezionato.getIdI(), tipo);
			servizioS.inserisci(s);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Avviso Inserimento");
			alert.setHeaderText(null);
			alert.setContentText("Inserimento nuovo sensore nell'impianto selezionato avvenuta con successo!");

			alert.showAndWait();

			menuModello.getSelectionModel().clearSelection();
			menuTipo.getSelectionModel().clearSelection();
		});

	}

	private ObservableList<Tipo> stampaListaT(List<Tipo> lista) {
		tableTipiData.clear();
		for (Tipo i : lista) {
			tableTipiData.add(i);
		}
		return tableTipiData;
	}

	private ObservableList<ModelloSensore> stampaListaM(List<ModelloSensore> lista) {
		tableModelliData.clear();
		for (ModelloSensore i : lista) {
			tableModelliData.add(i);
		}
		return tableModelliData;
	}

	private boolean checkSelection() {
		if (menuModello.getSelectionModel().getSelectedItem() != null
				&& menuTipo.getSelectionModel().getSelectedItem() != null)
			return true;
		else
			return false;
	}

}
