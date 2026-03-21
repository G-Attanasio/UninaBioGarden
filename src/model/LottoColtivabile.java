package model;

import java.util.ArrayList;

public class LottoColtivabile {

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
	private Utente proprietario;
	private ArrayList<ProgettoStagionale> progettiAssegnati;
	
	public LottoColtivabile (TipoTessitura tessitura, int dimensioni, double ph, TipoMorfologia morfologia, int altitudine, String località, String comune, String provincia, Utente proprietario) {
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
		this.progettiAssegnati=new ArrayList<>();
		this.proprietario=proprietario;
	}
	
	public LottoColtivabile(int codLotto, TipoTessitura tessitura, int dimensioni, double ph, TipoMorfologia morfologia,int altitudine,String localita, String comune, String provincia,Utente proprietario, boolean attivo) {
		 	this.tessitura = tessitura;
		    this.dimensioni = dimensioni;
		    this.ph = ph;
		    this.morfologia = morfologia;
		    this.altitudine = altitudine;
		    this.localita = localita;
		    this.comune = comune;
		    this.provincia = provincia;
		    this.proprietario = proprietario;
		    this.codLotto = codLotto;
		    this.attivo = attivo;
		    this.progettiAssegnati = new ArrayList<>(); 
    }
	
	public void addProprietario(Utente u) {
		this.setProprietario(u);
	}
	
	public void addProgetto(ProgettoStagionale ps) {
		if(ps!=null && !this.progettiAssegnati.contains(ps)) {
			progettiAssegnati.add(ps);
		if(ps.getLottoImpegnato()==null) {
			ps.setLottoImpegnato(this);
			}
		}
	}
	
	public boolean isValidPh(double ph) {
		if (ph <4.0 || ph>9.0) return false;
		return true;
	}
	
	public boolean isValidDimensioni(int dimensione) {
		if(dimensioni <100 || dimensioni >  1000000) return false;
		return true;
	}
	
	public boolean isLunghezzaValida(String stringa) {
		if (stringa==null || stringa.length()>= 50 || stringa.isEmpty()) return false;
		return true;
	}
	
	public boolean isLunghezzaProvinciaValida(String testo) {
		if(testo==null || testo.length()!=2 || testo.isEmpty()) return false;
		return true;
	}

	public boolean isSoloLettere(String testo) {
	    for (int i = 0; i < testo.length(); i++) {
	        char c = testo.charAt(i);
	        if (!Character.isLetter(c) && !Character.isWhitespace(c) && c != '\'') {
	            return false; 
	        }
	    }
	    return true;
	}
	
	public boolean isSoloCifre(String testo ) {
		if (testo == null || testo.isEmpty()) return false;
	    int contatorePunti = 0;
	    for (int i = 0; i < testo.length(); i++) {
	        char c = testo.charAt(i);
	        if (c == '.') {
	            contatorePunti++;
	        } else if (!Character.isDigit(c)) {
	            return false;
	        }
	        if (contatorePunti > 1) return false;
	    }
	    return !testo.equals(".");
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
	public double getDimensioni() {
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

	public Utente getProprietario() {
		return proprietario;
	}

	public void setProprietario(Utente proprietario) {
		this.proprietario = proprietario;
	}
	
	public ArrayList<ProgettoStagionale> getProgettiAssegnati(){
		return progettiAssegnati;
	}
	
	public void setProgettiAssegnati(ArrayList<ProgettoStagionale> lista) {
		this.progettiAssegnati=lista;
	}
}
