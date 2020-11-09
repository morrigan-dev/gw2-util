package de.morrigan.dev.gw2.utils.model.interfaces;

/**
 * Dieses Interface muss von allen Modellen implementiert werden, die über einen Thread ihre Berechnungen ausführen
 * lassen. Es ermöglicht, dem entsprechenden Thread die Fertigstellung der Berechnung mitzuteilen, damit das Model sich
 * die Ergebnisse aus dem Thread holen kann, bevor dieser beendet wird.
 * 
 * @author morrigan
 */
public interface IModelCallback {

	/**
	 * Über diese Methode informiert der Thread das Model, dass die Berechnung beendet ist und die Ergebnisse abgerufen
	 * werden können.
	 * 
	 * @return Die Klasse des aufrufenden Objekts.
	 */
	public void receiveData(Class<?> workerClass);
}
