package presentation.view;

import java.io.IOException;
import java.util.List;

import business.servizi.ServizioImpianti;
import integration.DAO.DaoImpianto.coppia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
/**
 * 
 * @author PPSR
 *
 */

public class ListaImpiantiCodiceController {

	private final ServizioImpianti servizio = new ServizioImpianti();

	private ObservableList<coppia> tableImpiantiData = FXCollections.observableArrayList();

	public static coppia impiantoSelezionato;

	public static final String LISTA_SENSORI_CODICE;

	static {
		impiantoSelezionato = null;
		LISTA_SENSORI_CODICE = "presentation/view/ListaSensoriCodice.fxml";
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

			tableImpianti.setItems(tableImpiantiData);

			tableImpianti.setRowFactory(tv -> {
				TableRow<coppia> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (!row.isEmpty()) {
						coppia rowData = row.getItem();
						impiantoSelezionato = rowData;
						System.out.println(rowData.getI().getNome());
						goScene(LISTA_SENSORI_CODICE);
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
