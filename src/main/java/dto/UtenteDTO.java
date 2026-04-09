package dto;

import java.time.LocalDate;

import model.TipoRuolo;

public class UtenteDTO {

	private int idUtente;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String email;
	private LocalDate dataNascita;
	private TipoRuolo ruolo;
	
	public UtenteDTO(String nome, String cognome, String username, String password, String email, LocalDate dataNascita, TipoRuolo ruolo) {
		this.idUtente=0;
		this.nome=nome;
		this.cognome=cognome;
		this.username=username;
		this.password=password;
		this.email=email;
		this.dataNascita=dataNascita;
		this.ruolo=ruolo;
		
	}
	
	public UtenteDTO(int idUtente, String nome, String cognome, String username, String password, String email, LocalDate dataNascita, TipoRuolo ruolo) {
	    this(nome, cognome, username, password, email, dataNascita, ruolo); 
	    this.idUtente = idUtente;
	}
	
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public TipoRuolo getRuolo() {
		return ruolo;
	}
	public void setRuolo(TipoRuolo ruolo) {
		this.ruolo = ruolo;
	}
}
