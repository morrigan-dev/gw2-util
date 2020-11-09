package de.morrigan.dev.swing.models.table;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.swing.interfaces.table.ITableModel;

/**
 * Dieses Tabellenmodel stellt das Basismodel für alle Tabellenmodell dar. Es enthält allgemeine Funktionalität, die in
 * allen Tabellenmodellen benötigt wird.
 * 
 * @param <T> Der Datentyp der Elemente, die eine Zeile der Tabelle darstellen.
 * @author morrigan
 */
public abstract class AbstractBaseTableModel<T> extends AbstractTableModel implements ITableModel<T> {

	private static final long serialVersionUID = 1L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractBaseTableModel.class);
	
	/** Handle auf den ResourceManager */
	protected static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

	/** Liste mit den einzelnen Entities, wobei jeder Satz einer Zeile in der Tabelle entspricht */
	private List<T> dataList = new ArrayList<T>();

	/** Anzahl der Spalten in der Tabelle */
	private final int columnCount;

	/** Überschriften in den Tabellenheadern */
	private String[] columnNames;

	public AbstractBaseTableModel(final List<T> dataList, final int columnCount) {
		super();
		LOG.debug("dataList: {}, columnCount: {}", dataList, columnCount);

		this.dataList = dataList;
		this.columnCount = columnCount;
		this.columnNames = new String[columnCount];
	}

	@Override
	public void addElement(final T element) {
		LOG.debug("element: {}", element);
		this.dataList.add(element);
		final int updatedRowIndex = this.dataList.size() - 1;
		fireTableRowsInserted(updatedRowIndex, updatedRowIndex);
	}

	@Override
	public void clear() {
		this.dataList.clear();
		fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return this.columnCount;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		LOG.debug("columnIndex: {}", columnIndex);
		return RESOURCE_MANAGER.getLabel(this.columnNames[columnIndex]);
	}

	public List<T> getDataList() {
		return this.dataList;
	}

	@Override
	public T getElementAt(final int rowIndex) {
		if ((rowIndex > -1) && (rowIndex < this.dataList.size())) {
			return this.dataList.get(rowIndex);
		}
		return null;
	}

	@Override
	public int getRowCount() {
		return this.dataList.size();
	}

	@Override
	public boolean removeElement(final T elementToRemove) {
		final int indexToRemove = this.dataList.indexOf(elementToRemove);
		final boolean result = this.dataList.remove(elementToRemove);
		if (result) {
			fireTableRowsDeleted(indexToRemove, indexToRemove);
		}
		return result;
	}

	/**
	 * Setzt eine neue Datenliste ins Model und veranlasst, dass sich die GUI Tabelle aktualisiert.
	 * 
	 * @param dataList eine neue Datenliste. (not null)
	 * @author morrigan
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setDataList(final List<T> dataList) {
		Validate.notNull(dataList, "Der Parameter (dataList) darf nicht null sein!");
		LOG.debug("dataList: {}", dataList);
		
		this.dataList = dataList;
		fireTableDataChanged();
	}

	@Override
	public void setElementAt(final int indexToInsert, final T elementToAdd) {
		this.dataList.set(indexToInsert, elementToAdd);
		fireTableRowsUpdated(indexToInsert, indexToInsert);
	}

	public void updateTableHeaderText(final JTable table) {
		final Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			final TableColumn column = columns.nextElement();
			column.setHeaderValue(table.getModel().getColumnName(column.getModelIndex()));
		}
		table.getTableHeader().repaint();
	}

	/**
	 * Diese Methode setzt die Spaltenüberschriften.
	 * 
	 * @param columnNames Spaltenüberschriften für die Tabelle.
	 * @author morrigan
	 */
	protected void setColumnNames(final String[] columnNames) {
		this.columnNames = columnNames;
	}
}
