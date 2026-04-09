package dto;

import java.time.LocalDate;

import model.Stagione;
import model.Stato;


public class ProgettoDTO {

	private int codProgetto;
	private String nomeProgetto;
	private Stagione stagioneDiRiferimento;
	private int durata;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private Stato statoEsecuzione;
	private int idCreatore;
	private int lottoImpegnato;
	
	
	public ProgettoDTO(String nomeProgetto, Stagione stagioneDiRiferimento, int durata, LocalDate dataInizio,int idCreatore,int lottoImpegnato) {
		this.codProgetto=0;
		this.nomeProgetto=nomeProgetto;
		this.stagioneDiRiferimento=stagioneDiRiferimento;
		this.durata=durata;
		this.dataInizio=dataInizio;
		this.statoEsecuzione=Stato.PIANIFICATO;
		this.idCreatore=idCreatore;
		this.lottoImpegnato=lottoImpegnato;
		
	}
	
	public ProgettoDTO(int codProgetto,String nomeProgetto, Stagione stagioneDiRiferimento, int durata, LocalDate dataInizio,LocalDate dataFine,Stato statoEsecuzione,int lottoImpegnato,int idCreatore) {
		this(nomeProgetto,stagioneDiRiferimento,durata,dataInizio,idCreatore, lottoImpegnato);
		this.codProgetto=codProgetto;
		this.dataFine=dataFine;
		this.statoEsecuzione=statoEsecuzione;
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
	public int getLottoImpegnato() {
		return lottoImpegnato;
	}
	public void setLottoImpegnato(int lottoImpegnato) {
		this.lottoImpegnato = lottoImpegnato;
	}
	public int getIdCreatore() {
		return idCreatore;
	}
	public void setIdCreatore(int idCreatore) {
		this.idCreatore = idCreatore;
	}	
}
