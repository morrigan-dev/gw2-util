package de.morrigan.dev.swing.factories;

import java.awt.Window;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import de.morrigan.dev.gw2.resources.ImageManager;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.exceptions.ConfirmationException;
import de.morrigan.dev.gw2.utils.exceptions.NotifyException;
import de.morrigan.dev.swing.models.dialogs.ConfirmationDialog;
import de.morrigan.dev.swing.models.dialogs.ErrorDialog;
import de.morrigan.dev.swing.models.dialogs.MessageDialogModel;
import de.morrigan.dev.swing.models.dialogs.NotificationDialog;
import de.morrigan.dev.swing.wrapper.ListenerWrapper;

/**
 * Dieser Dialog zeigt Informationen aus einer {@link AbstractException} an. Als Kennzeichen, dass es sich um einen
 * unerwarteten Fehler handelt, zeigt dieser Dialog ein rotes Symbol mit einem X an.
 * 
 * @author morrigan
 */
public class MessageDialogFactory extends JOptionPane {

	private static final long serialVersionUID = 1L;

	/** Handel auf den ImageManager */
	private static final ImageManager IMAGE_MANAGER = ImageManager.getInstance();

	/** Handle auf den ResourceManager */
	private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

	/**
	 * Diese Methode zeigt einen Dialog an, der je nach �bergebenen Exception ein anderes Aussehen und eine andere
	 * Funktionalit�t bietet. Insgesamt sind vier verschiede Dialoge verf�gbar.
	 * <p>
	 * <ul>
	 * <li>Hinweis-Dialog: Diese Dialog wird angezeigt, wenn eine {@link NotifyException} �bergeben wird. Der Dialog
	 * zeigt den Hinweis aus der Exception an und bietet die M�glichkeit mit OK zu best�tigen. Als Antwort wird
	 * {@link JOptionPane#OK_OPTION} zur�ckgegeben.</li>
	 * <li>Frage-Dialog: Dieser Dialog wird angezeigt, wenn eine {@link ConfirmationException} �bergeben wird. Der
	 * Dialog zeigt die Frage aus der Exception an und bietet die M�glichkeiten mit Ja/Nein/Abbrechen zu best�tigen. Als
	 * Antwort wird entsprechend {@link JOptionPane#YES_OPTION}/{@link JOptionPane#NO_OPTION}/
	 * {@link JOptionPane#CANCEL_OPTION} zur�ckgegeben.</li>
	 * <li>Fehler-Dialog: Dieser Dialog wird in zwei F�llen angezeigt. Zum Einen kann eine {@link AbstractException}
	 * behandelt werden und zum Anderen ein beliebiges {@link Throwable}. Beim ersten fall, besitzt die Exception noch
	 * einen ErrorCode, der in einen Fehlertext �bersetzt wird. Beim zweiten Fall wird immer ein interner Fehler
	 * gemeldet zusammen mit einer Message aus der Exception. Der Dialog kann mit einem OK best�tigt werden. Als Antwort
	 * wird {@link JOptionPane#OK_OPTION} zur�ckgegeben.</li>
	 * </ul>
	 * 
	 * @author morrigan
	 * @param parent Die GUI Komponente, auf dem der Dialog liegt.
	 * @param exception eine Exception f�r Best�tigungen.
	 * @param headerColor Die Farbe des obigen Banners
	 * @param autoDispose Nach dieser Zeit wird der Dialog disposed, au�er es ist ein Wert<=0 angegeben
	 */
	public static void handleExcpetion(final Window mainWindow, final Throwable exception,
			final ListenerWrapper<Object> listenerWrapper) {
		// TODO DefaultLocale wieder setzen, wenn im LabelManager die Sprache gesetzt werden kann. (morrigan,
		// 28.03.2013)
		// setDefaultLocale(RESOURCE_MANAGER.getCurrentLocale());
		if (exception instanceof NotifyException) {
			showNotificationDialog(mainWindow, (NotifyException) exception, listenerWrapper);
		} else if (exception instanceof ConfirmationException) {
			showConfirmationDialog(mainWindow, (ConfirmationException) exception, listenerWrapper);
		} else if (exception instanceof AbstractException) {
			showAbstractExceptionDialog(mainWindow, (AbstractException) exception, listenerWrapper);
		} else {
			showErrorDialog(mainWindow, exception, listenerWrapper);
		}
	}

