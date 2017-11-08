package business.servizi;

import business.entita.Utente;

/**
 * Classe (pattern: singleton) per login e sessione dell'utente
 * 
 * @author PPSR
 *
 */

public class ServizioLogin {
	// non ricordo perchè l'ho fatto ma funziona
	private static final String MENOUNO = "-1";
	private static Utente utenteLoggato = new Utente(MENOUNO, "", "", "", "", "0", "");

	/**
	 * Oggetto per l'utente loggato nel sistema
	 */
	private ServizioLogin() {

	}

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public static boolean effettuaLogin(String email, String pwd) {
		boolean ret = false;

		ServizioUtenti serv = new ServizioUtenti();
		Utente utenteTemp = serv.controlloIdentita(email, pwd);
		if (utenteTemp != null) {
			utenteLoggato = utenteTemp;

			System.out.println(utenteLoggato.getEmail() + " " + utenteLoggato.getPassword() + " "
					+ utenteLoggato.getAdmin() + " " + utenteLoggato.getCodice());

			ret = true;
		} else {
			utenteLoggato = new Utente(MENOUNO, "", "", "", "", "0", "");
		}

		return ret;
	}

}
