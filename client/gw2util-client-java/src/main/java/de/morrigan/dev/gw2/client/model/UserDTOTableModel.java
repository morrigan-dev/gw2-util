package de.morrigan.dev.gw2.client.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.model.table.AbstractGW2TableModel;
import de.morrigan.dev.gw2.dto.admin.UserDTO;

public class UserDTOTableModel extends AbstractGW2TableModel<UserDTO> {

	private static final long serialVersionUID = 1L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(UserDTOTableModel.class);

	/** Anzahl der Spalten in diesem TableModel */
	private static final int COLUMN_COUNT = 2;

	public static final int COLUMN_USERNAME = 0;
	public static final int COLUMN_ACTIVE_STATUS = 1;

	public UserDTOTableModel(List<UserDTO> dataList) {
		super(dataList, COLUMN_COUNT);

		setColumnNames(new String[] { "username", "state" });
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		final UserDTO element = getElementAt(rowIndex);
		switch (columnIndex) {
			case COLUMN_USERNAME:
				return element.getUserName();
			case COLUMN_ACTIVE_STATUS:
				return element.getActiveState();
			default:
				LOG.warn("Es existiert keine Spalte mit dem Index {}", columnIndex);
				return "";
		}
	}

}
