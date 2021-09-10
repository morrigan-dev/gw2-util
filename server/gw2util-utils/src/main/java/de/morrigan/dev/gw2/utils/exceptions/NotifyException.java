package de.morrigan.dev.gw2.utils.exceptions;

/**
 * Diese Exceptionklasse dient dazu reine Hinweise an den Benutzer zu handeln. Sie wird von einem speziellen Dialog mit
 * entsprechendem Icon behandelt, der lediglich die enthaltene Information der Exception zur Anzeige bringt.<br>
 * Dies kann beispielsweise die Meldung "Sie besitzen nicht die nötigen Rechte, um diese Aktion durchführen zu können."
 * sein, die der Benutzer lediglich mit einem OK button bestätigen kann.
 * <p>
 * Der <code>notificationCode</code> ist gleichbedeutend mit dem <code>errorCode</code> aus der Oberklasse, über den die
 * Fehlermeldung geladen wird.
 * 
 * @author morrigan
 */
public class NotifyException extends AbstractException {

	private static final long serialVersionUID = 1L;

	public NotifyException(final int notificationCode) {
		super(notificationCode);
	}

	public NotifyException(final int notificationCode, final String detailMessage) {
		super(notificationCode, detailMessage);
	}

	public int getNotificationCode() {
		return getErrorCode();
	}

}
