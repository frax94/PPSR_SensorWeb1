package integration.DAO;

import java.util.List;
/**
 * 
 * @author PPSR
 *
 */

public interface DAO<Entita> {

	boolean inserisci(Entita e);

	boolean elimina(Entita e);

	List<Entita> visualizzaTutti();

	List<Entita> cerca(String campo, String valore);
}
