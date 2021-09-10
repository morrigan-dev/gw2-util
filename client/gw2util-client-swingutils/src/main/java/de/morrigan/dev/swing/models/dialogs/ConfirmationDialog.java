package de.morrigan.dev.swing.models.dialogs;

import java.awt.Window;

import javax.swing.JPanel;

import de.morrigan.dev.swing.wrapper.ListenerWrapper;

public class ConfirmationDialog extends MessageDialog {

	private static final long serialVersionUID = 1L;

	public ConfirmationDialog(final Window window, final MessageDialogModel dialogModel,
			final ListenerWrapper<Object> listenerWrapper) {
		super(window, dialogModel, listenerWrapper);
	}

	@Override
	protected void layoutButtonPanel(final JPanel buttonPanel) {
		buttonPanel.add(this.btnYes);
		buttonPanel.add(this.btnNo);
		buttonPanel.add(this.btnCancel);
	}
}
