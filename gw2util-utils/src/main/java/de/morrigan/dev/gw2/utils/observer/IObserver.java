package de.morrigan.dev.gw2.utils.observer;

/**
 * Dieses Interface muss von allen Observern implementiert werden.
 * 
 * @author morrigan
 */
public interface IObserver {

	/**
	 * Callback, der vom Model aufgerufen wird. (aktualisiert z.B. die Werte im GUI)
	 * 
	 * @param obs Handle zum aufrufenden Model.
	 * @param updateFlag zus�tzliche Information (z.B. welche Bereiche sollen aktualisiert werden)
	 */
	void update(IObservable obs, long updateFlag);
}
