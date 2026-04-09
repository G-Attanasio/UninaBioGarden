package dto;

public class DatiReportDTO {


	 private String nomeColtura;
	 private double quantitaSemi;
	 private double quantitaPrevista;
	 private double quantitaReale;

	    public DatiReportDTO(String nome, double s, double p, double r) {
	        this.nomeColtura = nome;
	        this.quantitaSemi=s;
	        this.quantitaPrevista=p;
	        this.quantitaReale=r;
	    }

	   
	    public double getQuantitaSemi() {
			return quantitaSemi;
		}
		public void setQuantitaSemi(double quantitaSemi) {
			this.quantitaSemi = quantitaSemi;
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
		public void setNomeColtura(String nomeColtura) {
			this.nomeColtura = nomeColtura;
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
