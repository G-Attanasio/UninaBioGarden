package dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class AttivitaImminenteDTO extends NotificaDTO {

	private String tipoAttivitaImminente;
	private String descrizione;
	private LocalDate dataScadenza;
	private String tipo;

	public AttivitaImminenteDTO( LocalDate dataInvio, int idCreatore, String tipoAttivitaImminente, String descrizione,LocalDate dataScadenza, ArrayList<String> destinatari) {
		super(dataInvio, idCreatore,destinatari);
		this.tipoAttivitaImminente=tipoAttivitaImminente;
		this.descrizione=descrizione;
		this.dataScadenza=dataScadenza;
	}
	
	public AttivitaImminenteDTO(int codNotifica, LocalDate dataInvio,int idCreatore, String tipoAttivitaImminente, String descrizione, LocalDate dataScadenza, ArrayList<String> destinatari) {
		super(codNotifica,dataInvio,idCreatore,destinatari);
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
