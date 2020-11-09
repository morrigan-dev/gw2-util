package de.morrigan.dev.gw2.client.gui.interfaces;

import java.util.EventObject;

public interface IStructuredView {

	/** Über diese Methode werden die Eigenschaften von GUI-Elementen gesetzt */
	void configureGUI();

	/**
	 * Über diese Methode werden die benötigten Listener erzeugt und an die entsprechenden GUI-Elemente gesetzt. Die
	 * auszuführende Aktion eines Listeners wird in einer separaten Methode abgewickelt.
	 */
	void configureListener();

	/** Über diese Methode werden alle für diese View benötigten GUI-Elemente erzeugt. */
	void createGUI();

	/**
	 * Über diese Methode werden alle Events von Listenern behandelt. Dabei wird von den Listenern, sobald diese
	 * gefeuert werden, diese Methode aufgerufen und ein entsprechender Aktionsparameter übergeben. Die einzelnen
	 * Aktionen sind als Enumeration in der jeweiligen View bereitzustellen und müssen {@link IListenerAction}
	 * implementieren.
	 * <p>
	 * In dieser Methode ist sowohl das Logging als auch das Exceptionshandling unterzubringen.
	 * <p>
	 * Diese Methode fungiert ausschließlich aus Delegator und leitet die aufrufe an kleine Methoden weiter, die die
	 * entsprechende Funktionalität besitzen, die auszuführen ist.
	 * 
	 * @param listenerAction eine auszuführende Aktion.
	 * @param event ein Event, welches von einem Listener getriggert wurde.
	 */
	void handleListenerEvent(IListenerAction listenerAction, EventObject event);

	/** Über diese Methode werden alle für diese View benötigten GUI-Elemente im Layout des Content-Panels angeordnet */
	void layoutGUI();

	/** Über diese Methode werden alle Beschriftungen von den GUI-Elementen aktualisiert */
	void updateLanguage();
}
