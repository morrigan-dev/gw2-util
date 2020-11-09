package de.morrigan.dev.swing.interfaces.dialog;

import java.util.List;

/**
 * Dieses Interface muss von allen Models implementiert werden, die von einem ZweiListenDialog eine Zuordnung von
 * Elementen erhalten wollen. Dabei holt sich der ZweiListenDialog über die Schnittstelle die benötigten Listen als
 * Referenz, sodass Änderungen an den Listen im Callbackmodel sich ebenfalls auswirken.
 * <p>
 * Die Listen können nur übergeben werden und nicht direkt gespeichert, da es vorkommen kann, dass das Objekt, dem die
 * Auswahl zugeordnet werden soll noch nicht gespeichert ist. Daher muss sichergestellt werden, dass die Zuordnungen
 * (Beziehungen) erst nach dem Speichern des primären Objekts gespeichert werden.
 * <p>
 * Anmerkung: Da jedes Model dieses Interface nur einmal implementieren kann, folgt daraus, dass für jede
 * unterschiedlche Zuordnung von Objekten auch unterschiedliche Models benötigt werden. Sollte also in einer View
 * mehrere Zuordnungen durchgeführt werden, so wird empfohlen kleine Models speziell für die Zuordnung zu erstellen, die
 * dann von dem entsprechenden Model der View intern verwendet werden.
 * 
 * @author morrigan
 */
public interface IZweiListenCallbackModel<T> {

	/**
	 * Diese Methode stellt die verfügbaren Elemente bereit, die aus allen möglichen Elementen minus den bereits
	 * zugeordneten Elementen besteht.
	 * 
	 * @return Liste mit den verfügbaren Elementen
	 */
	public List<T> getAvailableEntries();

	/**
	 * Diese Methode stellt die bereits zugeordneten Elemente bereits.
	 * 
	 * @return Liste mit den zugeordneten Elementen
	 */
	public List<T> getSelectedEntries();

	/**
	 * Über diese Methode informiert das ZweiListenModell das Callback Model ü4ber Änderungen an den Daten. Dies ist
	 * nötig, da die Änderungen intime geschehen, da referenzen auf Listen übergeben werden. Damit ein update() der GUi
	 * getriggert werden kann, muss das Callback Model über diese Änderungen informiert werden.
	 * 
	 * @author morrigan
	 */
	public void twoListDataChanged();

}
