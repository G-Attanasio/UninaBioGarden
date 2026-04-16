package exceptions;

import java.util.ArrayList;

public class ValidazioneException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ArrayList<String> errori;
	
	public ValidazioneException(ArrayList<String> errori) {
		this.errori=errori;
	}

	public ArrayList<String> getErrori() {
		return errori;
	}

}
