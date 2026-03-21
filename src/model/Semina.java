package model;

import java.time.LocalDate;

public class  Semina extends Attivita {
	
	private double quantitaSemi;

	public Semina(LocalDate dataInizio, LocalDate dataFine, Utente coltivatore,ProgettoStagionale progetto, double quantitaSemi) {
		super(dataInizio, dataFine, coltivatore, progetto);
		this.quantitaSemi=quantitaSemi;
		
	}
	
	public Semina(int codAttivita, Stato statoEsecuzione, LocalDate dataInizio, LocalDate dataFine, Utente coltivatore, ProgettoStagionale progetto, double quantitaSemi) {
	    super(codAttivita, statoEsecuzione, dataInizio, dataFine, coltivatore, progetto); 
	    this.quantitaSemi = quantitaSemi;
	}

	public boolean isQuantitaSemiValida(double numero) {
		if( numero < 0) {
			return false;
		}
		return true;
	}

	public boolean isSoloCifre(String testo ) {
		if (testo == null || testo.isEmpty()) return false;
	    int contatorePunti = 0;
	    for (int i = 0; i < testo.length(); i++) {
	        char c = testo.charAt(i);
	        if (c == '.') {
	            contatorePunti++;
	        } else if (!Character.isDigit(c)) {
	            return false;
	        }
	        if (contatorePunti > 1) return false;
	    }
	    return !testo.equals(".");
	}
	
	public double getQuantitaSemi() {
		return quantitaSemi;
	}

	public void setQuantitaSemi(double quantitaSemi) {
		this.quantitaSemi = quantitaSemi;
	}
	
}
