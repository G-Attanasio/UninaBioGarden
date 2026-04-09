package dto;

public class ColturaDTO {

	private int codColtura;
	private String nome;
	private String specie;
	private String famiglia;
	private int tempoMaturazione;
	private String destinazioneUso;
	private String periodoIdeale;
	
	public ColturaDTO (int codColtura,String nome, String specie, String famiglia, int tempoMaturazione, String destinazioneUso, String periodoIdeale) {
		this.codColtura=codColtura;
		this.nome=nome;
		this.specie=specie;
		this.famiglia=famiglia;
		this.tempoMaturazione=tempoMaturazione;
		this.destinazioneUso=destinazioneUso;
		this.periodoIdeale=periodoIdeale;
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
}
