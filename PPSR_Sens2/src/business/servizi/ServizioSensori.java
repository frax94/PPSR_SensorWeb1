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

	final static String ERRORE = "ERR";
	final static String ANOMALIA = "ANM";
	final static String SUCCESSO = "SUC";

	final static String TEMPERATURA = "T";
	final static String UMIDITA = "U";
	final static String PRESSIONE = "P";

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
		int[] num = {0 , 2 , 3 , 4 , 5 , 8 , 9 , 11 , 13 , 15 , 10 , 12 , 14 , 16};
		if (rilevazione.length() > num[0]) { // previene rilevazione vuota
			
			String stato = rilevazione.substring(rilevazione.length() - num[2]);// prende
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
																			// c'�
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
				String decimale = rilevazione.substring(num[4], rilevazione.length() - num[2]);

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
					giorno = decimale.substring(num[0], num[1]);
					mese = decimale.substring(num[1], num[3]);
					anno = decimale.substring(num[3], num[5]);
					segno = decimale.substring(num[5], num[6]);
					valore = decimale.substring(num[6], num[7]);
					ore = decimale.substring(num[7], num[8]);
					min = decimale.substring(num[8], num[9]);
					misura = "�";

					format = ordina(ore, min, giorno, mese, anno, segno, valore, misura);
				} else if (tipo.equalsIgnoreCase(UMIDITA)) {
					giorno = decimale.substring(num[0], num[1]);
					mese = decimale.substring(num[1], num[3]);
					anno = decimale.substring(num[3], num[5]);
					valore = decimale.substring(num[5], num[10]);
					ore = decimale.substring(num[10], num[11]);
					min = decimale.substring(num[11], num[12]);
					misura = "%";

					format = ordina(ore, min, giorno, mese, anno, valore, misura);
				} else if (tipo.equalsIgnoreCase(PRESSIONE)) {
					giorno = decimale.substring(num[0], num[1]);
					mese = decimale.substring(num[1], num[3]);
					anno = decimale.substring(num[3], num[5]);
					valore = decimale.substring(num[5], num[11]);
					ore = decimale.substring(num[11], num[12]);
					min = decimale.substring(num[12], num[13]);
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

	// Ordina umidit�
	private String ordina(String ore, String min, String giorno, String mese, String anno, String valore,
			String misura) {
		String toReturn = "";
		int val = Integer.parseInt(valore);
		toReturn = ore + ":" + min + "   |   " + giorno + "/" + mese + "/" + anno + "   |   " + val + misura;
		return toReturn;
	}

}
