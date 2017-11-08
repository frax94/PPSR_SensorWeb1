package business.servizi;

import java.util.List;

import business.entita.Impianto;
import integration.DAO.DaoImpianto;
import integration.DAO.DaoImpianto.coppia;
/**
 * 
 * @author PPSR
 *
 */

public class ServizioImpianti implements CRUD<Impianto> {
	private DaoImpianto daoImpianto = new DaoImpianto();

	@Override
	public boolean inserisci(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Impianto) {
			Impianto imp = (Impianto) oggetto;
			ret = daoImpianto.inserisci(imp);
		}
		return ret;
	}

	@Override
	public boolean elimina(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Impianto) {
			Impianto imp = (Impianto) oggetto;
			ret = daoImpianto.elimina(imp);
		}
		return ret;
	}

	@Override
	public List<Impianto> visualizzaTutti() {
		List<Impianto> ret = daoImpianto.visualizzaTutti();
		return ret;
	}

	// visualizza impianto altro cliente con passaggio del codice
	public List<coppia> visualizza(String codice) {
		List<coppia> ret = daoImpianto.visualizza(codice);
		return ret;
	}

	@Override
	public List<Impianto> cerca(String campo, String valore) {
		List<Impianto> ret = daoImpianto.cerca(campo, valore);
		return ret;
	}

	public List<coppia> cercaC(String valore) {
		List<coppia> ret = daoImpianto.cercaC(valore);
		return ret;
	}

	public List<coppia> visualizzaTuttiC() {
		List<coppia> ret = daoImpianto.visualizzaTuttiC();
		return ret;
	}

}
