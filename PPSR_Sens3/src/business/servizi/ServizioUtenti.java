package business.servizi;

import java.util.List;

import business.entita.Utente;
import integration.DAO.DaoUtente;
/**
 * 
 * @author PPSR
 *
 */

public class ServizioUtenti implements CRUD<Utente> {
	private DaoUtente daoUtente = new DaoUtente();

	public boolean inserisciDipendente(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Utente) {
			Utente usr = (Utente) oggetto;
			ret = daoUtente.inserisciDipendente(usr);
		}

		return ret;
	}

	@Override
	public boolean inserisci(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Utente) {
			Utente usr = (Utente) oggetto;
			ret = daoUtente.inserisci(usr);
		}
		return ret;

	}

	@Override
	public boolean elimina(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Utente) {
			Utente usr = (Utente) oggetto;
			ret = daoUtente.elimina(usr);
		}
		return ret;
	}

	@Override
	public List<Utente> visualizzaTutti() {
		List<Utente> ret = daoUtente.visualizzaTutti();
		return ret;
	}

	public List<Utente> visualizzaTuttiDipendenti() {
		List<Utente> ret = daoUtente.visualizzaTuttiDipendenti();
		return ret;
	}

	@SuppressWarnings("rawtypes")
	public List visualizzaCodici() {
		List ret = daoUtente.visualizzaCodici();
		return ret;
	}

	@Override
	public List<Utente> cerca(String campo, String valore) {
		List<Utente> ret = daoUtente.cerca(campo, valore);
		return ret;
	}

	public Utente controlloIdentita(String email, String pwd) {
		Utente ret = null;
		List<Utente> listaUtenti = daoUtente.cerca("email", email);
		for (Utente usrTemp : listaUtenti) {
			if (usrTemp.getPassword().equals(pwd)) {
				ret = usrTemp;
			}
		}
		return ret;

	}

}
