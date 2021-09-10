package de.morrigan.dev.gw2.exception;

import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Diese Exceptionklasse wird verwendet, wenn eine Datenbankabfrage kein Ergebnis geliefert hat.
 * 
 * @author morrigan
 */
public class NoResultException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public NoResultException() {
		super(AbstractException.DATABASE_NO_RESULT_EXCEPTION);
	}

	public NoResultException(final String message, final Throwable cause) {
		super(AbstractException.DATABASE_NO_RESULT_EXCEPTION, message, cause);
	}
}
