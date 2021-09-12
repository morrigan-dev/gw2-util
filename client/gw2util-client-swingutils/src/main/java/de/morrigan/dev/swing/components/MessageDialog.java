package de.morrigan.dev.swing.components;

import java.awt.Component;

import javax.swing.JOptionPane;

import de.morrigan.dev.gw2.resources.ImageConstants;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.exceptions.ConfirmationException;
import de.morrigan.dev.gw2.utils.exceptions.NotifyException;

/**
 * Dieser Dialog zeigt Informationen aus einer {@link AbstractException} an. Als Kennzeichen, dass es sich um einen
 * unerwarteten Fehler handelt, zeigt dieser Dialog ein rotes Symbol mit einem X an.
 * 
 * @author morrigan
 */
public class MessageDialog extends JOptionPane {

	private static final long serialVersionUID = 1L;

	/** Handel auf den ImageManager */
	private static final ImageConstants IMAGE_MANAGER = ImageConstants.getInstance();

	/** Handle auf den ResourceManager */
	private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

	/**
	 * Diese Methode zeigt einen Dialog an, der je nach übergebenen Exception ein anderes Aussehen und eine andere
	 * Funktionalität bietet. Insgesamt sind vier verschiede Dialoge verfügbar.
	 * <p>
	 * <ul>
	 * <li>Hinweis-Dialog: Diese Dialog wird angezeigt, wenn eine {@link NotifyException} übergeben wird. Der Dialog zeigt
	 * den Hinweis aus der Exception an und bietet die Möglichkeit mit OK zu bestätigen. Als Antwort wird
	 * {@link JOptionPane#OK_OPTION} zurückgegeben.</li>
	 * <li>Frage-Dialog: Dieser Dialog wird angezeigt, wenn eine {@link ConfirmationException} übergeben wird. Der Dialog
	 * zeigt die Frage aus der Exception an und bietet die Möglichkeiten mit Ja/Nein/Abbrechen zu bestätigen. Als Antwort
	 * wird entsprechend {@link JOptionPane#YES_OPTION}/{@link JOptionPane#NO_OPTION}/ {@link JOptionPane#CANCEL_OPTION}
	 * zurückgegeben.</li>
	 * <li>Fehler-Dialog: Dieser Dialog wird in zwei Fällen angezeigt. Zum Einen kann eine {@link AbstractException}
	 * behandelt werden und zum Anderen ein beliebiges {@link Throwable}. Beim ersten fall, besitzt die Exception noch
	 * einen ErrorCode, der in einen Fehlertext übersetzt wird. Beim zweiten Fall wird immer ein interner Fehler gemeldet
	 * zusammen mit einer Message aus der Exception. Der Dialog kann mit einem OK bestätigt werden. Als Antwort wird
	 * {@link JOptionPane#OK_OPTION} zurückgegeben.</li>
	 * </ul>
	 * 
	 * @author morrigan
	 * @param parent Die GUI Komponente, auf dem der Dielog liegt.
	 * @param exception eine Exception für Bestätigungen.
	 * @return Einen Bestätigungscode ({@link JOptionPane#OK_OPTION}, {@link JOptionPane#NO_OPTION},
	 *         {@link JOptionPane#CANCEL_OPTION})
	 */
	public static int handleExcpetion(final Component parent, final Throwable exception) {
		setDefaultLocale(RESOURCE_MANAGER.getCurrentLocale());
		if (exception instanceof NotifyException) {
			return showNotificationDialog(parent, (NotifyException) exception);
		} else if (exception instanceof ConfirmationException) {
			return showConfirmationDialog(parent, (ConfirmationException) exception);
		} else if (exception instanceof AbstractException) {
			return showServiceExceptionDialog(parent, (AbstractException) exception);
		} else {
			return showErrorDialog(parent, exception);
		}
	}

	/**
	 * Diese Methode liefert zu einem Fehlercode den entsprechenen Fehlertext.
	 * 
	 * @param errorCode Ein Fehlercode.
	 * @return Ein Fehlertext.
	 */
	private static String getErrMsg(final int errorCode) {
		final StringBuilder errMsg = new StringBuilder();
		errMsg.append("<html><b>");
		errMsg.append(RESOURCE_MANAGER.getErrorMessage(errorCode));
		errMsg.append("</b></html>");
		return errMsg.toString();
	}

	private static int showConfirmationDialog(final Component parent, final ConfirmationException exception) {
		final String title = RESOURCE_MANAGER.getLabel("confirmation");
		final String errorCodeMsg = getErrMsg(exception.getNotificationCode());
		final String detailMsg = exception.getMessage();
		return showConfirmDialog(parent, new Object[] { errorCodeMsg, detailMsg }, title,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				IMAGE_MANAGER.getImageIcon(ImageConstants.CONFIRMATION_ICON));
	}

	private static int showErrorDialog(final Component parent, final Throwable exception) {
		final String title = RESOURCE_MANAGER.getLabel("error");
		final String errorCodeMsg = getErrMsg(AbstractException.UNEXPECTED_EXCEPTION);
		final String detailMsg = exception.getMessage();
		showMessageDialog(parent, new Object[] { errorCodeMsg, detailMsg }, title, JOptionPane.ERROR_MESSAGE,
				IMAGE_MANAGER.getImageIcon(ImageConstants.ERROR_ICON));
		return JOptionPane.OK_OPTION;
	}

	private static int showNotificationDialog(final Component parent, final NotifyException exception) {
		final String title = RESOURCE_MANAGER.getLabel("notification");
		final String errorCodeMsg = getErrMsg(exception.getNotificationCode());
		final String detailMsg = exception.getMessage();
		showMessageDialog(parent, new Object[] { errorCodeMsg, detailMsg }, title, JOptionPane.INFORMATION_MESSAGE,
				IMAGE_MANAGER.getImageIcon(ImageConstants.NOTIFICATION_ICON));
		return JOptionPane.OK_OPTION;
	}

	private static int showServiceExceptionDialog(final Component parent, final AbstractException exception) {
		final String title = RESOURCE_MANAGER.getLabel("error");
		final String errorCodeMsg = getErrMsg(exception.getErrorCode());
		final String detailMsg = exception.getMessage();
		showMessageDialog(parent, new Object[] { errorCodeMsg, detailMsg }, title, JOptionPane.ERROR_MESSAGE,
				IMAGE_MANAGER.getImageIcon(ImageConstants.ERROR_ICON));
		return JOptionPane.OK_OPTION;
	}
}
