package integration.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.entita.Impianto;
import business.entita.Utente;
import integration.DBConnector;

/**
 * @author Andrea
 *
 */
public class DaoImpianto implements DAO<Impianto> {

	private static final String VISUALIZZA_TUTTI_QUERY = "SELECT impianti.*, utenti.nome AS nome_cliente, utenti.cognome AS cognome_cliente FROM impianti, utenti WHERE impianti.Cliente = utenti.ID";
	private static final String VISUALIZZA_IMPIANTO_CLIENTE2_QUERY = "SELECT impianti.*, utenti.nome AS nome_cliente, utenti.cognome AS cognome_cliente FROM impianti, utenti WHERE impianti.Cliente = utenti.ID AND Codice = ?";
	private static final String INSERISCI_QUERY = "INSERT INTO `test`.`impianti` (`Nome`, `Cliente`) VALUES (?, ?);";
	private static final String ELIMINA_QUERY = "DELETE FROM `test`.`impianti` WHERE `ID`= ?";
	private static final String CERCA_QUERY = "SELECT impianti.*, utenti.nome AS nome_cliente, utenti.cognome AS cognome_cliente FROM impianti, utenti WHERE impianti.Cliente = utenti.ID AND Cliente = ?";

	private PreparedStatement query = null;

	@Override
	public boolean inserisci(Impianto i) {
		Boolean ret = false;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();

			query = conn.prepareStatement(INSERISCI_QUERY);
			query.setString(1, i.getNome());
			query.setString(2, i.getCliente());

			query.execute();
			ret = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public boolean elimina(Impianto i) {
		Boolean ret = null;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();

			query = conn.prepareStatement(ELIMINA_QUERY);
			query.setString(1, i.getID());
			query.execute();
			ret = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public List<Impianto> visualizzaTutti() {
		List<Impianto> ret = null;
		ResultSet result;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(VISUALIZZA_TUTTI_QUERY);
			result = query.executeQuery();
			ret = creaLista(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public List<Impianto> cerca(String campo, String valore) {
		List<Impianto> ret = null;
		ResultSet result;

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

	private List<Impianto> creaLista(ResultSet res) {
		List<Impianto> lista = new LinkedList<Impianto>();

		try {
			while (res.next()) {

				String ID = res.getString("ID");
				String nome = res.getString("Nome");
				String cliente = res.getString("Cliente");

				Impianto impianto = new Impianto(ID, nome, cliente);

				lista.add(impianto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public class coppia {
		coppia(Impianto i, Utente u) {
			this.i = i;
			this.u = u;
			idI = i.getID();
			nomeI = i.getNome();
			idU = u.getID();
			nomeU = u.getNome();
			cognomeU = u.getCognome();
		}

		Impianto i;
		Utente u;
		String idI;
		String nomeI;
		String idU;
		String nomeU;
		String cognomeU;

		public Impianto getI() {
			return i;
		}

		public Utente getU() {
			return u;
		}

		public String getIdI() {
			return idI;
		}

		public String getNomeI() {
			return nomeI;
		}

		public String getIdU() {
			return idU;
		}

		public String getNomeU() {
			return nomeU;
		}

		public String getCognomeU() {
			return cognomeU;
		}
	}

	/**
	 * Crea una lista a partire dal resultset
	 * 
	 * @param res
	 *            risultato della query
	 * @return lista generata
	 * @author andrea
	 */
	private List<coppia> creaListaC(ResultSet res) {
		List<coppia> lista = new LinkedList<coppia>();

		try {
			while (res.next()) {

				String ID = res.getString("ID");
				String nome = res.getString("Nome");
				String cliente = res.getString("Cliente");
				String nomeC = res.getString("nome_cliente");
				String cognomeC = res.getString("cognome_cliente");

				Impianto impianto = new Impianto(ID, nome, cliente);
				//se da problemi futuri controllare virgolette finali 
				Utente utente = new Utente(cliente, nomeC, cognomeC, "", "", "0", "");// 0
																					// imposta
																					// l'untente
																					// come
																					// cliente
				coppia c = new coppia(impianto, utente);

				lista.add(c);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<coppia> cercaC(String valore) {
		List<coppia> ret = null;
		ResultSet result;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(CERCA_QUERY);
			query.setString(1, valore);
			result = query.executeQuery();
			ret = creaListaC(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public List<coppia> visualizzaTuttiC() {
		List<coppia> ret = null;
		ResultSet result;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(VISUALIZZA_TUTTI_QUERY);
			result = query.executeQuery();
			ret = creaListaC(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	// VISUALIZZA IMPIANTI DELL'ALTRO CLIENTE
	public List<coppia> visualizza(String codice) {
		List<coppia> ret = null;
		ResultSet result;

		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(VISUALIZZA_IMPIANTO_CLIENTE2_QUERY);
			query.setString(1, codice);
			result = query.executeQuery();
			ret = creaListaC(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

}
