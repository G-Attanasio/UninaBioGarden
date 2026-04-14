package dto;

import java.time.LocalDate;
import java.util.ArrayList;

import model.LivelloGravita;

public class NotificaDTO {

	private int codNotifica;
	private LocalDate dataInvio;
	private int idCreatore;
    ArrayList<String> destinatari;
    private String tipo;
    private String descrizioneVeloce;
    private String descrizione;
    private LocalDate dataScadenza;
    private String gravità;
    private int estensione;
	
	
	public NotificaDTO(LocalDate dataInvio, int idCreatore, ArrayList<String> destinatari) {
		this.dataInvio=dataInvio;
		this.idCreatore=idCreatore;
		this.destinatari=destinatari;
	}
	
	public NotificaDTO(int codNotifica, LocalDate dataInvio,int idCreatore, ArrayList<String> destinatari) {
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
	public ArrayList<String> getDestinatari() {
		return destinatari;
	}
	public int getIdCreatore() {
		return idCreatore;
	}
	public void setIdCreatore(int idCreatore) {
		this.idCreatore = idCreatore;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescrizioneVeloce() {
		return descrizioneVeloce;
	}

	public void setDescrizioneVeloce(String descrizioneVeloce) {
		this.descrizioneVeloce = descrizioneVeloce;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getGravità() {
		return gravità;
	}

	public void setGravità(String gravità) {
		this.gravità = gravità;
	}

	public int getEstensione() {
		return estensione;
	}

	public void setEstensione(int estensione) {
		this.estensione = estensione;
	}
}
