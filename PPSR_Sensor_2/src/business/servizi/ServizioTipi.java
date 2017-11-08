package business.servizi;

import java.util.List;

import business.entita.Tipo;
import integration.DAO.DaoTipo;
/**
 * 
 * @author PPSR
 *
 */

public class ServizioTipi implements CRUD<Tipo> {
	private DaoTipo daoTipo = new DaoTipo();

	@Override
	public boolean inserisci(Object oggetto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean elimina(Object oggetto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Tipo> visualizzaTutti() {
		List<Tipo> ret = daoTipo.visualizzaTutti();
		return ret;
	}

	@Override
	public List<Tipo> cerca(String campo, String valore) {
		// TODO Auto-generated method stub
		return null;
	}

}
