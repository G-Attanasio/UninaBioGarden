package exceptions;

public class ErroreDatabaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public ErroreDatabaseException() {
		super("Errore database: operazione fallita.");
	}
}
