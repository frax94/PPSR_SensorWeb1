package presentation.view;

import java.sql.SQLException;
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

public class NuovoDipendenteController {

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
			} else {
				okButton.setDisable(true);
			}
			if (!checkEmail() && !emailField.getText().isEmpty()) {
				emailField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
			} else {
				emailField.setStyle("-fx-border-color: null");
			}
			if (!checkPass() && !ripPassField.getText().isEmpty()) {
				ripPassField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
			} else {
				ripPassField.setStyle("-fx-border-color: null");
			}

		});

		okButton.setOnAction((event) -> {

			String dipendente = "1";
			// modifica aggiungendo il valore 1 per identificarlo come
			// dipendente
			Utente newUtente = new Utente(nomeField.getText(), cognomeField.getText(), emailField.getText(),
					passField.getText(), dipendente);

			boolean result = servizioU.inserisciDipendente(newUtente);

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

	private boolean checkNotEmpty() {
		boolean check;
		if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() || emailField.getText().isEmpty()
				|| passField.getText().isEmpty() || ripPassField.getText().isEmpty()) {
			check = false;
		} else {
			check = true;
		}
		return check;
	}

	private boolean checkEmail() {
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(emailField.getText());
		boolean check;
		if (matcher.matches()) {
			check = true;
		} else {
			check = false;
		}
		return check;
	}

	private boolean checkPass() {
		boolean check;
		if (passField.getText().length() >= 4 && passField.getText().equals(ripPassField.getText())) {
			check = true;
		} else {
			check = false;
		}
		return check;
	}

}
