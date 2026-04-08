package model;

import java.time.LocalDate;


public  class Utente {

	private int idUtente;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String email;
	private LocalDate dataNascita;
	private TipoRuolo ruolo;
	
	
	public Utente(String nome, String cognome, String username, String password, String email, LocalDate dataNascita, TipoRuolo ruolo) {
		this.idUtente=0;
		this.nome=nome;
		this.cognome=cognome;
		this.username=username;
		this.password=password;
		this.email=email;
		this.dataNascita=dataNascita;
		this.ruolo=ruolo;
		
	}
	
	public Utente(int idUtente, String nome, String cognome, String username, String password, String email, LocalDate dataNascita, TipoRuolo ruolo) {
	    this(nome, cognome, username, password, email, dataNascita, ruolo); 
	    this.idUtente = idUtente;
	}
	
	
	public static boolean isSoloLettere(String testo) {
	    for (int i = 0; i < testo.length(); i++) {
	        char c = testo.charAt(i);
	        if (!Character.isLetter(c) && !Character.isWhitespace(c) && c != '\'') {
	            return false; 
	        }
	    }
	    return true;
	}
	
	public static boolean isLunghezzaValida(String testo) {
		if(testo == null || testo.length()>=30 || testo.isEmpty()) return false;
		return true;
	}

	public static boolean isEmailValida(String email) {
	    if (email == null) return false;
	    if(email.contains("@") && !email.startsWith("@") && !email.endsWith("@") && email.contains(".") && email.lastIndexOf(".") > email.indexOf("@")) return true;
	    return false;
	}
	
	public static int calcolaEta(LocalDate dataNascita) {
	    if (dataNascita == null) return 0;
	    return java.time.Period.between(dataNascita, java.time.LocalDate.now()).getYears();
	}

	public static boolean isEtaCoerente(LocalDate dataNascita) {
	    int eta = calcolaEta(dataNascita);
	    return eta >= 18 && eta <= 120;
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
