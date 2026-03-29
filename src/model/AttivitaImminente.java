package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class AttivitaImminente extends Notifica {

	private String tipoAttivitaImminente;
	private String descrizione;
	private LocalDate dataScadenza;

	public AttivitaImminente( LocalDate dataInvio, Utente creatore, String tipoAttivitaImminente, String descrizione,LocalDate dataScadenza, ArrayList<Utente> destinatari) {
		super(dataInvio, creatore,destinatari);
		this.tipoAttivitaImminente=tipoAttivitaImminente;
		this.descrizione=descrizione;
		this.dataScadenza=dataScadenza;
	}
	
	public AttivitaImminente(int codNotifica, LocalDate dataInvio,Utente creatore, String tipoAttivitaImminente, String descrizione, LocalDate dataScadenza, ArrayList<Utente> destinatari) {
		super(codNotifica,dataInvio,creatore,destinatari);
		this.tipoAttivitaImminente=tipoAttivitaImminente;
		this.descrizione=descrizione;
		this.dataScadenza=dataScadenza;
	}
	
	public String getTipoAttivitaImminente() {
		return tipoAttivitaImminente;
	}

	public void setTipoAttivitaImminente(String tipoAttivitaImminente) {
		this.tipoAttivitaImminente = tipoAttivitaImminente;
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
		this.dataScadenza=dataScadenza;
	}
}
