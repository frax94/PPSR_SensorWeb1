package business.entita;

/**
 * 
 * @author PPSR
 *
 */

public class Utente implements Entita {

	private String ID;
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String admin;
	private String codice;

	// completo
	public Utente(String id, String nome, String cognome, String email, String password, String admin, String codice) {
		this.ID = id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.codice = codice;
	}

	// Senza id
	public Utente(String nome, String cognome, String email, String password, String admin, String codice) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.codice = codice;

	}

	// senza id e codice
	public Utente(String nome, String cognome, String email, String password, String admin) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}

	// senza id e admin e codice
	public Utente(String nome, String cognome, String email, String password) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

}
