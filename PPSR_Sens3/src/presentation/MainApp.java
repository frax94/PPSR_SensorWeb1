package presentation;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * 
 * @author PPSR
 *
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primeStage) {
		this.primaryStage = primeStage;
		this.primaryStage.setTitle("PPSR_Sensor");
		primaryStage.setResizable(false);

		initRootLayout();

		showLogin();
	}

	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Connessione Fallita!");
			//e.printStackTrace();
		}
	}

	/*
	 * Shows the login inside the root layout.
	 */
	public void showLogin() {
		try {
			// Load login.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
			AnchorPane login = (AnchorPane) loader.load();

			// Set login into the center of root layout.
			rootLayout.setCenter(login);
		} catch (IOException e) {

			System.out.println("Connessione Fallita!");
			//e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return primaryStage
	 * @author andrea
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
