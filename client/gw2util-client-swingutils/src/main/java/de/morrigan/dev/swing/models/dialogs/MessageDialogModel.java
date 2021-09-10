package de.morrigan.dev.swing.models.dialogs;

import javax.swing.Icon;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.swing.models.AbstractModel;

/**
 * Dieses Model ist für alle Fehler/Hinweis/Frage Dialoge da, um die Daten, die der modale Dialog während der Erzeugung
 * benötigt bereitzustellen.
 * 
 * @author morrigan
 */
public class MessageDialogModel extends AbstractModel {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(MessageDialogModel.class);

	/** Ein Icon, welches im Dialog angezeigt werden soll */
	private Icon icon;

	/** Ein Titel des Dialogfensters */
	private String title;

	/** Eine Beschriftung für den Dialogkopf */
	private String dialogHeaderText;

	/** Eine kurze Fehlerbeschreibung */
	private String errorMsg;

	/** Eine ausführliche Fehlerbeschreibung */
	private String detailMsg;

	/** Eine aufgetretene Exception */
	private Throwable exception;

	/** Timeout in Sekunden nach dem sich der Dialog automatisch schließt */
	private int disposeTimeout;

	/**
	 * Erzeugt ein neues Model und befüllt dieses mit den angegebenen Werten.
	 * 
	 * @param icon Ein Icon, welches im Dialog angezeigt werden soll (not null)
	 * @param title Ein Titel des Dialogfensters.
	 * @param dialogHeaderText Eine Beschriftung für den Dialogkopf. (not null)
	 * @param errorMsg Eine kurze Fehlerbeschreibung. (not null)
	 * @param detailMsg Eine ausführliche Fehlerbeschreibung.
	 */
	public MessageDialogModel(final Icon icon, final String title, final String dialogHeaderText,
			final String errorMsg, final String detailMsg) {
		super();
		Validate.notNull(icon, "Folgender Parameter darf nicht null sein! icon: {}", icon);
		Validate.notNull(dialogHeaderText, "Folgender Parameter darf nicht null sein! dialogHeaderText: {}",
				dialogHeaderText);
		Validate.notNull(errorMsg, "Folgender Parameter darf nicht null sein! errorMsg: {}", errorMsg);
		LOG.debug("icon: {}, title: {}, dialogHeaderText: {}, errorMsg: {}, detailMsg: {}", icon, title, dialogHeaderText,
				errorMsg, detailMsg);

		this.icon = icon;
		this.title = title;
		this.dialogHeaderText = dialogHeaderText;
		this.errorMsg = errorMsg;
		this.detailMsg = detailMsg;
	}

	public String getDetailMsg() {
		return this.detailMsg;
	}

	public String getDialogHeaderText() {
		return this.dialogHeaderText;
	}

	public int getDisposeTimeout() {
		return this.disposeTimeout;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public Throwable getException() {
		return this.exception;
	}

	public Icon getIcon() {
		return this.icon;
	}

	public String getTitle() {
		return this.title;
	}

	public void setDetailMsg(final String detailMsg) {
		this.detailMsg = detailMsg;
	}

	public void setDialogHeaderText(final String dialogHeaderText) {
		this.dialogHeaderText = dialogHeaderText;
	}

	public void setDisposeTimeout(final int disposeTimeout) {
		this.disposeTimeout = disposeTimeout;
	}

	public void setErrorMsg(final String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setException(final Throwable exception) {
		this.exception = exception;
	}

	public void setIcon(final Icon icon) {
		this.icon = icon;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
