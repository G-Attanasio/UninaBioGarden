package model;

import java.util.Date;

public  class Utente {

	private String idUtente;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String email;
	private Date dataNascita;
	private TipoRuolo ruolo;
	
	public Utente(String idUtente, String nome, String cognome, String username, String password, String email, Date dataNascita,TipoRuolo ruolo) {
		this.idUtente=null;
		this.nome=nome;
		this.cognome=cognome;
		this.username=username;
		this.password=password;
		this.email=email;
		this.dataNascita=dataNascita;
		this.ruolo=ruolo;
	}
	
}
