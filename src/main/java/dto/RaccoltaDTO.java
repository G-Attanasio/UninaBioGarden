package dto;

import java.time.LocalDate;


import model.Stato;
import model.TipoRaccolta;


public class RaccoltaDTO extends AttivitaDTO{

	private TipoRaccolta metodoRaccolta;
	private double quantitaPrevista;
	private double quantitaReale;
	private int codColtura;
	
	
	public RaccoltaDTO(LocalDate dataInizio, LocalDate dataFine, int idColtivatore, int codProgetto,TipoRaccolta metodoRaccolta,double quantitaPrevista,int codColtura) {
		super(dataInizio, dataFine, idColtivatore, codProgetto);
		this.metodoRaccolta=metodoRaccolta;
		this.quantitaPrevista=quantitaPrevista;
		this.codColtura=codColtura;
	}
	
	public RaccoltaDTO(int codAttivita, Stato statoEsecuzione,LocalDate dataInizio,LocalDate dataFine,int idColtivatore, int codProgetto, TipoRaccolta metodoRaccolta, double quantitaPrevista,double quantitaReale,int codColtura) {
		super(codAttivita,statoEsecuzione,dataInizio,dataFine,idColtivatore,codProgetto);
		this.metodoRaccolta=metodoRaccolta;
		this.quantitaPrevista=quantitaPrevista;
		this.quantitaReale=quantitaReale;
		this.codColtura=codColtura;
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
	public int getCodColtura() {
		return codColtura;
	}
	public void setCodColtura(int codColtura) {
		this.codColtura = codColtura;
	}
}
