package dto;

public class InputRaccoltaDTO {

    private String quantitaPrevista;
    private String dataInizioRaccolta;
    private String dataFineRaccolta;
    private String metodoRaccolta;
    private String coltivatoreRaccolta;

    public InputRaccoltaDTO(String quantitaPrevista,String dataInizioRaccolta,String dataFineRaccolta,String metodoRaccolta,String coltivatoriRaccolta) {
        this.quantitaPrevista = quantitaPrevista;
        this.dataInizioRaccolta = dataInizioRaccolta;
        this.dataFineRaccolta = dataFineRaccolta;
        this.metodoRaccolta = metodoRaccolta;
        this.coltivatoreRaccolta = coltivatoriRaccolta;
    }

    public String getQuantitaPrevista() {
        return quantitaPrevista;
    }

    public String getDataInizioRaccolta() {
        return dataInizioRaccolta;
    }

    public String getDataFineRaccolta() {
        return dataFineRaccolta;
    }

    public String getMetodoRaccolta() {
        return metodoRaccolta;
    }

    public String getColtivatoreRaccolta() {
        return coltivatoreRaccolta;
    }
}
