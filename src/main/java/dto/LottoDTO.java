package dto;

import model.TipoMorfologia;
import model.TipoTessitura;


public class LottoDTO {

	private int codLotto;
	private TipoTessitura tessitura;
	private int dimensioni;
	private double ph;
	private TipoMorfologia morfologia;
	private int altitudine;
	private String localita;
	private String comune;
	private String provincia;
	private boolean attivo;
	private int idProprietario;
	
	
	public LottoDTO (TipoTessitura tessitura, int dimensioni, double ph, TipoMorfologia morfologia, int altitudine, String località, String comune, String provincia, int idProprietario) {
		this.codLotto=0;
		this.tessitura=tessitura;
		this.dimensioni=dimensioni;
		this.ph=ph;
		this.morfologia=morfologia;
		this.altitudine=altitudine;
		this.localita=località;
		this.comune=comune;
		this.provincia=provincia;
		this.attivo=true;	
		this.idProprietario=idProprietario;
	}
	
	public LottoDTO(int codLotto, TipoTessitura tessitura, int dimensioni, double ph, TipoMorfologia morfologia,int altitudine,String localita, String comune, String provincia,int idProprietario, boolean attivo) {
		 	this.tessitura = tessitura;
		    this.dimensioni = dimensioni;
		    this.ph = ph;
		    this.morfologia = morfologia;
		    this.altitudine = altitudine;
		    this.localita = localita;
		    this.comune = comune;
		    this.provincia = provincia;
		    this.idProprietario = idProprietario;
		    this.codLotto = codLotto;
		    this.attivo = attivo;		  
    }
	
	public int getCodLotto() {
		return codLotto;
	}
	public void setCodLotto(int codLotto) {
		this.codLotto = codLotto;
	}
	public TipoTessitura getTessitura() {
		return tessitura;
	}
	public void setTessitura(TipoTessitura tessitura) {
		this.tessitura = tessitura;
	}
	public int getDimensioni() {
		return dimensioni;
	}
	public void setDimensioni(int dimensioni) {
		this.dimensioni = dimensioni;
	}
	public TipoMorfologia getMorfologia() {
		return morfologia;
	}
	public void setMorfologia(TipoMorfologia morfologia) {
		this.morfologia = morfologia;
	}
	public double getPh() {
		return ph;
	}
	public void setPh(double ph) {
		this.ph = ph;
	}
	public int getAltitudine() {
		return altitudine;
	}
	public void setAltitudine(int altitudine) {
		this.altitudine = altitudine;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String località) {
		this.localita = località;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public boolean isAttivo() {
		return attivo;
	}
	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	public int getIdProprietario() {
		return idProprietario;
	}
	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}
}
