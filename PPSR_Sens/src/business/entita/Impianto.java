package business.entita;

/**
 * 
 * @author PPSR
 *
 */
public class Impianto implements Entita {

	public Impianto(String nome, String cliente) {
		this.nome = nome;
		this.cliente = cliente;
	}

	public Impianto(String iD, String nome, String cliente) {
		ID = iD;
		this.nome = nome;
		this.cliente = cliente;
	}

	private String ID;
	private String nome;
	private String cliente;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
}
