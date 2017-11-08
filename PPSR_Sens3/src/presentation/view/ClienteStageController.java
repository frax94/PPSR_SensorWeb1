package presentation.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author PPSR
 *
 */

public class ClienteStageController {

	@FXML
	private AnchorPane content;
	@FXML
	private Button homeButton;
	@FXML
	private Button clienteButton;

	@FXML
	public void initialize() {
		AdminStageController.funzione = 1;

		homeButton.setOnAction((event) -> {
			goHome("presentation/view/ListaImpianti.fxml");
		});

		clienteButton.setOnAction((event) -> {
			goHome("presentation/view/CodiceCliente.fxml");
		});

		System.out.println("Andrea Past Impianto!");

		goHome("presentation/view/ListaImpianti.fxml");

	}

	private void goHome(String scene) {
		AnchorPane newLoadedPane;
		System.out.println("Andrea Past Impianto!");
		try {
			newLoadedPane = FXMLLoader.load(getClass().getClassLoader().getResource(scene));
			content.getChildren().clear();
			content.getChildren().add(newLoadedPane);
		} catch (IOException e) {
			System.out.println("Fallito carricamento schermata!");
			//e.printStackTrace();
		}
	}

}
