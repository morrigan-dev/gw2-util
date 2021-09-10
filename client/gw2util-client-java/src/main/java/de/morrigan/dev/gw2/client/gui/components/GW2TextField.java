package de.morrigan.dev.gw2.client.gui.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import de.morrigan.dev.swing.factories.ComponentFactory;

public class GW2TextField extends JTextField {

	private static final long serialVersionUID = 1L;

	private static final Color BACKGROUND = new Color(29, 28, 24, 150);
	private static final Color FOREGROUND = new Color(222, 222, 222);
	private static final Border BORDER = new LineBorder(Color.BLACK, 2);

	public GW2TextField() {
		super();

		configureGUI();
	}

	private void configureGUI() {
		setBackground(BACKGROUND);
		setForeground(FOREGROUND);
		setBorder(BORDER);
		final int height = getPreferredSize().height;
		setPreferredSize(new Dimension(ComponentFactory.DEFAULT_WIDTH, height));
	}
}
