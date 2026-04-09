package dto;

import java.time.LocalDate;

import model.Stato;


public abstract class AttivitaDTO {

	private int codAttivita;
	private Stato statoEsecuzione;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private int idColtivatore;
	private int codProgetto;
	
	public AttivitaDTO( LocalDate dataInizio, LocalDate dataFine, int idColtivatore,int codProgetto) {
		this.setCodAttivita(0);
		this.setStatoEsecuzione(Stato.PIANIFICATO);
		this.setDataInizio(dataInizio);
		this.setDataFine(dataFine);
		this.setIdColtivatore(idColtivatore);
		this.setCodProgetto(codProgetto);
	}
	
	public AttivitaDTO(int codAttivita, Stato stato, LocalDate inizio, LocalDate fine, int idColtivatore, int codProgetto) {
	    this.setCodAttivita(codAttivita);
	    this.setStatoEsecuzione(stato);
	    this.setDataInizio(inizio);
	    this.setDataFine(fine);
	    this.setIdColtivatore(idColtivatore);
	    this.setCodProgetto(codProgetto);
	}

	public int getCodAttivita() {
		return codAttivita;
	}

	public void setCodAttivita(int codAttivita) {
		this.codAttivita = codAttivita;
	}

	public Stato getStatoEsecuzione() {
		return statoEsecuzione;
	}

	public void setStatoEsecuzione(Stato statoEsecuzione) {
		this.statoEsecuzione = statoEsecuzione;
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

	public int getIdColtivatore() {
		return idColtivatore;
	}

	public void setIdColtivatore(int idColtivatore) {
		this.idColtivatore = idColtivatore;
	}

	public int getCodProgetto() {
		return codProgetto;
	}

	public void setCodProgetto(int codProgetto) {
		this.codProgetto = codProgetto;
	}
}
