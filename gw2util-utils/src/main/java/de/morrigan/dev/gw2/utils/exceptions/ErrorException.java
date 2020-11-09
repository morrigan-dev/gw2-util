package de.morrigan.dev.gw2.utils.exceptions;

/**
 * Diese Exceptionklasse dient dazu clientseitige Fehler zu behandeln. Sie wird von einem speziellen Dialog mit
 * entsprechendem Icon behandelt, der lediglich die enthaltenen Fehlerhinweise der Exception zur Anzeige bringt.<br>
 * 
 * @author morrigan
 */
public class ErrorException extends AbstractException {

	private static final long serialVersionUID = 1L;

	/** siehe {@link AbstractException#AException(int)  */
	public ErrorException(final int errorCode) {
		super(errorCode);
	}

	/** siehe {@link AbstractException#AException(int, String)  */
	public ErrorException(final int errorCode, final String detailMessage) {
		super(errorCode, detailMessage);
	}
}
