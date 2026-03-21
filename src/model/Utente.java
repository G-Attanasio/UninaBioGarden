package model;

import java.time.LocalDate;
import java.util.ArrayList;

public  class Utente {

	private int idUtente;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String email;
	private LocalDate dataNascita;
	private TipoRuolo ruolo;
	private ArrayList<LottoColtivabile> lottiPosseduti;
	private ArrayList<ProgettoStagionale> progettiCreati;
	private ArrayList<ProgettoStagionale> progettiAssegnati;
	private ArrayList<Attività> attivitaAssegnate;
	
	public Utente(String nome, String cognome, String username, String password, String email, LocalDate dataNascita, TipoRuolo ruolo) {
		this.idUtente=0;
		this.nome=nome;
		this.cognome=cognome;
		this.username=username;
		this.password=password;
		this.email=email;
		this.dataNascita=dataNascita;
		this.ruolo=ruolo;
		this.lottiPosseduti= new ArrayList<LottoColtivabile>();
		this.progettiCreati= new ArrayList<ProgettoStagionale>();
		this.progettiAssegnati= new ArrayList<ProgettoStagionale>();
		this.attivitaAssegnate= new ArrayList<Attività>();
	}
	
	public Utente(int idUtente, String nome, String cognome, String username, String password, String email, LocalDate dataNascita, TipoRuolo ruolo) {
	    this(nome, cognome, username, password, email, dataNascita, ruolo); 
	    this.idUtente = idUtente;
	}
	
	
	public boolean isSoloLettere(String testo) {
	    for (int i = 0; i < testo.length(); i++) {
	        char c = testo.charAt(i);
	        if (!Character.isLetter(c) && !Character.isWhitespace(c) && c != '\'') {
	            return false; 
	        }
	    }
	    return true;
	}
	
	public boolean isLunghezzaValida(String testo) {
		if(testo == null || testo.length()>=50 || testo.isEmpty()) return false;
		return true;
	}

	public boolean isEmailValida(String email) {
	    if (email == null) return false;
	    if(email.contains("@") && !email.startsWith("@") && !email.endsWith("@") && email.contains(".")) return true;
	    return false;
	}
	
	public int calcolaEta() {
	    if (this.dataNascita == null) return 0;
	    return java.time.Period.between(this.dataNascita, java.time.LocalDate.now()).getYears();
	}

	public boolean isEtaCoerente() {
	    int eta = calcolaEta();
	    return eta >= 0 && eta <= 120;
	}
	
	public void addProgettoCreato(ProgettoStagionale ps) {
	    if (ps != null && !this.progettiCreati.contains(ps)) {
	        this.progettiCreati.add(ps);
	        if (ps.getCreatore() == null) {
	            ps.setCreatore(this);
	        }
	    }
	}

	public void addLotto(LottoColtivabile l) {
	    if (l != null &&!this.lottiPosseduti.contains(l)) this.lottiPosseduti.add(l);
	}

	public void addAttivita(Attività a) {
	    if (a != null && !this.attivitaAssegnate.contains(a)) this.attivitaAssegnate.add(a);
	}

	public void addProgettoAssegnato(ProgettoStagionale ps) {
	    if (ps!=null && !this.progettiAssegnati.contains(ps)) {
	        this.progettiAssegnati.add(ps);
	    }
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

	public ArrayList<LottoColtivabile> getLottiPosseduti() {
		return lottiPosseduti;
	}

	public void setLottiPosseduti(ArrayList<LottoColtivabile> lottiPosseduti) {
		this.lottiPosseduti = lottiPosseduti;
	}

	public ArrayList<ProgettoStagionale> getProgettiCreati() {
		return progettiCreati;
	}

	public void setProgettiCreati(ArrayList<ProgettoStagionale> progettiCreati) {
		this.progettiCreati = progettiCreati;
	}

	public ArrayList<ProgettoStagionale> getProgettiAssegnati() {
		return progettiAssegnati;
	}

	public void setProgettiAssegnati(ArrayList<ProgettoStagionale> progettiAssegnati) {
		this.progettiAssegnati = progettiAssegnati;
	}

	public ArrayList<Attività> getAttivitaAssegnate() {
		return attivitaAssegnate;
	}

	public void setAttivitaAssegnate(ArrayList<Attività> attivitàAssegnate) {
		this.attivitaAssegnate = attivitàAssegnate;
	}
	
}
