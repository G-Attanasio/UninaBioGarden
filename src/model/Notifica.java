package model;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Notifica {

	private int codNotifica;
	private LocalDate dataInvio;
	private Utente creatore;
	
	
	public Notifica(LocalDate dataInvio, Utente creatore) {
		this.dataInvio=dataInvio;
		this.creatore=creatore;
		
	}
	
	public Notifica(int codNotifica, LocalDate dataInvio,Utente creatore) {
		this(dataInvio,creatore);
		this.codNotifica=codNotifica;
	}
	
	
	
	public int getCodNotifica() {
		return codNotifica;
	}
	public void setCodNotifica(int codNotifica) {
		this.codNotifica = codNotifica;
	}
	public LocalDate getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(LocalDate dataInvio) {
		this.dataInvio = dataInvio;
	}
	public Utente getCreatore() {
		return creatore;
	}
	public void setCreatore(Utente creatore) {
		this.creatore = creatore;
	}

	
}
