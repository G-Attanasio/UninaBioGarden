package model;

import java.time.LocalDate;

public class ProgettoStagionale {

	private int codProgetto;
	private String nomeProgetto;
	private Stagione stagioneDiRiferimento;
	private int durata;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private Stato statoEsecuzione;
	
	public ProgettoStagionale( int codProgetto,String nomeProgetto, Stagione stagioneDiRiferimento, int durata, LocalDate dataInizio) {
		this.codProgetto=0;
		this.nomeProgetto=nomeProgetto;
		this.stagioneDiRiferimento=stagioneDiRiferimento;
		this.durata=durata;
		this.dataInizio=dataInizio;
		statoEsecuzione="PIANIFICATO";
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
	
}
