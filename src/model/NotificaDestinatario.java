package model;

public class NotificaDestinatario {

	private Notifica notifica;
	private Utente destinatario;
	private boolean visualizzata;
	
	public NotificaDestinatario(Notifica notifica, Utente destinatario, boolean visualizzata) {
		this.notifica=notifica;
		this.destinatario=destinatario;
		this.visualizzata=visualizzata;
	}
	
	public Notifica getNotifica() {
		return notifica;
	}
	public void setNotifica(Notifica notifica) {
		this.notifica = notifica;
	}
	public Utente getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(Utente destinatario) {
		this.destinatario = destinatario;
	}
	public boolean isVisualizzata() {
		return visualizzata;
	}
	public void setVisualizzata(boolean visualizzata) {
		this.visualizzata = visualizzata;
	}
}
