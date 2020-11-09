package de.morrigan.dev.swing.factories;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import de.morrigan.dev.swing.components.gw2.GW2ComboBox;
import de.morrigan.dev.swing.renderer.table.DefaultTableCellRenderer;

public class ComponentFactory {

	/** Standardbreite eines GUI Elements */
	public static final int DEFAULT_WIDTH = 200;

	/**
	 * Diese Methode erzeugt und konifguriert eine JTable, die in der Administration verwendet wird.
	 * 
	 * @return eine JTable
	 */
	public static final JTable getDefaultTable() {
		final JTable tblResultTable = new JTable();
		tblResultTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
		tblResultTable.setShowHorizontalLines(false);
		tblResultTable.setShowVerticalLines(false);
		tblResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblResultTable.setFillsViewportHeight(true);
		tblResultTable.setIntercellSpacing(new Dimension(0, 0));
		return tblResultTable;
	}

	/**
	 * Diese Methode erzeugt und konfiguriert ein JPasswordField.
	 * <p>
	 * <b>Eigenschaften</b><br>
	 * <ul>
	 * <li>Preferred width: {@link ComponentFactory#DEFAULT_WIDTH}</li>
	 * </ul>
	 * 
	 * @return ein JPasswordField
	 */
	public static final JPasswordField getDefaultJPasswordField() {
		final JPasswordField tfResultPwField = new JPasswordField();
		final int height = tfResultPwField.getPreferredSize().height;
		tfResultPwField.setPreferredSize(new Dimension(DEFAULT_WIDTH, height));
		return tfResultPwField;
	}

	/**
	 * Diese Methode erzeugt und konfiguriert ein JTextField.
	 * <p>
	 * <b>Eigenschaften</b><br>
	 * <ul>
	 * <li>Preferred width: {@link ComponentFactory#DEFAULT_WIDTH}</li>
	 * </ul>
	 * 
	 * @return ein JTextField
	 */
	public static final JTextField getDefaultJTextField() {
		final JTextField tfResultTextField = new JTextField();
		final int height = tfResultTextField.getPreferredSize().height;
		tfResultTextField.setPreferredSize(new Dimension(DEFAULT_WIDTH, height));
		return tfResultTextField;
	}

	public static final <T> GW2ComboBox<T> getGW2ComboBox() {
		final GW2ComboBox<T> cbResult = new GW2ComboBox<>();
		cbResult.setBackground(new Color(0, 0, 0));
		cbResult.setForeground(new Color(250, 250, 250));
		final int height = cbResult.getPreferredSize().height;
		cbResult.setPreferredSize(new Dimension(DEFAULT_WIDTH, height));
		cbResult.setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				result.setBackground(Color.BLACK);
				result.setForeground(new Color(250, 250, 250));
				return result;
			}
		});

		return cbResult;
	}

	public static final JLabel getGW2Label() {
		final JLabel lblResult = new JLabel();
		lblResult.setForeground(new Color(250, 250, 250));
		return lblResult;
	}

	/**
	 * Diese Methode erzeugt und konfiguriert ein JTextArea Element, welches als mehrzeiliges Label verwendet werden kann.
	 * <p>
	 * <b>Eigenschaften</b><br>
	 * <ul>
	 * <li>LineWrap: true</li>
	 * <li>WrapStyleWord: true</li>
	 * <li>Editable: false</li>
	 * <li>Opaque: false</li>
	 * </ul>
	 * 
	 * @return eine mehrzeilige TextArea
	 */
	public static JTextArea getMultiLineLabel() {
		final JTextArea taResultTextArea = new JTextArea();
		taResultTextArea.setLineWrap(true);
		taResultTextArea.setWrapStyleWord(true);
		taResultTextArea.setEditable(false);
		taResultTextArea.setOpaque(false);
		return taResultTextArea;
	}

	private ComponentFactory() {
		super();
	}
}
