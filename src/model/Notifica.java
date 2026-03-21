package model;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Notifica {

	private int codNotifica;
	private LocalDate dataInvio;
	private Utente creatore;
	private ArrayList<NotificaDestinatario> listaDestinatari;
	
	public Notifica(LocalDate dataInvio, Utente creatore, ArrayList<NotificaDestinatario> listaDestinatari) {
		this.dataInvio=dataInvio;
		this.creatore=creatore;
		this.listaDestinatari=listaDestinatari;
	}
	
	public Notifica(int codNotifica, LocalDate dataInvio,Utente creatore) {
		this(dataInvio,creatore, new ArrayList<NotificaDestinatario>());
		this.codNotifica=codNotifica;
	}
	
	public void addNotifica(NotificaDestinatario nd) {
		if(nd != null && listaDestinatari.contains(nd)) {
			listaDestinatari.add(nd);
			if(nd.getNotifica()==null) {
				nd.setNotifica(this);
			}
		}
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

	public ArrayList<NotificaDestinatario> getListaDestinatari() {
		return listaDestinatari;
	}

	public void setListaDestinatari(ArrayList<NotificaDestinatario> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
}
