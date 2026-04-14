package dto;

import java.time.LocalDate;
import java.util.ArrayList;

import model.LivelloGravita;


public class AnomaliaDTO extends NotificaDTO {

	private String tipoAnomalia;
	private String descrizione;
	private LivelloGravita gravita;
	private int estensione;
	private String tipo;
	
	public AnomaliaDTO(LocalDate dataInvio, int idCreatore,String tipoAnomalia,String descrizione, LivelloGravita gravita, int estensione, ArrayList<String> destinatari) {
		super(dataInvio, idCreatore, destinatari);
		this.tipoAnomalia=tipoAnomalia;
		this.descrizione=descrizione;
		this.gravita=gravita;
		this.estensione=estensione;
	}
	
	public AnomaliaDTO( int codNotifica, LocalDate dataInvio, int idCreatore,String tipoAnomalia, String descrizione, LivelloGravita gravita, int estensione, ArrayList<String> destinatari) {
		super(codNotifica, dataInvio,idCreatore,destinatari);
		this.tipoAnomalia=tipoAnomalia;
		this.descrizione=descrizione;
		this.gravita=gravita;
		this.estensione=estensione;
	}
	
	public String getTipoAnomalia() {
		return this.tipoAnomalia;
	}
	public void setTipoAnomalia(String tipoAnomalia) {
		this.tipoAnomalia = tipoAnomalia;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public LivelloGravita getGravita() {
		return gravita;
	}
	public void setGravita(LivelloGravita gravita) {
		this.gravita = gravita;
	}
	public int getEstensione() {
		return estensione;
	}
	public void setEstensione(int estensione) {
		this.estensione = estensione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
