package presentation.view;

import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import business.entita.Utente;
import business.servizi.ServizioUtenti;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author PPSR
 *
 */

public class NuovoClienteController {

	@FXML
	TextField nomeField;
	@FXML
	TextField cognomeField;
	@FXML
	TextField emailField;
	@FXML
	private PasswordField passField;
	@FXML
	private PasswordField ripPassField;
	@FXML
	private Button okButton;

	@FXML
	private AnchorPane content;

	ServizioUtenti servizioU = new ServizioUtenti();

	@FXML
	private void initialize() throws SQLException {

		okButton.setDisable(true);
		nomeField.requestFocus();

		content.setOnKeyReleased((event) -> {
			if (checkNotEmpty() && checkEmail() && checkPass()) {
				okButton.setDisable(false);
			} else
				okButton.setDisable(true);

			if (!checkEmail() && !emailField.getText().isEmpty())
				emailField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
			else
				emailField.setStyle("-fx-border-color: null");
			if (!checkPass() && !ripPassField.getText().isEmpty())
				ripPassField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
			else
				ripPassField.setStyle("-fx-border-color: null");

		});

		okButton.setOnAction((event) -> {
			String cliente = "0";
			String codice = nomeField.getText() + cognomeField.getText();
			char[] caratteriCod = codice.toLowerCase().toCharArray();
			codice = nuovoCodice(caratteriCod);
			// modifica aggiungendo il valore 0 per identificarlo come cliente

			Utente newUtente = new Utente(nomeField.getText(), cognomeField.getText(), emailField.getText(),
					passField.getText(), cliente, codice);
			boolean result = servizioU.inserisci(newUtente);

			Alert alert;
			String aTitle = "";
			String aContent = "Avviso Inserimento";

			if (result) {

				aContent = "Inserimento nuovo utente avvenuto con successo!";
				alert = new Alert(AlertType.INFORMATION);

				nomeField.clear();
				cognomeField.clear();
				emailField.clear();
				passField.clear();
				ripPassField.clear();

			} else {

				aContent = "Inserimento nuovo utente fallito! \nEmail già presente nel sistema.";
				alert = new Alert(AlertType.ERROR);

				emailField.requestFocus();
			}

			alert.setTitle(aTitle);
			alert.setHeaderText(null);
			alert.setContentText(aContent);
			alert.showAndWait();

		});

	}

	// crea il codice che viene passato tra i clienti
	private String nuovoCodice(char[] str) {
		Random pos = new Random();
		String codice = "";
		int tempPos;
		for (int i = 0; i < str.length; i++) {
			tempPos = pos.nextInt(str.length);
			codice += str[tempPos];
		}
		return codice;
	}

	private boolean checkNotEmpty() {
		if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() || emailField.getText().isEmpty()
				|| passField.getText().isEmpty() || ripPassField.getText().isEmpty())
			return false;
		else
			return true;
	}

	private boolean checkEmail() {
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(emailField.getText());

		if (matcher.matches())
			return true;
		else
			return false;
	}

	private boolean checkPass() {
		if (passField.getText().length() >= 4 && passField.getText().equals(ripPassField.getText()))
			return true;
		else
			return false;
	}

}
