package dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class NotificaDTO {

	private int codNotifica;
	private LocalDate dataInvio;
	private int idCreatore;
    ArrayList<UtenteDTO> destinatari;
	
	
	public NotificaDTO(LocalDate dataInvio, int idCreatore, ArrayList<UtenteDTO> destinatari) {
		this.dataInvio=dataInvio;
		this.idCreatore=idCreatore;
		this.destinatari=destinatari;
	}
	
	public NotificaDTO(int codNotifica, LocalDate dataInvio,int idCreatore, ArrayList<UtenteDTO> destinatari) {
		this(dataInvio,idCreatore, destinatari);
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
	public ArrayList<UtenteDTO> getDestinatari() {
		return destinatari;
	}
	public int getIdCreatore() {
		return idCreatore;
	}
	public void setIdCreatore(int idCreatore) {
		this.idCreatore = idCreatore;
	}
}
