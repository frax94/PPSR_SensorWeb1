package integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe (Pattern: singleton) per la connessione ad un DB
 * 
 * @author Andrea
 *
 */
public class DBConnector {

	/**
	 * Connessione a MySQL (sessione)
	 * 
	 * @author andrea
	 */
	private Connection connessione = null;

	/**
	 * Istanza della classe ConnettoreMySQL
	 */
	private static final DBConnector ISTANZA = new DBConnector();

	/**
	 * Costruttore privato per la classe, contentente le credenziali di accesso
	 * al server
	 */
	private DBConnector() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connessione = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root");

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Database NOT Connected!");
		} catch (ClassNotFoundException e) {

		}

	}

	/**
	 * @return Oggetto Connection: connessione a MySql
	 * @author andrea
	 */
	public Connection getConnessione() {
		return connessione;
	}

	/**
	 * @param connessione
	 *            La connessione da impostare
	 * @author andrea
	 */

	public void setConnessione(Connection connessione) {
		this.connessione = connessione;
	}

	/**
	 * Metodo per accedere al singleton
	 * 
	 * @return Unica istanza della classe ConnettoreMySQL
	 * @author andrea
	 */

	public static DBConnector getConnector() {
		return ISTANZA;
	}
}
