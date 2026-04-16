package dto;

public class InputIrrigazioneDTO {

    private String metodoIrrigazione;
    private String coltivatoreIrrigazione;
    private String dataInizioIrrigazione;
    private String dataFineIrrigazione;

    public InputIrrigazioneDTO(String metodoIrrigazione,String coltivatoriIrrigazione,String dataInizioIrrigazione,String dataFineIrrigazione) {
        this.metodoIrrigazione = metodoIrrigazione;
        this.coltivatoreIrrigazione = coltivatoriIrrigazione;
        this.dataInizioIrrigazione = dataInizioIrrigazione;
        this.dataFineIrrigazione = dataFineIrrigazione;
    }

    public String getMetodoIrrigazione() {
        return metodoIrrigazione;
    }

    public String getColtivatoreIrrigazione() {
        return coltivatoreIrrigazione;
    }

    public String getDataInizioIrrigazione() {
        return dataInizioIrrigazione;
    }

    public String getDataFineIrrigazione() {
        return dataFineIrrigazione;
    }
}
