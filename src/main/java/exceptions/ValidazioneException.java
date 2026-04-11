package exceptions;

public class ValidazioneException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errore;
	
	public ValidazioneException(String errore) {
		this.errore=errore;
	}

	public String getErrore() {
		return errore;
	}

}
