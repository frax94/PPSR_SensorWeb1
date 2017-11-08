package presentation.view;

import java.io.IOException;
import java.util.ArrayList;

import business.entita.Utente;
import business.servizi.ServizioUtenti;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author PPSR
 *
 */

public class CodiceClienteController {

	@FXML
	private AnchorPane content;
	@FXML
	private TableView<Utente> tableClienti;

	private AnchorPane newLoadedPane;

	@FXML
	private Button codiceButton;
	@FXML
	private TextField nomeField;

	public static int funzione;
	public static String codiceAdmin;

	private static final String LISTA_IMPIANTI_CODICE;

	static {
		LISTA_IMPIANTI_CODICE = "presentation/view/ListaImpiantiCodice.fxml";
		funzione = 0;
		codiceAdmin = "";
	}

	@FXML
	private void initialize() {

		codiceButton.setDisable(true);
		nomeField.requestFocus();

		nomeField.setOnKeyReleased((event) -> {
			if (nomeField.getText().isEmpty()) {
				codiceButton.setDisable(true);
			} else
				codiceButton.setDisable(false);

		});

		codiceButton.setOnAction((event) -> {
			ServizioUtenti su = new ServizioUtenti();
			@SuppressWarnings("rawtypes")
			ArrayList listaCodici = su.visualizzaCodici();
			String aContent = "";
			Alert alert;
			boolean trovato = false;
			codiceAdmin = nomeField.getText();
			for (int i = 0; i < listaCodici.size(); i++) {
				if (listaCodici.get(i).equals(codiceAdmin)) {
					trovato = true;
					break;
				} else
					trovato = false;
			}
			if (trovato) {
				aContent = "Codice inserito correttamente";
				alert = new Alert(AlertType.INFORMATION);
				funzione = 10;
				goScene(LISTA_IMPIANTI_CODICE);
				nomeField.clear();
			} else {
				aContent = "Codice Errato";
				alert = new Alert(AlertType.ERROR);
				nomeField.requestFocus();
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
			e.printStackTrace();
		}
	}

}
