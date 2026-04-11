package dto;

public class SeminaColturaDTO {

	private int coltura;
	private int semina;
	private double quantitaSemi;
	
	
	public SeminaColturaDTO(int coltura, int semina, double quantitaSemi) {
	    this.semina = semina;
	    this.coltura = coltura;
	    this.quantitaSemi = quantitaSemi;
	}	    
	
	public int getColtura() {
		return coltura;
	}
	public void setColtura(int coltura) {
		this.coltura = coltura;
	}
	public int getSemina() {
		return semina;
	}
	public void setSemina(int semina) {
		this.semina = semina;
	}
	public double getQuantitaSemi() {
		return quantitaSemi;
	}
	public void setQuantitaSemi(double quantitaSemi) {
		this.quantitaSemi = quantitaSemi;
	}
}
