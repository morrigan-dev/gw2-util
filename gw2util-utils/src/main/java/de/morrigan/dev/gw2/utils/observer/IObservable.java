package de.morrigan.dev.gw2.utils.observer;

/**
 * Dieses Interface wird von der abstracten Klasse {@link AObservable} implementiert. Hier werden die Grundlegenden
 * Methoden des Observer-Patterns festgelegt.
 * 
 * @author morrigan
 */
public interface IObservable {

	/**
	 * Diese Methode fügt einen Observer hinzu.
	 * 
	 * @param obs Ein beliebiger Observer (not null)
	 */
	public void addObserver(IObserver obs);

	/**
	 * Diese Methode entfernt alle aktuell angemeldeten Observer.
	 */
	public void deleteObservers();

	/**
	 * Diese Methode informiert alle aktuell angemeldeten Observer.
	 * 
	 * @param obs Das aufrufende Model. (not null)
	 */
	public void notifyObservers(IObservable obs);

	/**
	 * Diese Methode informiert alle aktuell angemeldeten Observer und ermöglicht die Übergabe eines zusätzlichen
	 * Parameters, über den z.B. nur gewisse Teile der GUI aktualisiert werden sollen.
	 * 
	 * @param obs Das aufrufende Model. (not null)
	 * @param updateFlag Ein Wert, der angibt welche Komponente aktualisiert werden soll.
	 */
	public void notifyObservers(IObservable obs, long updateFlag);

	/**
	 * Diese Methode entfernt den übergebenen Observer, sofern er überhaupt angemeldet ist.
	 * 
	 * @param obs Der zu entfernende Observer. (not null)
	 */
	public void removeObserver(IObserver obs);
}
