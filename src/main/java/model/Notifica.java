package model;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Notifica {

	private int codNotifica;
	private LocalDate dataInvio;
	private Utente creatore;
    ArrayList<Utente> destinatari;
	
	
	public Notifica(LocalDate dataInvio, Utente creatore, ArrayList<Utente> destinatari) {
		this.dataInvio=dataInvio;
		this.creatore=creatore;
		this.destinatari=destinatari;
	}
	
	public Notifica(int codNotifica, LocalDate dataInvio,Utente creatore, ArrayList<Utente> destinatari) {
		this(dataInvio,creatore, destinatari);
		this.codNotifica=codNotifica;
	}
	
	public static boolean isNotificaLunghezzaValida(String stringa) {
		if (stringa==null || stringa.length()>= 30 || stringa.isEmpty()) return false;
		return true;
	}
	
	public static boolean isDescrizioneLunghezzaValida(String stringa) {
		if (stringa.length()>= 500) return false;
		return true;
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

	public ArrayList<Utente> getDestinatari() {
		return destinatari;
	}
	

	
}
