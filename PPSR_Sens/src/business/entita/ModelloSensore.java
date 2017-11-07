package business.entita;


/**
 * 
 * @author PPSR
 *
 */
public class ModelloSensore implements Entita {

	public ModelloSensore(String codice, String tipo, String marca) {
		super();
		this.codice = codice;
		this.tipo = tipo;
		this.marca = marca;
	}

	private String codice;
	private String tipo;
	private String marca;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
}
