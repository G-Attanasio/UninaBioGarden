package dto;

import java.time.LocalDate;


import model.Stato;
import model.TipoIrrigazione;


public class IrrigazioneDTO extends AttivitaDTO{

	private TipoIrrigazione metodoIrrigazione;

	public IrrigazioneDTO(LocalDate dataInizio, LocalDate dataFine, int idColtivatore,int codProgetto,TipoIrrigazione metodoIrrigazione) {
		super(dataInizio, dataFine, idColtivatore, codProgetto);
		this.metodoIrrigazione=metodoIrrigazione;
	}
	
	public IrrigazioneDTO( int codAttivita, Stato statoEsecuzione, LocalDate dataInizio, LocalDate dataFine,int idColtivatore,int codProgetto, TipoIrrigazione metodoIrrigazione) {
		super(codAttivita,statoEsecuzione,dataInizio,dataFine,idColtivatore,codProgetto);
		this.metodoIrrigazione=metodoIrrigazione;
	}

	public TipoIrrigazione getMetodoIrrigazione() {
		return metodoIrrigazione;
	}

	public void setMetodoIrrigazione(TipoIrrigazione metodoIrrigazione) {
		this.metodoIrrigazione = metodoIrrigazione;
	}
}
