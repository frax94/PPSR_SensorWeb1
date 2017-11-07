package business.servizi;

import java.util.List;

import business.entita.ModelloSensore;
import integration.DAO.DaoModelloSensore;
/**
 * 
 * @author PPSR
 *
 */

public class ServizioModelli implements CRUD<ModelloSensore> {

	private DaoModelloSensore daoModello = new DaoModelloSensore();

	@Override
	public boolean inserisci(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof ModelloSensore) {
			ModelloSensore mod = (ModelloSensore) oggetto;
			ret = daoModello.inserisci(mod);
		}
		return ret;
	}

	@Override
	public boolean elimina(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof ModelloSensore) {
			ModelloSensore mod = (ModelloSensore) oggetto;
			ret = daoModello.elimina(mod);
		}
		return ret;
	}

	@Override
	public List<ModelloSensore> visualizzaTutti() {
		List<ModelloSensore> ret = daoModello.visualizzaTutti();
		return ret;
	}

	@Override
	public List<ModelloSensore> cerca(String campo, String valore) {
		List<ModelloSensore> ret = daoModello.cerca(campo, valore);
		return ret;
	}

}
