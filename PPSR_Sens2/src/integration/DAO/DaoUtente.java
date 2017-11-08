package integration.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import business.entita.Utente;
import integration.DBConnector;

public class DaoUtente implements DAO<Utente> {
	private static final String VISUALIZZA_TUTTI_CLIENTI = "SELECT * FROM utenti WHERE admin = 0";// admin
																									// 0
																									// imposta
																									// cliente
	private static final String VISUALIZZA_TUTTI_DIPENDENTI = "SELECT * FROM utenti WHERE admin = 1";// admin
																										// 1
																										// imposta
																										// dipendente

	private static final String INSERISCI_QUERY = "INSERT INTO `test`.`utenti` (`Nome`, `Cognome`, `Email`, `Password`, `admin`, `Codice`) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String CERCA_QUERY = "SELECT * FROM utenti WHERE email = ?";
	private static final String ELIMINA_QUERY = "DELETE FROM utenti WHERE ID = ?";
	private static final String CODICI_QUERY = "SELECT Codice FROM `utenti` WHERE admin = 0";

	private PreparedStatement query = null;
	private ResultSet result;
	private int[] num = {1 , 2 , 3 , 4 , 5 , 6};

	public boolean inserisciDipendente(Utente u) {
		boolean ret = false;
		try {
			// imposta il valore di admin a livello dipendente
			String dipendente = "1";
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(INSERISCI_QUERY);
			query.setString(num[0], u.getNome());
			query.setString(num[1], u.getCognome());
			query.setString(num[2], u.getEmail());
			query.setString(num[3], u.getPassword());
			query.setString(num[4], dipendente);
			// modificato
			query.setString(num[5], null);
			query.execute();

			ret = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public boolean inserisci(Utente u) {
		boolean ret = false;
		try {

			// imposta il valore di admin a livello cliente
			String cliente = "0";
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(INSERISCI_QUERY);
			query.setString(num[0], u.getNome());
			query.setString(num[1], u.getCognome());
			query.setString(num[2], u.getEmail());
			query.setString(num[3], u.getPassword());
			query.setString(num[4], cliente);
			query.setString(num[5], u.getCodice());
			query.execute();

			ret = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public boolean elimina(Utente u) {
		Boolean ret = null;
		try {
			Connection conn = DBConnector.getConnector().getConnessione();

			query = conn.prepareStatement(ELIMINA_QUERY);
			query.setString(1, u.getID());
			query.execute();
			ret = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public List<Utente> visualizzaTutti() {
		List<Utente> ret = null;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(VISUALIZZA_TUTTI_CLIENTI);
			result = query.executeQuery();
			ret = creaLista(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public List<Utente> visualizzaTuttiDipendenti() {
		List<Utente> ret = null;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(VISUALIZZA_TUTTI_DIPENDENTI);
			result = query.executeQuery();
			ret = creaLista(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public List<Utente> cerca(String campo, String valore) {
		List<Utente> ret = null;
		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(CERCA_QUERY);
			query.setString(1, valore);
			result = query.executeQuery();
			ret = creaLista(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * Crea una lista a partire dal resultset
	 * 
	 * @param res
	 *            risultato della query
	 * @return lista generata
	 */
	private List<Utente> creaLista(ResultSet res) {
		List<Utente> lista = new LinkedList<Utente>();

		try {
			while (res.next()) {
				String ID = res.getString("ID");
				String nome = res.getString("Nome");
				String cognome = res.getString("Cognome");
				String email = res.getString("Email");
				String password = res.getString("Password");
				String admin = res.getString("Admin");
				String codice = res.getString("Codice");

				Utente utente = new Utente(ID, nome, cognome, email, password, admin, codice);

				lista.add(utente);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList creaListaCodici(ResultSet res) {
		ArrayList lista = new ArrayList();
		try {
			while (res.next()) {
				String codice = res.getString("Codice");
				lista.add(codice);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList visualizzaCodici() {
		ArrayList ret = null;
		ResultSet result;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(CODICI_QUERY);
			result = query.executeQuery();
			ret = creaListaCodici(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

}