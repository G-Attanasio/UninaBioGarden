package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class  Semina extends Attivita {
	
	private TipoSemina metodoSemina;
	
	

	public Semina(LocalDate dataInizio, LocalDate dataFine, Utente coltivatore,ProgettoStagionale progetto, TipoSemina metodoSemina) {
		super(dataInizio, dataFine, coltivatore, progetto);
		this.metodoSemina=metodoSemina;
		
	}
	
	public Semina(int codAttivita, Stato statoEsecuzione, LocalDate dataInizio, LocalDate dataFine, Utente coltivatore, ProgettoStagionale progetto, TipoSemina metodoSemina) {
	    super(codAttivita, statoEsecuzione, dataInizio, dataFine, coltivatore, progetto); 
	    this.metodoSemina=metodoSemina;
	 
	  
	}
	

	public TipoSemina getMetodoSemina() {
		return metodoSemina;
	}

	public void setMetodoSemina(TipoSemina metodoSemina) {
		this.metodoSemina = metodoSemina;
	}

	

	
	
}
