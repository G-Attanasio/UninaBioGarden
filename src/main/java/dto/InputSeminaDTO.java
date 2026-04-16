package dto;

public class InputSeminaDTO {

    private String quantitaSemi;
    private String dataInizioSemina;
    private String dataFineSemina;
    private String metodoSemina;
    private String coltivatoreSemina;

    public InputSeminaDTO(String quantitaSemi,String dataInizioSemina,String dataFineSemina,String metodoSemina,String coltivatoriSemina) {
        this.quantitaSemi = quantitaSemi;
        this.dataInizioSemina = dataInizioSemina;
        this.dataFineSemina = dataFineSemina;
        this.metodoSemina = metodoSemina;
        this.coltivatoreSemina = coltivatoriSemina;
    }

    public String getQuantitaSemi() {
        return quantitaSemi;
    }

    public String getDataInizioSemina() {
        return dataInizioSemina;
    }

    public String getDataFineSemina() {
        return dataFineSemina;
    }

    public String getMetodoSemina() {
        return metodoSemina;
    }

    public String getColtivatoreSemina() {
        return coltivatoreSemina;
    }
}
