package de.morrigan.dev.gw2.dto.exceptions;

import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Diese Exception wird in der Service Schicht verwendet, um dort aufgetretene Fehler zu behandeln und diese an die GUI
 * Schicht zu melden. Mit dieser Exceptionklasse ist es auch möglich eine {@link PersistenceException} zu kapseln. In
 * der GUI wird diese Exception dann zur Anzeige gebracht.
 * 
 * @author morrigan
 */
public class ServiceException extends AbstractException {

	private static final long serialVersionUID = 1L;

	public ServiceException(final int errorCode) {
		super(errorCode);
	}

	public ServiceException(final int errorCode, final String message) {
		super(errorCode, message);
	}

	public ServiceException(final int errorCode, final String message, final Throwable cause) {
		super(errorCode, message, cause);
	}

	public ServiceException(final PersistenceException e) {
		super(e.getErrorCode(), e.getMessage(), e.getCause());
	}

	public ServiceException(final Throwable t) {
		super(t);
	}
}
