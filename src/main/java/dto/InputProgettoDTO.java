package dto;

public class InputProgettoDTO {

    private String stagione;
    private String durata;
    private String dataInizio;

    public InputProgettoDTO(String stagione,String durata,String dataInizio) {
        this.stagione = stagione;
        this.durata = durata;
        this.dataInizio = dataInizio;
    }

    public String getPeriodo() {
        return stagione;
    }

    public String getDurata() {
        return durata;
    }

    public String getDataInizio() {
        return dataInizio;
    }
}
