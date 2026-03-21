package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProgettoStagionale {

	private int codProgetto;
	private String nomeProgetto;
	private Stagione stagioneDiRiferimento;
	private int durata;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private Stato statoEsecuzione;
	private Utente creatore;
	private ArrayList<Utente> coltivatoriAssegnati;
	private LottoColtivabile lottoImpegnato;
	
	public ProgettoStagionale(String nomeProgetto, Stagione stagioneDiRiferimento, int durata, LocalDate dataInizio,Utente creatore,LottoColtivabile lottoImpegnato) {
		this.codProgetto=0;
		this.nomeProgetto=nomeProgetto;
		this.stagioneDiRiferimento=stagioneDiRiferimento;
		this.durata=durata;
		this.dataInizio=dataInizio;
		statoEsecuzione=Stato.PIANIFICATO;
		this.creatore=creatore;
		this.lottoImpegnato=lottoImpegnato;
		this.coltivatoriAssegnati=new ArrayList<Utente>();
	}
	
	public ProgettoStagionale(int codProgetto,String nomeProgetto, Stagione stagioneDiRiferimento, int durata, LocalDate dataInizio,LocalDate dataFine,Utente creatore,LottoColtivabile lottoImpegnato) {
		this(nomeProgetto,stagioneDiRiferimento,durata,dataInizio,creatore, lottoImpegnato);
		this.codProgetto=codProgetto;
		this.dataFine=dataFine;
	}
	
	public void addColtivatore(Utente u) {
		if(u != null && !coltivatoriAssegnati.contains(u)) {
			coltivatoriAssegnati.add(u);
		}
	}
	
	public boolean isLunghezzaNomeValida(String testo) {
		if(testo == null || testo.length()>50 || testo.isEmpty()) return false;
		return true;
	}
	
	public boolean isDurataValida(int durata) {
		if(durata <1 || durata >180) return false;
		return true;
	}
	
	public boolean isAlfaNumerico(String testo) {
		for (int i=0; i< testo.length(); i++) {
			char c= testo.charAt(i);
			if(!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
				return false;
			}
		}
		return true;
	}
	
	public int getCodProgetto() {
		return codProgetto;
	}
	public void setCodProgetto(int codProgetto) {
		this.codProgetto = codProgetto;
	}
	public Stagione getStagioneDiRiferimento() {
		return stagioneDiRiferimento;
	}
	public void setStagioneDiRiferimento(Stagione stagioneDiRiferimento) {
		this.stagioneDiRiferimento = stagioneDiRiferimento;
	}
	public String getNomeProgetto() {
		return nomeProgetto;
	}
	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}
	public int getDurata() {
		return durata;
	}
	public void setDurata(int durata) {
		this.durata = durata;
	}
	public LocalDate getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}
	public LocalDate getDataFine() {
		return dataFine;
	}
	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}
	public Stato getStatoEsecuzione() {
		return statoEsecuzione;
	}
	public void setStatoEsecuzione(Stato statoEsecuzione) {
		this.statoEsecuzione = statoEsecuzione;
	}

	public Utente getCreatore() {
		return creatore;
	}

	public void setCreatore(Utente creatore) {
		this.creatore = creatore;
	}

	public ArrayList<Utente> getColtivatoriAssegnati() {
		return coltivatoriAssegnati;
	}

	public void setColtivatoriAssegnati(ArrayList<Utente> coltivatoriAssegnati) {
		this.coltivatoriAssegnati = coltivatoriAssegnati;
	}

	public LottoColtivabile getLottoImpegnato() {
		return lottoImpegnato;
	}

	public void setLottoImpegnato(LottoColtivabile lottoImpegnato) {
		this.lottoImpegnato = lottoImpegnato;
	}
	
}
