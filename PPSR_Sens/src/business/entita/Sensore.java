package business.entita;

/**
 * 
 * @author PPSR
 *
 */
public class Sensore implements Entita {

	public Sensore(String iD, String modello, String impianto, String rilevazione, String tipo) {
		ID = iD;
		this.modello = modello;
		this.impianto = impianto;
		this.rilevazione = rilevazione;
		this.tipo = tipo;
	}

	public Sensore(String modello, String impianto, String tipo) {
		this.modello = modello;
		this.impianto = impianto;
		this.tipo = tipo;
	}

	private String ID;
	private String modello;
	private String impianto;
	private String rilevazione;
	private String tipo;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getImpianto() {
		return impianto;
	}

	public void setImpianto(String impianto) {
		this.impianto = impianto;
	}

	public String getRilevazione() {
		return rilevazione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setRilevazione(String rilevazione) {
		this.rilevazione = rilevazione;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
