package presentation.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import business.entita.ModelloSensore;
import business.entita.Tipo;
import business.servizi.ServizioModelli;
import business.servizi.ServizioTipi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author PPSR
 *
 */

public class NuovoModelloController {

	@FXML
	private TextField codiceField;
	@FXML
	private TextField marcaField;
	@FXML
	private ComboBox<String> tipoBox;
	@FXML
	private Button okButton;
	@FXML
	private AnchorPane content;
	private AnchorPane newLoadedPane;

	private ServizioTipi servizioT = new ServizioTipi();
	private ServizioModelli servizioM = new ServizioModelli();

	private ObservableList<Tipo> tableTipiData = FXCollections.observableArrayList();

	@FXML
	private void initialize() {

		okButton.setDisable(true);

		tipoBox.setStyle("-fx-font-size : 14 pt;");

		tableTipiData = stampaListaT(servizioT.visualizzaTutti());

		Iterator<Tipo> itT = tableTipiData.iterator();

		while (itT.hasNext()) {
			String item = itT.next().getTipo();
			tipoBox.getItems().add(item);
		}

		content.setOnKeyReleased((event) -> {
			// Controlla che la stringa inizia con T U P che sono i tipi e che
			// sia di lunghezza corretta la stringa contiene tipo e marca
			if (codiceField.getText().length() != 5 || !codiceField.getText().substring(0, 1).equals("P")
					&& !codiceField.getText().substring(0, 1).equals("T")
					&& !codiceField.getText().substring(0, 1).equals("U")) {
				codiceField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
			}else {
				codiceField.setStyle("-fx-border-color: null");
			}
			if (checkSelection()) {
				okButton.setDisable(false);
			}else {
				okButton.setDisable(true);
			}
		});

		tipoBox.setOnHidden((event) -> {
			if (checkSelection()) {
				okButton.setDisable(false);
			}else {
				okButton.setDisable(true);
			}
		});

		okButton.setOnAction((event) -> {
			String identificativo = codiceField.getText();
			String tipo = tipoBox.getSelectionModel().getSelectedItem();
			String marca = marcaField.getText();

			ModelloSensore m = new ModelloSensore(identificativo, tipo, marca);

			boolean result = servizioM.inserisci(m);

			String aContent = "";
			Alert alert;

			if (result) {
				aContent = "Inserimento nuovo modello sensore avvenuto con successo!";
				alert = new Alert(AlertType.INFORMATION);
				goScene(AdminStageController.AGGIORNA_DB);
			} else {
				aContent = "Inserimento nuovo modello sensore fallito! \nCodice modello già presente nel sistema.";
				alert = new Alert(AlertType.ERROR);
				codiceField.requestFocus();
			}

			alert.setTitle("Avviso Inserimento");
			alert.setHeaderText(null);
			alert.setContentText(aContent);
			alert.showAndWait();

		});

	}

	public void goScene(String scene) {
		System.out.println("Andrea Past " + scene);
		try {
			newLoadedPane = FXMLLoader.load(getClass().getClassLoader().getResource(scene));
			content.getChildren().clear();
			content.getChildren().add(newLoadedPane);
		} catch (IOException e) {
			System.out.println("Fallito caricamento schermata!");
			//e.printStackTrace();
		}
	}

	private ObservableList<Tipo> stampaListaT(List<Tipo> lista) {
		tableTipiData.clear();
		for (Tipo i : lista) {
			tableTipiData.add(i);
		}
		return tableTipiData;
	}

	private boolean checkSelection() {
		if (codiceField.getText().length() == 5 && !marcaField.getText().isEmpty()
				&& tipoBox.getSelectionModel().getSelectedItem() != null) {
			if (codiceField.getText().substring(0, 1).equals("P") || codiceField.getText().substring(0, 1).equals("T")
					|| codiceField.getText().substring(0, 1).equals("U")) {
				return true;
			}
		}
		return false;
	}

}
