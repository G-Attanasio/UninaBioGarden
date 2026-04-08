package model;

import java.time.LocalDate;

public class Irrigazione extends Attivita {
	
	private TipoIrrigazione metodoIrrigazione;

	public Irrigazione(LocalDate dataInizio, LocalDate dataFine, Utente coltivatore, ProgettoStagionale progetto,TipoIrrigazione metodoIrrigazione) {
		super(dataInizio, dataFine, coltivatore, progetto);
		this.metodoIrrigazione=metodoIrrigazione;
	}
	
	public Irrigazione( int codAttivita, Stato statoEsecuzione, LocalDate dataInizio, LocalDate dataFine, Utente coltivatore, ProgettoStagionale progetto, TipoIrrigazione metodoIrrigazione) {
		super(codAttivita,statoEsecuzione,dataInizio,dataFine,coltivatore,progetto);
		this.metodoIrrigazione=metodoIrrigazione;
	}

	public TipoIrrigazione getMetodoIrrigazione() {
		return metodoIrrigazione;
	}

	public void setMetodoIrrigazione(TipoIrrigazione metodoIrrigazione) {
		this.metodoIrrigazione = metodoIrrigazione;
	}

	

	
}
