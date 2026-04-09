package dto;

import java.time.LocalDate;

import model.Stato;
import model.TipoSemina;



public class SeminaDTO extends AttivitaDTO {

private TipoSemina metodoSemina;
	
	

	public SeminaDTO(LocalDate dataInizio, LocalDate dataFine, int idColtivatore,int codProgetto, TipoSemina metodoSemina) {
		super(dataInizio, dataFine, idColtivatore, codProgetto);
		this.metodoSemina=metodoSemina;
		
	}
	
	public SeminaDTO(int codAttivita, Stato statoEsecuzione, LocalDate dataInizio, LocalDate dataFine, int idColtivatore, int codProgetto, TipoSemina metodoSemina) {
	    super(codAttivita, statoEsecuzione, dataInizio, dataFine, idColtivatore, codProgetto); 
	    this.metodoSemina=metodoSemina;
	 
	  
	}

	public TipoSemina getMetodoSemina() {
		return metodoSemina;
	}

	public void setMetodoSemina(TipoSemina metodoSemina) {
		this.metodoSemina = metodoSemina;
	}
}
