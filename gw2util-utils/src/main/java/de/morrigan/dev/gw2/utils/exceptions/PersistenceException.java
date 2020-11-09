package de.morrigan.dev.gw2.utils.exceptions;

/**
 * Diese Exceptionklasse wird verwendet, um Fehler in der Persistence Schicht (DAOs) zu behandeln. Dabei werden
 * Exception aus Fremdlibraries beim Zugriff auf z.B. Datenbanken direkt an der auftretenden Stelle gefangen und in
 * einer {@code PersistenceException} gekapselt.<br>
 * Diese Exception darf maximal bis an die Service Schicht gereicht werden. Danach muss sie in eine
 * {@code ServiceException} umgewandelt werden!
 * 
 * @author morrigan
 */
public class PersistenceException extends AbstractException {

	private static final long serialVersionUID = 1L;

	public PersistenceException(final int errorCode) {
		super(errorCode);
	}

	public PersistenceException(final int errorCode, final String message) {
		super(errorCode, message);
	}

	public PersistenceException(final int errorCode, final String message, final Throwable cause) {
		super(errorCode, message, cause);
	}

	public PersistenceException(final Throwable t) {
		super(t);
	}
}
