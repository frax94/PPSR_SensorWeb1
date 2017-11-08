package business.servizi;

import java.util.List;

import business.entita.Sensore;
//import business.entita.Utente;
import integration.DAO.DaoSensore;
/**
 * 
 * @author PPSR
 *
 */

public class ServizioSensori implements CRUD<Sensore> {

	final static String ERRORE;
	final static String ANOMALIA;
	final static String SUCCESSO;

	final static String TEMPERATURA;
	final static String UMIDITA;
	final static String PRESSIONE;
	
	static{
		ERRORE = "ERR";
		ANOMALIA = "ANM";
		SUCCESSO = "SUC";

		TEMPERATURA = "T";
		UMIDITA = "U";
		PRESSIONE = "P";
		
	}
	private DaoSensore daoSensore = new DaoSensore();

	@Override
	public boolean inserisci(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Sensore) {
			Sensore sens = (Sensore) oggetto;
			ret = daoSensore.inserisci(sens);
		}
		return ret;
	}

	@Override
	public boolean elimina(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Sensore) {
			Sensore sens = (Sensore) oggetto;
			ret = daoSensore.elimina(sens);
		}
		return ret;
	}

	@Override
	public List<Sensore> visualizzaTutti() {
		List<Sensore> ret = daoSensore.visualizzaTutti();
		return ret;

	}

	@Override
	public List<Sensore> cerca(String campo, String valore) {
		List<Sensore> ret = daoSensore.cerca(campo, valore);
		for (Sensore s : ret) {
			String ril = s.getRilevazione();
			ril = formattaRilev(ril);
			s.setRilevazione(ril);
		}
		return ret;

	}

	// classe di formattazione stringa rielevazione
	private String formattaRilev(String rilevazione) {
		String format = null;
		if (rilevazione.length() > 0) { // previene rilevazione vuota
			String stato = rilevazione.substring(rilevazione.length() - 3);// prende
																			// ultime
																			// 3
																			// lettere
																			// dove
																			// sta
																			// scritto
																			// lo
																			// stato
																			// del
																			// sensore
																			// se
																			// c'è
																			// ANM
																			// ERR
																			// O
																			// SUCC
			if (stato.equalsIgnoreCase(ERRORE)) {
				format = "Errore di trasmissione!";
			} else if (stato.equalsIgnoreCase(ANOMALIA)) {
				format = "Malfunzionamento sostrituire sensore!";
			} else {
				String tipo = rilevazione.substring(0, 1);
				String decimale = rilevazione.substring(5, rilevazione.length() - 3);

				System.out.println("rilevazi: " + rilevazione);
				System.out.println("decimale: " + decimale);

				String ore = "";
				String min = "";
				String anno = "";
				String mese = "";
				String giorno = "";
				String segno = "";
				String valore = "";
				String misura = "";

				if (tipo.equalsIgnoreCase(TEMPERATURA)) { // formatta la
															// temperatura
					giorno = decimale.substring(0, 2);
					mese = decimale.substring(2, 4);
					anno = decimale.substring(4, 8);
					segno = decimale.substring(8, 9);
					valore = decimale.substring(9, 11);
					ore = decimale.substring(11, 13);
					min = decimale.substring(13, 15);
					misura = "°";

					format = ordina(ore, min, giorno, mese, anno, segno, valore, misura);
				} else if (tipo.equalsIgnoreCase(UMIDITA)) {
					giorno = decimale.substring(0, 2);
					mese = decimale.substring(2, 4);
					anno = decimale.substring(4, 8);
					valore = decimale.substring(8, 10);
					ore = decimale.substring(10, 12);
					min = decimale.substring(12, 14);
					misura = "%";

					format = ordina(ore, min, giorno, mese, anno, valore, misura);
				} else if (tipo.equalsIgnoreCase(PRESSIONE)) {
					giorno = decimale.substring(0, 2);
					mese = decimale.substring(2, 4);
					anno = decimale.substring(4, 8);
					valore = decimale.substring(8, 12);
					ore = decimale.substring(12, 14);
					min = decimale.substring(14, 16);
					misura = " hPa";

					format = ordina(ore, min, giorno, mese, anno, valore, misura);
				}
			}
		}

		return format;
	}

	// ordina Temperatura
	private String ordina(String ore, String min, String giorno, String mese, String anno, String segno, String valore,
			String misura) {
		String toReturn = "";

		if (segno.equalsIgnoreCase("0"))
			segno = "+";
		else if (segno.equalsIgnoreCase("1"))
			segno = "-";

		int val = Integer.parseInt(valore);

		toReturn = ore + ":" + min + "   |   " + giorno + "/" + mese + "/" + anno + "   |   " + segno + val + misura;

		return toReturn;
	}

	// Ordina umidità
	private String ordina(String ore, String min, String giorno, String mese, String anno, String valore,
			String misura) {
		String toReturn = "";
		int val = Integer.parseInt(valore);
		toReturn = ore + ":" + min + "   |   " + giorno + "/" + mese + "/" + anno + "   |   " + val + misura;
		return toReturn;
	}

}
