package dto;

public class InputLottoDTO {

	    private String tessitura;
	    private String dimensioni;
	    private String ph;
	    private String morfologia;
	    private String altitudine;
	    private String localita;
	    private String comune;
	    private String provincia;

	    public InputLottoDTO(String tessitura,String dimensioni,String ph,String morfologia,String altitudine,String localita,String comune,String provincia) {
	        this.tessitura = tessitura;
	        this.dimensioni = dimensioni;
	        this.ph = ph;
	        this.morfologia = morfologia;
	        this.altitudine = altitudine;
	        this.localita = localita;
	        this.comune = comune;
	        this.provincia = provincia;
	    }

	    public String getTessitura() {
	        return tessitura;
	    }

	    public String getDimensioni() {
	        return dimensioni;
	    }

	    public String getPh() {
	        return ph;
	    }

	    public String getMorfologia() {
	        return morfologia;
	    }

	    public String getAltitudine() {
	        return altitudine;
	    }

	    public String getLocalita() {
	        return localita;
	    }

	    public String getComune() {
	        return comune;
	    }

	    public String getProvincia() {
	        return provincia;
	    }
	
}
