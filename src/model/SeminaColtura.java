package model;

public class SeminaColtura {

	private Coltura coltura;
	private Semina semina;
	private double quantitaSemi;
	
	
	public SeminaColtura(Coltura coltura, Semina semina, double quantitaSemi) {
	    this.semina = semina;
	    this.coltura = coltura;
	    this.quantitaSemi = quantitaSemi;
	}
	
	public static boolean isQuantitaSemiValida(double numero){
		if(numero < 1) return false;
		return true;
	}
	
	public static boolean isSoloCifre(String testo ) {
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
	    
	
	public Coltura getColtura() {
		return coltura;
	}
	public void setColtura(Coltura coltura) {
		this.coltura = coltura;
	}
	public Semina getSemina() {
		return semina;
	}
	public void setSemina(Semina semina) {
		this.semina = semina;
	}
	public double getQuantitaSemi() {
		return quantitaSemi;
	}
	public void setQuantitaSemi(double quantitaSemi) {
		this.quantitaSemi = quantitaSemi;
	}
}
