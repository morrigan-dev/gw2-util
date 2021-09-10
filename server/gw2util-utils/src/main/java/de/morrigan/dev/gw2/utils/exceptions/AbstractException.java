package de.morrigan.dev.gw2.utils.exceptions;

/**
 * Diese abstrakte Klasse stellt das Grundgerüst für alle Exceptions dar. Sie beinhaltet alle verfügbaren Fehlercodes.
 * Neue Fehlercodes müssen hier als Konstante abgelegt werden und mit der Konstante erzeugt werden. Jedem Fehlercode ist
 * ein kurzer Fehlertext zugeordnet.
 * 
 * @author morrigan
 */
// TODO: Fehlercodes sollten nicht hier stehen, sondern in einer eigenen error-codes.properies, sodass über diese
// dann die Fehlertexte geladen werden können. Diese bilden dann den Fehlertext Header mit einer kurzen Fehler
// Überschrift. Eine ausführliche Beschreibung des Fehlers kann dann ebenfalls über einen weiteren Property Eintrag
// geladen werden.
// Erstellt von morrigan am 09.11.2020
public abstract class AbstractException extends Exception {

	private static final long serialVersionUID = 1L;

	/* Programmierer Fehlercodes (1 - 999) */
	public static final int UNEXPECTED_EXCEPTION = 1;
	public static final int TYPE_CHECK_EXCEPTION = 2;
	public static final int INVALID_ARRAY_SIZE = 3;
	public static final int CLONING_NOT_SUPPORTED = 4;
	public static final int ATTACHED_ENTITIES_ARE_NOT_SUPPORTED = 5;
	public static final int EXECUTION_STOPPED = 6;

	/* Datenbank Fehlercodes (1000 - 1999) */
	public static final int DATABASE_NO_RESULT_EXCEPTION = 1001;
	public static final int DATABASE_DELETE_FAILED_EXCEPTION = 1002;
	public static final int DATABASE_NO_ENTITY_OR_INVALID_PKEY = 1003;
	public static final int DATABASE_MERGE_FAILED_EXCEPTION = 1004;
	public static final int DATABASE_UNEXPECTED_EXCEPTION = 1005;
	public static final int DATABASE_NON_UNIQUE_EXCEPTION = 1006;
	public static final int DATABASE_ALREADY_EXIST_EXCEPTION = 1007;
	public static final int DATABASE_PERSIST_FAILED_EXCEPTION = 1008;
	public static final int DATABASE_MULTIPLE_CLIENTS_FOR_DEVICE = 1009;

	/* Serverseitige Fehldercodes (2000 - 2999) */
	public static final int SERVER_THROWS_EXCEPTION = 2000;
	public static final int SERVER_ACCESS_DENIED = 2001;
	public static final int SERVER_UPDATE_FROM_GW2DB_FAILED = 2002;
	public static final int SERVER_UPDATE_FROM_GWTDB_FAILED = 2003;

	/** Fehlercode für diese Exception */
	private final int errorCode;

	/**
	 * Erzeugt eine Exception mit lediglich einem Fehlercode.
	 * 
	 * @param errorCode Ein Fehlercode für diese Exception.
	 */
	public AbstractException(final int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	/**
	 * Erzeugt eine Exception mit einem Fehlercode und einer detaillierten Fehlermeldung.
	 * 
	 * @param errorCode Ein Fehlercode für diese Exception.
	 * @param errorMessage Eine ausführliche Beschreibung des aufgetretenen Fehlers.
	 */
	public AbstractException(final int errorCode, final String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	/**
	 * Erzeugt eine Exception mit einem Fehlercode, einer detaillierten Fehlermeldung und einer vorangegangenen Exception,
	 * die nun als Cause angehangen wird.
	 * 
	 * @param errorCode Ein Fehlercode für diese Exception.
	 * @param errorMessage Eine ausführliche Beschreibung des aufgetretenen Fehlers.
	 * @param cause Eine vorangegangene Exception.
	 */
	public AbstractException(final int errorCode, final String errorMessage, final Throwable cause) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
	}

	/**
	 * Erstellt aus einer beliebigen Exception eine LossyException. Ist die Exception eine LossyException wird deren
	 * Errorcode verwendet. Ansonsten wird UNEXPECTED_EXCEPTION genutzt.
	 * <p>
	 * ACHTUNG: Die Exception verliert dadurch etwaige Runtimeexceptioneigenschaften.
	 * 
	 * @param t Die zu konvertierende Exception
	 */
	public AbstractException(final Throwable t) {
		super(t.getMessage(), t);
		if (t instanceof AbstractException) {
			final AbstractException le = (AbstractException) t;
			this.errorCode = le.errorCode;
		} else {
			this.errorCode = AbstractException.UNEXPECTED_EXCEPTION;
		}
	}

	public int getErrorCode() {
		return this.errorCode;
	}
}
