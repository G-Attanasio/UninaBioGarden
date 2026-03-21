package model;

import java.util.ArrayList;

public class Coltura {

	private int codColtura;
	private String nome;
	private String specie;
	private String famiglia;
	private int tempoMaturazione;
	private String destinazioneUso;
	private String periodoIdeale;
	private ArrayList<Raccolta> listaRaccolte;
	private ArrayList<SeminaColtura> listaSemine;
	
	public Coltura (int codColtura,String nome, String specie, String famiglia, int tempoMaturazione, String destinazioneUso, String periodoIdeale) {
		this.codColtura=codColtura;
		this.nome=nome;
		this.specie=specie;
		this.famiglia=famiglia;
		this.tempoMaturazione=tempoMaturazione;
		this.destinazioneUso=destinazioneUso;
		this.periodoIdeale=periodoIdeale;
		this.listaRaccolte=new ArrayList<Raccolta>();
		this.listaSemine= new ArrayList<SeminaColtura>();
	}
	
	public void addRaccolta(Raccolta r) {
	    if (r != null && !this.listaRaccolte.contains(r)) {
	        this.listaRaccolte.add(r);
	        if(r.getColtura()==null) {
	        	r.setColtura(this);
	        }
	    }
	}

	public void addSemina(SeminaColtura sc) {
	    if (sc != null && !this.listaSemine.contains(sc)) {
	        this.listaSemine.add(sc);
	        if(sc.getColtura()==null) {
	        	sc.setColtura(this);
	        }
	    }
	}

	
	public int getCodColtura() {
		return codColtura;
	}
	public void setCodColtura(int codColtura) {
		this.codColtura = codColtura;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFamiglia() {
		return famiglia;
	}
	public void setFamiglia(String famiglia) {
		this.famiglia = famiglia;
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	public int getTempoMaturazione() {
		return tempoMaturazione;
	}
	public void setTempoMaturazione(int tempoMaturazione) {
		this.tempoMaturazione = tempoMaturazione;
	}
	public String getDestinazioneUso() {
		return destinazioneUso;
	}
	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}
	public String getPeriodoIdeale() {
		return periodoIdeale;
	}
	public void setPeriodoIdeale(String periodoIdeale) {
		this.periodoIdeale = periodoIdeale;
	}
	public ArrayList<Raccolta> getListaRaccolte() {
		return listaRaccolte;
	}
	public void setListaRaccolte(ArrayList<Raccolta> listaRaccolte) {
		this.listaRaccolte = listaRaccolte;
	}
	public ArrayList<SeminaColtura> getListaSemine(){
		return listaSemine;
	}
	public void setListaSemine( ArrayList<SeminaColtura> listaSemine) {
		this.listaSemine=listaSemine;
	}
	@Override
	public String toString() {
	    return nome; 
	}

}
