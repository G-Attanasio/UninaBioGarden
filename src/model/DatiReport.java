package model;

public class DatiReport {

	 private String nomeColtura;
	 private double quantitaSemi;
	 private double quantitaPrevista;
	 private double quantitaReale;

	    public DatiReport(String nome, double s, double p, double r) {
	        this.nomeColtura = nome;
	        this.quantitaSemi=s;
	        this.quantitaPrevista=p;
	        this.quantitaReale=r;
	    }

	   
	    public String getNomeColtura() { 
	    	return nomeColtura; 
	    	}
	    public double getSemi() {
	    	return quantitaSemi; 
	    	}
	    public double getPrevista() {
	    	return quantitaPrevista; 
	    	}
	    public double getReale() {
	    	return quantitaReale; 
	    	}
}
