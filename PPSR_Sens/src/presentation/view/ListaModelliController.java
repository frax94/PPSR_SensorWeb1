package presentation.view;

import java.io.IOException;
import java.util.List;

import business.entita.ModelloSensore;
import business.servizi.ServizioModelli;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class ListaModelliController {

	private final ServizioModelli servizioM = new ServizioModelli();

	private ObservableList<ModelloSensore> tableModelliData = FXCollections.observableArrayList();

	private ModelloSensore modelloSelezionato;

	@FXML
	private Button nuovoModelloButton;
	@FXML
	private Button eliminaModelloButton;
	@FXML
	private TableView<ModelloSensore> tableModelli;

	private AnchorPane newLoadedPane;
	@FXML
	private AnchorPane content;

	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {

		nuovoModelloButton.setOnAction((event) -> {
			goScene("presentation/view/NuovoModello.fxml");
		});

		eliminaModelloButton.setDisable(true);
		eliminaModelloButton.setOnAction((event) -> {

			boolean result = servizioM.elimina(modelloSelezionato);

			String aContent = "";
			Alert alert;

			if (result) {
				aContent = "Eliminazione modello sensore selezionato avvenuta con successo!";
				alert = new Alert(AlertType.INFORMATION);
				goScene(AdminStageController.AGGIORNA_DB);
			} else {
				aContent = "Eliminazione modello sensore selezionato fallita!";
				alert = new Alert(AlertType.ERROR);

			}

			alert.setTitle("Avviso Eliminazione");
			alert.setHeaderText(null);
			alert.setContentText(aContent);
			alert.showAndWait();

		});

		tableModelli.setRowFactory(tv -> {
			TableRow<ModelloSensore> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty()) {
					ModelloSensore rowData = row.getItem();
					modelloSelezionato = rowData;
					System.out.println(rowData.getCodice());
					eliminaModelloButton.setDisable(false);
				}
			});

			return row;
		});

		TableColumn<ModelloSensore, String> idCol = new TableColumn<ModelloSensore, String>("Identificativo");
		idCol.setMinWidth(30);
		idCol.setCellValueFactory(new PropertyValueFactory<>("Codice"));

		TableColumn<ModelloSensore, String> nomeCol = new TableColumn<ModelloSensore, String>("Tipo");
		nomeCol.setMinWidth(100);
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("Tipo"));

		TableColumn<ModelloSensore, String> cognomeCol = new TableColumn<ModelloSensore, String>("Marca");
		cognomeCol.setMinWidth(100);
		cognomeCol.setCellValueFactory(new PropertyValueFactory<>("Marca"));

		tableModelliData = stampaLista(servizioM.visualizzaTutti());

		tableModelli.setItems(tableModelliData);
		tableModelli.getColumns().addAll(idCol, nomeCol, cognomeCol);

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

	private ObservableList<ModelloSensore> stampaLista(List<ModelloSensore> lista) {
		tableModelliData.clear();
		for (ModelloSensore m : lista) {
			tableModelliData.add(m);
		}
		return tableModelliData;
	}

}
