package de.morrigan.dev.swing.models.dialogs;

import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.morrigan.dev.swing.wrapper.ListenerWrapper;

public class ErrorDialog extends MessageDialog {

	private static final long serialVersionUID = 1L;

	public ErrorDialog(final Window window, final MessageDialogModel dialogModel,
			final ListenerWrapper<Object> listenerWrapper) {
		super(window, dialogModel, listenerWrapper);
	}

	@Override
	protected void layoutButtonPanel(final JPanel buttonPanel) {
		buttonPanel.add(new JLabel());
		buttonPanel.add(new JLabel());
		buttonPanel.add(this.btnOk);
	}
}
