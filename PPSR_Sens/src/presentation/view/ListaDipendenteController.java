package presentation.view;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import business.entita.Utente;
import business.servizi.ServizioUtenti;
//import integration.DAO.DaoImpianto.coppia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
/**
 * 
 * @author PPSR
 *
 */

public class ListaDipendenteController {

	@FXML
	private Text textImpianti;
	@FXML
	private AnchorPane content;
	@FXML
	private TableView<Utente> tableClienti;

	private AnchorPane newLoadedPane;

	private final ServizioUtenti servizio = new ServizioUtenti();

	private ObservableList<Utente> tableUtentiData = FXCollections.observableArrayList();

	public static Utente utenteSelezionato;

	static {
		utenteSelezionato = null;
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {

		if (DipendenteStageController.funzione == 9)
			tableClienti.setRowFactory(tv -> {
				TableRow<Utente> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (!row.isEmpty()) {
						Utente rowData = row.getItem();
						utenteSelezionato = rowData;
						System.out.println(rowData.getNome());
						goScene(DipendenteStageController.LISTA_DIPENDENTE);
					}
				});

				return row;
			});

		// aggiunta elimina cliente
		else if (DipendenteStageController.funzione == 8) {
			tableClienti.setRowFactory(tv -> {
				TableRow<Utente> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (!row.isEmpty()) {
						Utente rowData = row.getItem();
						utenteSelezionato = rowData;
						System.out.println(rowData.getID());
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Avviso Eliminazione");
						alert.setHeaderText(null);
						alert.setContentText("Eliminare il cliente " + utenteSelezionato.getNome() + " "
								+ utenteSelezionato.getCognome() + " ?");
						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == ButtonType.OK) {
							servizio.elimina(utenteSelezionato);
						} else
							goScene(DipendenteStageController.LISTA_DIPENDENTE);

						goScene(DipendenteStageController.LISTA_DIPENDENTE);
					}
				});

				return row;
			});
		}

		TableColumn<Utente, String> idCol = new TableColumn<Utente, String>("ID");
		idCol.setMinWidth(30);
		idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

		TableColumn<Utente, String> nomeCol = new TableColumn<Utente, String>("Nome");
		nomeCol.setMinWidth(100);
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn<Utente, String> cognomeCol = new TableColumn<Utente, String>("Cognome");
		cognomeCol.setMinWidth(100);
		cognomeCol.setCellValueFactory(new PropertyValueFactory<>("cognome"));

		TableColumn<Utente, String> emailCol = new TableColumn<Utente, String>("Email");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableUtentiData = stampaLista(servizio.visualizzaTuttiDipendenti());

		tableClienti.setItems(tableUtentiData);
		tableClienti.getColumns().addAll(idCol, nomeCol, cognomeCol, emailCol);

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

	/**
	 * Trasforma la lista in una observable list da mostrare nella tabella
	 * 
	 * @param lista
	 *            lista contenente il resultset
	 * @return observable list da mostrare
	 */
	private ObservableList<Utente> stampaLista(List<Utente> lista) {
		tableUtentiData.clear();
		for (Utente u : lista) {
			tableUtentiData.add(u);
		}
		return tableUtentiData;
	}

}