	/**
	 * @param mainWindow
	 * @param messageHeader
	 * @param message
	 * @param messageDetail
	 * @param headerColor Die Farbe des obigen Banners
	 * @param autoDispose Nach dieser Zeit wird der Dialog disposed, au�er es ist ein Wert<=0 angegeben
	 */
	public static void showConfirmationDialog(final Window mainWindow, final ListenerWrapper<Object> listenerWrapper,
			final String messageHeader, final String message, final String messageDetail) {
		final Icon icon = IMAGE_MANAGER.getImageIcon(ImageManager.CONFIRMATION_ICON);
		final String title = RESOURCE_MANAGER.getLabel("confirmation");
		final MessageDialogModel dialogModel = new MessageDialogModel(icon, title, messageHeader, message,
				messageDetail);
		new ConfirmationDialog(mainWindow, dialogModel, listenerWrapper);
	}

	/**
	 * @param mainWindow
	 * @param messageHeader
	 * @param message
	 * @param messageDetail
	 * @param headerColor Die Farbe des obigen Banners
	 * @param autoDispose Nach dieser Zeit wird der Dialog disposed, au�er es ist ein Wert<=0 angegeben
	 */
	public static void showInformationDialog(final Window mainWindow, final ListenerWrapper<Object> listenerWrapper,
			final String messageHeader, final String message, final String messageDetail) {
		final Icon icon = IMAGE_MANAGER.getImageIcon(ImageManager.NOTIFICATION_ICON);
		final String title = RESOURCE_MANAGER.getLabel("notification");
		final MessageDialogModel dialogModel = new MessageDialogModel(icon, title, messageHeader, message,
				messageDetail);
		new NotificationDialog(mainWindow, dialogModel, listenerWrapper);
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

	private static void showAbstractExceptionDialog(final Window mainWindow, final AbstractException exception,
			final ListenerWrapper<Object> listenerWrapper) {
		final Icon icon = IMAGE_MANAGER.getImageIcon(ImageManager.ERROR_ICON);
		final String title = RESOURCE_MANAGER.getLabel("error");
		final String errorCodeMsg = getErrMsg(exception.getErrorCode());
		String detailMsg = exception.getMessage();
		if (detailMsg == null) {
			detailMsg = "";
		}
		final MessageDialogModel dialogModel = new MessageDialogModel(icon, title, title, errorCodeMsg, detailMsg);
		new ErrorDialog(mainWindow, dialogModel, listenerWrapper);
	}

	private static void showConfirmationDialog(final Window mainWindow, final ConfirmationException exception,
			final ListenerWrapper<Object> listenerWrapper) {
		final Icon icon = IMAGE_MANAGER.getImageIcon(ImageManager.CONFIRMATION_ICON);
		final String title = RESOURCE_MANAGER.getLabel("confirmation");
		final String errorCodeMsg = getErrMsg(exception.getNotificationCode());
		String detailMsg = exception.getMessage();
		if (detailMsg == null) {
			detailMsg = "";
		}
		final MessageDialogModel dialogModel = new MessageDialogModel(icon, title, title, errorCodeMsg, detailMsg);
		new ConfirmationDialog(mainWindow, dialogModel, listenerWrapper);
	}

	private static void showErrorDialog(final Window mainWindow, final Throwable exception,
			final ListenerWrapper<Object> listenerWrapper) {
		final Icon icon = IMAGE_MANAGER.getImageIcon(ImageManager.ERROR_ICON);
		final String title = RESOURCE_MANAGER.getLabel("error");
		final String errorCodeMsg = getErrMsg(AbstractException.UNEXPECTED_EXCEPTION);
		String detailMsg = exception.getMessage();
		if (detailMsg == null) {
			detailMsg = "";
		}
		final MessageDialogModel dialogModel = new MessageDialogModel(icon, title, title, errorCodeMsg, detailMsg);
		new ErrorDialog(mainWindow, dialogModel, listenerWrapper);
	}

	private static void showNotificationDialog(final Window mainWindow, final NotifyException exception,
			final ListenerWrapper<Object> listenerWrapper) {
		final Icon icon = IMAGE_MANAGER.getImageIcon(ImageManager.NOTIFICATION_ICON);
		final String title = RESOURCE_MANAGER.getLabel("notification");
		final String errorCodeMsg = getErrMsg(exception.getNotificationCode());
		String detailMsg = exception.getMessage();
		if (detailMsg == null) {
			detailMsg = "";
		}
		final MessageDialogModel dialogModel = new MessageDialogModel(icon, title, title, errorCodeMsg, detailMsg);
		new NotificationDialog(mainWindow, dialogModel, listenerWrapper);
	}
}
