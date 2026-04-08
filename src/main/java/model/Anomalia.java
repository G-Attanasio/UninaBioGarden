package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Anomalia extends Notifica {

	private String tipoAnomalia;
	private String descrizione;
	private LivelloGravita gravita;
	private int estensione;
	
	public Anomalia(LocalDate dataInvio, Utente creatore,String tipoAnomalia,String descrizione, LivelloGravita gravita, int estensione, ArrayList<Utente> destinatari) {
		super(dataInvio, creatore, destinatari);
		this.tipoAnomalia=tipoAnomalia;
		this.descrizione=descrizione;
		this.gravita=gravita;
		this.estensione=estensione;
	}
	
	public Anomalia( int codNotifica, LocalDate dataInvio, Utente creatore,String tipoAnomalia, String descrizione, LivelloGravita gravita, int estensione, ArrayList<Utente> destinatari) {
		super(codNotifica, dataInvio,creatore,destinatari);
		this.tipoAnomalia=tipoAnomalia;
		this.descrizione=descrizione;
		this.gravita=gravita;
		this.estensione=estensione;
	}
	
	
	public static boolean isEstensioneValida(int estensione) {
		if(estensione < 0) return false;
		return true;
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

	
}
