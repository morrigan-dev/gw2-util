package de.morrigan.dev.swing.renderer.list;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import de.morrigan.dev.gw2.utils.model.interfaces.IComboBoxItem;


public class DefaultComboBoxLabelRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	/** Farbe der Selektion in einer Tabelle */
	private static final Color SELECTION_BACKGROUND_COLOR = new Color(200, 220, 255);

	@Override
	public Component getListCellRendererComponent(final JList list, final Object value, final int index,
			final boolean isSelected, final boolean cellHasFocus) {
		final JLabel result = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		final IComboBoxItem item = (IComboBoxItem) value;
		result.setText(item != null ? item.getComboBoxLabel() : "");
		result.setOpaque(true);
		if (isSelected) {
			result.setBackground(SELECTION_BACKGROUND_COLOR);
		} else {
			result.setBackground(null);
		}
		return result;
	}

}
