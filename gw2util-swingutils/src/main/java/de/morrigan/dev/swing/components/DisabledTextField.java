package de.morrigan.dev.swing.components;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.UIManager;

public class DisabledTextField extends JTextField {

	private static final long serialVersionUID = 1L;

	/** Hintergundfarbe von deaktivierten TextFeldern */
	private static final Color DISABLED_COLOR = new Color(230, 230, 230);

	public DisabledTextField() {
		super();

		setEditable(false);
		setBackground(DISABLED_COLOR);
		setFocusable(false);
	}

	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			setBackground(DISABLED_COLOR);
		} else {
			setBackground(UIManager.getColor("TextField.inactiveBackground"));
		}
	}
}
