package de.morrigan.dev.swing.interfaces;

/**
 * Dieses Interface ermoglicht den Zugriff auf das Änderungsflag eines DTOs
 * 
 * @author morrigan
 */
public interface IChoosableDTO<T> extends Comparable<T> {

	public enum CHOOSABLE {
		ADDED, REMOVED
	}

	public boolean isAdded();

	public boolean isRemoved();

	/**
	 * Setzt das Änderungsflag
	 * 
	 * @param choosable
	 */
	public void setChoosableState(CHOOSABLE choosable);
}
