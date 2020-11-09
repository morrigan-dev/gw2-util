package de.morrigan.dev.gw2.utils.exceptions;

/**
 * Diese Exceptionklasse dient dazu dem Benutzer über eine aktuelle Situation zu informieren und auf eine Bestätigung zu
 * warten. Diese Exception wird von einem speziellen Dialog behandelt, der mehrere Optionen enthalten kann, von denen
 * der Benutzer eine auswählen muss, um fortzufahren.<br>
 * Dies kann beispielsweise ein "ja/nein" Dialog sein mit der Frage "Möchten Sie die Aktion XYZ wirklich ausführen?".
 * <p>
 * Der <code>notificationCode</code> ist gleichbedeutend mit dem <code>errorCode</code> aus der Oberklasse, über den die
 * Fehlermeldung geladen wird.
 * 
 * @author morrigan
 */
public class ConfirmationException extends AbstractException {

	private static final long serialVersionUID = 1L;

	/** siehe {@link AbstractException#LossyException(int)  */
	public ConfirmationException(final int notificationCode) {
		super(notificationCode);
	}

	/** siehe {@link AbstractException#LossyException(int, String)  */
	public ConfirmationException(final int notificationCode, final String detailMessage) {
		super(notificationCode, detailMessage);
	}

	public int getNotificationCode() {
		return getErrorCode();
	}
}
