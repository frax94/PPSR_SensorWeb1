package presentation.view;

import java.util.Iterator;
import java.util.List;

import business.entita.Impianto;
import business.entita.Sensore;
import business.entita.Tipo;
import business.servizi.ServizioSensori;
import business.servizi.ServizioTipi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author PPSR
 *
 */

public class ListaSensoriController {

	private final ServizioSensori servizioS = new ServizioSensori();
	private final ServizioTipi servizioT = new ServizioTipi();

	private ObservableList<Sensore> tableSensoriData = FXCollections.observableArrayList();
	private ObservableList<Tipo> tableTipiData = FXCollections.observableArrayList();

	@FXML
	private AnchorPane content;
	@FXML
	private TableView<Sensore> tableSensori;
	@FXML
	private Label nomeImpianto;
	@FXML
	private MenuButton menuFiltro;

	@SuppressWarnings("unchecked")
	public void initialize() {

		TableColumn<Sensore, String> modelloCol = new TableColumn<Sensore, String>("Modello");
		modelloCol.setMinWidth(30);
		modelloCol.setCellValueFactory(new PropertyValueFactory<>("modello"));

		TableColumn<Sensore, String> impiantoCol = new TableColumn<Sensore, String>("Tipo");
		impiantoCol.setMinWidth(50);
		impiantoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		TableColumn<Sensore, String> rilevazioneCol = new TableColumn<Sensore, String>("Rilevazione");
		rilevazioneCol.setMinWidth(150);
		rilevazioneCol.setCellValueFactory(new PropertyValueFactory<>("rilevazione"));
		tableSensori.getColumns().addAll(modelloCol, impiantoCol, rilevazioneCol);

		riempiTable(ListaImpiantiController.impiantoSelezionato.getI());

		nomeImpianto.setText(ListaImpiantiController.impiantoSelezionato.getI().getNome());

		tableTipiData = stampaListaT(servizioT.visualizzaTutti());

		Iterator<Tipo> it = tableTipiData.iterator();

		while (it.hasNext()) {
			MenuItem item = new MenuItem(it.next().getTipo());
			item.setOnAction(a -> { // lambda expression
				menuFiltro.setText(item.getText());

				tableSensori.getItems().clear();
				tableSensoriData = stampaListaS(
						servizioS.cerca(item.getText(), ListaImpiantiController.impiantoSelezionato.getI().getID()));
			});
			menuFiltro.getItems().add(item);
		}

		MenuItem tutti = new MenuItem("Tutti");
		tutti.setOnAction(a -> { // lambda expression
			menuFiltro.setText(tutti.getText());
			riempiTable(ListaImpiantiController.impiantoSelezionato.getI());
		});
		menuFiltro.getItems().add(tutti);
	}

	/**
	 * Trasforma la lista in una observable list da mostrare nella tabella
	 * 
	 * @param lista
	 *            contenente il resultset
	 * @return observable list da mostrare
	 */
	private ObservableList<Sensore> stampaListaS(List<Sensore> lista) {
		tableSensoriData.clear();
		for (Sensore i : lista) {
			tableSensoriData.add(i);
		}
		return tableSensoriData;
	}

	/**
	 * Trasforma la lista in una observable list da mostrare nella tabella
	 * 
	 * @param lista
	 *            lista contenente il resultset
	 * @return observable list da mostrare
	 */
	private ObservableList<Tipo> stampaListaT(List<Tipo> lista) {
		tableTipiData.clear();
		for (Tipo i : lista) {
			tableTipiData.add(i);
		}
		return tableTipiData;
	}

	private void riempiTable(Impianto i) {
		tableSensori.getItems().clear();

		tableSensoriData = stampaListaS(servizioS.cerca("", i.getID()));
		tableSensori.setItems(tableSensoriData);

	}

}
