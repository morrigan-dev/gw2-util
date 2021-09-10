package de.morrigan.dev.swing.interfaces.table;

import java.util.List;

import javax.swing.table.TableModel;

public interface ITableModel<T> extends TableModel {

	/**
	 * Diese Methode fügt ein Element ans Ende der Tabelle hinzu und informiert die GUI Tabelle über diese Änderung.
	 * 
	 * @param element Ein Element, welches hinzugefügt werden soll.
	 */
	public abstract void addElement(final T element);

	/**
	 * Diese Methode entfernt alle Elemente aus der Tabelle und informiert die GUI Tabelle üder diese Änderung.
	 */
	public abstract void clear();

	/**
	 * @return Liefert die Anzahl der, die beim Erstellen des Models angegeben wurde. Entsprechend viele GUI
	 *         Tabellenspalten werden auch erzeugt.
	 */
	@Override
	public abstract int getColumnCount();

	/**
	 * @param columnIndex Ein gültiger Spaltenindex.
	 * @return Liefert die Spaltenbeschriftung der angegebenen Spalte. Diese Namen als Spaltenbeschriftungen in der GUI
	 *         Tabelle benutzt.
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public abstract String getColumnName(final int columnIndex);

	/**
	 * Diese Methode ermittelt anhand des übergebenen Zeilenindexes ein Element, welches zurückgegeben wird.
	 * 
	 * @param rowIndex Ein gültiger Zeilenindex.
	 * @return Das Element an der angegebenen Stelle.
	 * @throws IndexOutOfBoundsException
	 */
	public abstract T getElementAt(final int rowIndex);

	@Override
	public abstract int getRowCount();

	@Override
	public abstract boolean isCellEditable(final int rowIndex, final int columnIndex);

	/**
	 * Diese Methode entfernt ein Element aus der Tabelle, sofern dies vorhanden ist, und informiert die GUI Tabelle über
	 * diese Änderung. Ist das Element mehrfach in der Liste, so wird nur das erste mit dem niedrigsten Index entfernt.
	 * 
	 * @param elementToRemove Das zu entfernende Element.
	 * @return true, falls das Element gelöscht wurde. Ansonsten false.
	 * @see List#remove(int)
	 */
	public abstract boolean removeElement(final T elementToRemove);

	/**
	 * Setzt die Liste mit Elementen neu und informiert die GUI Tabelle über diese Änderung.
	 * 
	 * @param dataList Eine Liste mit Elementen. (not null)
	 * @throws IllegalArgumentException
	 */
	public abstract void setDataList(final List<T> dataList);

	/**
	 * Diese Methode fügt ein Element an die angegebene Stelle in die Tabelle ein und informiert die GUi Tabelle über
	 * diese Änderung.
	 * 
	 * @param indexToInsert Ein Zeilenindex.
	 * @param elementToAdd Ein einzufügendes Element.
	 * @throws IndexOutOfBoundsException
	 */
	public abstract void setElementAt(final int indexToInsert, final T elementToAdd);
}
