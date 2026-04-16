package model;

import java.time.LocalDate;

public class Raccolta extends Attivita {

	private TipoRaccolta metodoRaccolta;
	private double quantitaPrevista;
	private double quantitaReale;
	private Coltura coltura;
	
	
	public Raccolta(LocalDate dataInizio, LocalDate dataFine, Utente coltivatore, ProgettoStagionale progetto,TipoRaccolta metodoRaccolta,double quantitaPrevista,Coltura coltura) {
		super(dataInizio, dataFine, coltivatore, progetto);
		this.metodoRaccolta=metodoRaccolta;
		this.quantitaPrevista=quantitaPrevista;
		this.coltura=coltura;
	}
	
	public Raccolta(int codAttivita, Stato statoEsecuzione,LocalDate dataInizio,LocalDate dataFine,Utente coltivatore, ProgettoStagionale progetto, TipoRaccolta metodoRaccolta, double quantitaPrevista,double quantitaReale, Coltura coltura) {
		super(codAttivita,statoEsecuzione,dataInizio,dataFine,coltivatore,progetto);
		this.metodoRaccolta=metodoRaccolta;
		this.quantitaPrevista=quantitaPrevista;
		this.quantitaReale=quantitaReale;
		this.coltura=coltura;
	}
	
	public static boolean isQuantitaPrevistaValida(double numero) {
		if(numero < 0 || numero > 1000000) return false;
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
	
	public TipoRaccolta getMetodoRaccolta() {
		return metodoRaccolta;
	}
	public void setMetodoRaccolta(TipoRaccolta metodoRaccolta) {
		this.metodoRaccolta = metodoRaccolta;
	}
	public double getQuantitaPrevista() {
		return quantitaPrevista;
	}
	public void setQuantitaPrevista(double quantitaPrevista) {
		this.quantitaPrevista = quantitaPrevista;
	}
	public double getQuantitaReale() {
		return quantitaReale;
	}
	public void setQuantitaReale(double quantitaReale) {
		this.quantitaReale = quantitaReale;
	}

	public Coltura getColtura() {
		return coltura;
	}

	public void setColtura(Coltura coltura) {
		this.coltura = coltura;
	}
}
