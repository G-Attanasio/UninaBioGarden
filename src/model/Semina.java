package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class  Semina extends Attivita {
	
	private TipoSemina metodoSemina;
	private ArrayList<SeminaColtura> listaSemine;
	

	public Semina(LocalDate dataInizio, LocalDate dataFine, Utente coltivatore,ProgettoStagionale progetto, TipoSemina metodoSemina) {
		super(dataInizio, dataFine, coltivatore, progetto);
		this.metodoSemina=metodoSemina;
		this.listaSemine= new ArrayList<SeminaColtura>();
	
	}
	
	public Semina(int codAttivita, Stato statoEsecuzione, LocalDate dataInizio, LocalDate dataFine, Utente coltivatore, ProgettoStagionale progetto, TipoSemina metodoSemina) {
	    super(codAttivita, statoEsecuzione, dataInizio, dataFine, coltivatore, progetto); 
	    this.metodoSemina=metodoSemina;
	    this.listaSemine= new ArrayList<SeminaColtura>();
	  
	}
	
	public void addSemina(SeminaColtura sc) {
		if(sc != null && !listaSemine.contains(sc)) {
			listaSemine.add(sc);
			if(sc.getSemina()==null) {
				sc.setSemina(this);
			}
		}
	}

	public TipoSemina getMetodoSemina() {
		return metodoSemina;
	}

	public void setMetodoSemina(TipoSemina metodoSemina) {
		this.metodoSemina = metodoSemina;
	}

	public ArrayList<SeminaColtura> getListaSemine() {
		return listaSemine;
	}

	public void setListaSemine(ArrayList<SeminaColtura> listaSemine) {
		this.listaSemine = listaSemine;
	}

	
	
}
