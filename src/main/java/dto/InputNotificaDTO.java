package dto;

import java.util.ArrayList;

public class InputNotificaDTO {

    private String descrizioneVeloce;
    private String descrizione;
    private String tipoNotifica;
    private String livelloGravita;
    private String estensione;
    private String dataScadenza;
    private ArrayList<String> destinatari;

    public InputNotificaDTO(String descrizioneVeloce,String descrizione,String tipoNotifica,String livelloGravita,String estensione,String dataScadenza,ArrayList<String> destinatari) {
        this.descrizioneVeloce = descrizioneVeloce;
        this.descrizione = descrizione;
        this.tipoNotifica = tipoNotifica;
        this.livelloGravita = livelloGravita;
        this.estensione = estensione;
        this.dataScadenza = dataScadenza;
        this.destinatari = destinatari;
    }

    public String getDescrizioneVeloce() {
        return descrizioneVeloce;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getTipoNotifica() {
        return tipoNotifica;
    }

    public String getLivelloGravita() {
        return livelloGravita;
    }

    public String getEstensione() {
        return estensione;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public ArrayList<String> getDestinatari() {
        return destinatari;
    }
}
