package presentation.view;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import business.entita.Utente;
import business.servizi.ServizioImpianti;
import business.servizi.ServizioLogin;
import integration.DAO.DaoImpianto.coppia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
/**
 * 
 * @author PPSR
 *
 */

public class ListaImpiantiController {

	private final ServizioImpianti servizio = new ServizioImpianti();

	private ObservableList<coppia> tableImpiantiData = FXCollections.observableArrayList();

	public static coppia impiantoSelezionato;

	static {
		impiantoSelezionato = null;
	}

	@FXML
	private AnchorPane content;
	@FXML
	private TableView<coppia> tableImpianti;
	private AnchorPane newLoadedPane;
	@FXML
	private Text textImpianti;

	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {

		TableColumn<coppia, String> nomeCol = new TableColumn<coppia, String>("Nome Impianto");
		nomeCol.setMinWidth(100);
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nomeI"));

		TableColumn<coppia, String> nomeCCol = new TableColumn<coppia, String>("Nome Cliente");
		nomeCCol.setMinWidth(100);
		nomeCCol.setCellValueFactory(new PropertyValueFactory<>("nomeU"));

		TableColumn<coppia, String> cognomeCCol = new TableColumn<coppia, String>("Cognome Cliente");
		cognomeCCol.setMinWidth(100);
		cognomeCCol.setCellValueFactory(new PropertyValueFactory<>("cognomeU"));

		tableImpianti.getColumns().addAll(nomeCol, nomeCCol, cognomeCCol);

		if (CodiceClienteController.funzione == 10) {
			tableImpiantiData = stampaLista(servizio.visualizza(CodiceClienteController.codiceAdmin));
		}

		if (AdminStageController.funzione == 1) {

			Utente utente = ServizioLogin.getUtenteLoggato();
			if (utente.getAdmin().equals("1")) {
				tableImpiantiData = stampaLista(servizio.visualizzaTuttiC());
			} else {
				tableImpiantiData = stampaLista(servizio.cercaC(utente.getID()));
			}

			tableImpianti.setItems(tableImpiantiData);

			tableImpianti.setRowFactory(tv -> {
				TableRow<coppia> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (!row.isEmpty()) {
						coppia rowData = row.getItem();
						impiantoSelezionato = rowData;
						System.out.println(rowData.getI().getNome());
						goScene(AdminStageController.LISTA_SENSORI);
					}
				});

				return row;
			});
		}

		// MODIFICA
		else if (AdminStageController.funzione == 4) {

			System.out.println("impianti " + ListaClientiController.utenteSelezionato.getNome());
			System.out.println("funzione: " + AdminStageController.funzione);

			tableImpiantiData = stampaLista(servizio.cercaC(ListaClientiController.utenteSelezionato.getID()));

			textImpianti.setText(ListaClientiController.utenteSelezionato.getNome() + " "
					+ ListaClientiController.utenteSelezionato.getCognome());

			tableImpianti.setItems(tableImpiantiData);

			tableImpianti.setRowFactory(tv -> {
				TableRow<coppia> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (!row.isEmpty()) {
						coppia rowData = row.getItem();
						impiantoSelezionato = rowData;
						System.out.println(rowData.getI().getNome());
						goScene(AdminStageController.MODIFICA_IMPIANTO);
					}
				});

				return row;
			});

			// ELIMINA
		} else if (AdminStageController.funzione == 3) {

			System.out.println("impianti " + ListaClientiController.utenteSelezionato.getNome());
			System.out.println("funzione: " + AdminStageController.funzione);

			tableImpiantiData = stampaLista(servizio.cercaC(ListaClientiController.utenteSelezionato.getID()));

			textImpianti.setText(ListaClientiController.utenteSelezionato.getNome() + " "
					+ ListaClientiController.utenteSelezionato.getCognome());

			tableImpianti.setItems(tableImpiantiData);

			tableImpianti.setRowFactory(tv -> {
				TableRow<coppia> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (!row.isEmpty()) {
						coppia rowData = row.getItem();
						impiantoSelezionato = rowData;
						System.out.println(rowData.getI().getNome());

						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Avviso Eliminazione");
						alert.setHeaderText(null);
						alert.setContentText("Eliminare l'impianto " + impiantoSelezionato.getNomeI() + " dell'utente "
								+ impiantoSelezionato.getNomeU() + " " + impiantoSelezionato.getCognomeU() + "?");

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == ButtonType.OK) {
							System.out.println("Elimina: " + impiantoSelezionato.getIdI());
							boolean resQ = servizio.elimina(impiantoSelezionato.getI());

							Alert alert2;
							String aContent;

							if (resQ) {
								alert2 = new Alert(AlertType.INFORMATION);
								aContent = "Eliminazione impianto per l'utente selezionato effettuata con successo!";

							} else {
								alert2 = new Alert(AlertType.ERROR);
								aContent = "Eliminazione impianto fallita!";
							}

							goScene(AdminStageController.LISTA_IMPIANTI);

							alert2.setTitle("Avviso Eliminazione");
							alert2.setHeaderText(null);
							alert2.setContentText(aContent);
							alert2.showAndWait();

						}
					}
				});

				return row;
			});
		}
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

	/**
	 * Trasforma la lista in una observable list da mostrare nella tabella
	 * 
	 * @param lista
	 *            lista contenente il resultset
	 * @return observable list da mostrare
	 * @author andrea
	 */
	private ObservableList<coppia> stampaLista(List<coppia> lista) {
		tableImpiantiData.clear();
		for (coppia i : lista) {
			tableImpiantiData.add(i);
		}
		return tableImpiantiData;
	}

}
