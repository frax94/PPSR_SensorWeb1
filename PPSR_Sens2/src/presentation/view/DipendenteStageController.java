package presentation.view;

import java.io.IOException;
import java.util.List;

import business.entita.Utente;
import business.servizi.ServizioUtenti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author PPSR
 *
 */

public class DipendenteStageController {
	@FXML
	private TableView<Utente> tableDipendenti;
	private ObservableList<Utente> tableUtentiData = FXCollections.observableArrayList();
	
	public static Utente utenteSelezionato;

	public static final String NUOVO_DIPENDENTE;
	public static final String LISTA_DIPENDENTE;

	public static int funzione = 0; // 8 se elimina impianto, 9 modifica
									// impianto

	@FXML
	private AnchorPane content;
	@FXML
	private Button homeButton;
	@FXML
	private Button nuovoDipendenteButt;
	@FXML
	private Button eliminaDipendenteButt;
	@FXML
	private Button listaDipendentiButt;

	AnchorPane newLoadedPane;

	static {
		NUOVO_DIPENDENTE = "presentation/view/NuovoDipendente.fxml";
		LISTA_DIPENDENTE = "presentation/view/ListaDipendenti.fxml";
		utenteSelezionato = null;
	}

	@FXML
	public void initialize() {
		nuovoDipendenteButt.setOnAction((event) -> {
			// funzione = 7; non neccesaria
			goScene(NUOVO_DIPENDENTE);
		});

		// elimina dipendente
		eliminaDipendenteButt.setOnAction((event) -> {
			funzione = 8;
			goScene(LISTA_DIPENDENTE);
		});

		// visualizza dipendente
		listaDipendentiButt.setOnAction((event) -> {
			funzione = 9;
			goScene(LISTA_DIPENDENTE);
		});
	}

	@SuppressWarnings("unchecked")
	@FXML
	private void visual() {
		int [] minLarg = {30 , 100 , 200};
		final ServizioUtenti servizio = new ServizioUtenti();
		TableColumn<Utente, String> idCol = new TableColumn<Utente, String>("ID");
		idCol.setMinWidth(minLarg[0]);
		idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

		TableColumn<Utente, String> nomeCol = new TableColumn<Utente, String>("Nome");
		nomeCol.setMinWidth(minLarg[1]);
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn<Utente, String> cognomeCol = new TableColumn<Utente, String>("Cognome");
		cognomeCol.setMinWidth(minLarg[1]);
		cognomeCol.setCellValueFactory(new PropertyValueFactory<>("cognome"));

		TableColumn<Utente, String> emailCol = new TableColumn<Utente, String>("Email");
		emailCol.setMinWidth(minLarg[2]);
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableUtentiData = stampaLista(servizio.visualizzaTuttiDipendenti());

		tableDipendenti.setItems(tableUtentiData);
		tableDipendenti.getColumns().addAll(idCol, nomeCol, cognomeCol, emailCol);

	}

	public void goScene(String str) {
		System.out.println("Andrea Past ");
		try {

			newLoadedPane = FXMLLoader.load(getClass().getClassLoader().getResource(str));
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
	 * @author andrea
	 */
	private ObservableList<Utente> stampaLista(List<Utente> lista) {
		tableUtentiData.clear();
		for (Utente u : lista) {
			tableUtentiData.add(u);
		}
		return tableUtentiData;
	}

}