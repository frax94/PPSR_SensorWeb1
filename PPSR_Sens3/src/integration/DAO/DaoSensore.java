package integration.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import business.entita.Sensore;
import integration.DBConnector;
/**
 * 
 * @author PPSR
 *
 */

public class DaoSensore implements DAO<Sensore> {

	private static final String INSERISCI_QUERY = "INSERT INTO sensori (modello, Impianto,rilevazione) VALUES (?,?,?)";
	private static final String ELIMINA_QUERY = "DELETE FROM sensori WHERE ID = ?";
	private static final String CERCA_QUERY = "SELECT sensori.*, modellisensori.Tipo FROM sensori, modellisensori WHERE sensori.modello = modellisensori.Codice AND impianto = ? AND tipo LIKE ?";
	private PreparedStatement query = null;
	private ResultSet result;

	@Override
	public boolean inserisci(Sensore s) {
		boolean ret = false;
		// preparo la stringa sensore
		String sensore = s.getModello() + data(s) + valoreSensore(s) + ora(s) + statoStringa(s);
		try {
			// oggetto connessione
			Connection conn = DBConnector.getConnector().getConnessione();
			// preparo la query
			query = conn.prepareStatement(INSERISCI_QUERY);
			query.setString(1, s.getModello());
			query.setString(2, s.getImpianto());
			query.setString(3, sensore); // Es:(P/U/T=tipo) (marca) (anno)
											// (mese) (giorno) (nel caso
											// di segno ci sara 0 positivo 1
											// negativo) (valore) (ore)
											// (minuti) (SUC/ERR/ANM=stato del
											// sensore)

			query.execute();
			ret = true;
		} catch (SQLException e) {

			System.out.println("Connessione Fallita!");
			//e.printStackTrace();
		}
		return ret;
	}

	// metodi di formazione della stringa di rilevazione
	private String data(Sensore s) {
		Date data = new Date();
		SimpleDateFormat dateForm = new SimpleDateFormat("ddMMyyyy");
		return dateForm.format(data);
	}

	private String ora(Sensore s) {
		Date data = new Date();
		SimpleDateFormat dateForm = new SimpleDateFormat("HHmm");
		return dateForm.format(data);
	}

	private String statoStringa(Sensore s) {
		String str = null;
		String[] stato = { "ANM", "SUC", "ERR" };// array che contiene le string
													// di annomalia successo o
													// errore
		Random rad = new Random();
		int posStato = rad.nextInt(3); // posizione ottenuta in maniera random
										// per scegliere lo stato del sensore
		str = stato[posStato];// stato del sensore
		return str;
	}

	private String valoreSensore(Sensore s) {
		int strinTipo = 0;
		Random rad = new Random();
		String str = "";
		int segno; // se è 0 valore positivo se e 1 valore negativo
		if (s.getTipo().equalsIgnoreCase("Pressione")) { // pressione stanza
															// normale
			strinTipo = 1000 + rad.nextInt(20);
			str += strinTipo;
		} else if (s.getTipo().equalsIgnoreCase("Temperatura")) {
			strinTipo = 0;
			strinTipo = rad.nextInt(45);// rimettere 45
			if (strinTipo >= 0 && strinTipo <= 9) {
				str = "0" + strinTipo;
			} else {
				str = Integer.toString(strinTipo);
			}
			segno = rad.nextInt(1);// valore random tra 0 e 1
			str = Integer.toString(segno) + str;// Integer.toString(strinTipo)

		} else {
			strinTipo = 0;
			strinTipo = rad.nextInt(100);
			if (strinTipo >= 0 && strinTipo <= 9) {
				str = "0" + strinTipo;
			}
			str += strinTipo;
		}
		return str;
	}
	// fine metodi

	@Override
	public boolean elimina(Sensore s) {
		boolean ret = false;
		try {
			// oggetto connessione
			Connection conn = DBConnector.getConnector().getConnessione();
			// preparo la query
			query = conn.prepareStatement(ELIMINA_QUERY);
			query.setString(1, s.getID());

			query.execute();
			ret = true;
		} catch (SQLException e) {

			System.out.println("Connessione Fallita!");
			//e.printStackTrace();
		}
		return ret;
	}

	@Override
	public List<Sensore> visualizzaTutti() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sensore> cerca(String campo, String valore) {
		List<Sensore> ret = null;
		try {
			Connection conn = DBConnector.getConnector().getConnessione();
			query = conn.prepareStatement(CERCA_QUERY);
			query.setString(2, "%" + campo + "%");
			query.setString(1, valore);
			result = query.executeQuery();
			ret = creaLista(result);
		} catch (SQLException e) {
			System.out.println("Connessione Fallita!");
			//e.printStackTrace();
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

	private List<Sensore> creaLista(ResultSet res) {
		List<Sensore> lista = new LinkedList<Sensore>();

		try {
			while (res.next()) {

				String ID = res.getString("ID");
				String modello = res.getString("modello");
				String impianto = res.getString("Impianto");
				String rilevazione = res.getString("rilevazione");
				String tipo = res.getString("tipo");

				Sensore sens = new Sensore(ID, modello, impianto, rilevazione, tipo);

				lista.add(sens);

			}
		} catch (SQLException e) {

			System.out.println("Connessione Fallita!");
			//e.printStackTrace();
		}

		return lista;
	}

}
