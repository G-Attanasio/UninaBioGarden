package model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Attivita {

	private int codAttivita;
	private Stato statoEsecuzione;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private Utente coltivatore;
	private ProgettoStagionale progetto;
	
	public Attivita( LocalDate dataInizio, LocalDate dataFine, Utente coltivatore,ProgettoStagionale progetto) {
		this.codAttivita=0;
		this.statoEsecuzione= Stato.PIANIFICATO;
		this.dataInizio=dataInizio;
		this.dataFine=dataFine;
		this.coltivatore=coltivatore;
		this.progetto=progetto;
	}
	
	public Attivita(int codAttivita, Stato stato, LocalDate inizio, LocalDate fine, Utente coltivatore, ProgettoStagionale progetto) {
	    this.codAttivita = codAttivita;
	    this.statoEsecuzione = stato;
	    this.dataInizio = inizio;
	    this.dataFine = fine;
	    this.coltivatore = coltivatore;
	    this.progetto = progetto;
	}
	
	public boolean isDataInizioValida(LocalDate inizio) {
	    if (inizio == null || inizio.isBefore(LocalDate.now())) return false;
	    return true;
	}

	public boolean isDataFineValida(LocalDate inizio, LocalDate fine) {
	    if (fine == null || fine.isBefore(inizio)) return false;
	    return true;
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
	public Utente getColtivatore() {
		return coltivatore;
	}
	public void setColtivatore(Utente coltivatore) {
		this.coltivatore = coltivatore;
	}
	public ProgettoStagionale getProgetto() {
		return progetto;
	}
	public void setProgetto(ProgettoStagionale progetto) {
		this.progetto = progetto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codAttivita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attivita other = (Attivita) obj;
		return codAttivita == other.codAttivita;
	}
}
