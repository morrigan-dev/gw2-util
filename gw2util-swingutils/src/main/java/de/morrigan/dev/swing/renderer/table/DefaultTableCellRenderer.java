package de.morrigan.dev.swing.renderer.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import de.morrigan.dev.gw2.utils.enums.ActiveState;

public class DefaultTableCellRenderer implements TableCellRenderer {

	/** Farbe der Selektion in einer Tabelle */
	private static final Color SELECTION_BACKGROUND_COLOR = new Color(200, 220, 255);

	/** Farbe für Datensätze mit dem AktivStatus DELETED */
	private static final Color ACTIVE_STATE_DELETED_BACKGROUND_COLOR = new Color(255, 230, 230);

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		final JLabel result = new JLabel();
		result.setOpaque(false);
		result.setText(value.toString());
		result.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

		// Setze Farbe für die Selektion
		if (isSelected) {
			result.setOpaque(true);
			result.setBackground(SELECTION_BACKGROUND_COLOR);
		} else {
			// Setze Hintergrundfarbe in Abhängigkeit des AktivStatus
			if (table.getModel() instanceof IActiveStateModel) {
				final IActiveStateModel model = (IActiveStateModel) table.getModel();
				if (model.getActiveState(row) == ActiveState.DELETED) {
					result.setOpaque(true);
					result.setBackground(ACTIVE_STATE_DELETED_BACKGROUND_COLOR);
				} else {
					result.setBackground(null);
				}
			}
		}

		return result;
	}

}
