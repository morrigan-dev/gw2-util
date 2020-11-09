package de.morrigan.dev.swing.models.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.utils.BitUtils;
import de.morrigan.dev.swing.interfaces.IChoosableDTO;
import de.morrigan.dev.swing.interfaces.IChoosableDTO.CHOOSABLE;
import de.morrigan.dev.swing.interfaces.dialog.IZweiListenCallbackModel;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.swing.models.table.AbstractBaseTableModel;

/**
 * Dieses Model wird verwendet um beliebige Elemente in einer Zwei-Listen-Ansicht auswählen zu können.
 * 
 * @author morrigan
 * @param <T> Die Klasse der Objekte, die ausgewählt werden können.
 */
public class ZweiListenAuswahlModel<T extends IChoosableDTO<T>> extends AbstractModel {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(ZweiListenAuswahlModel.class);

	public static final long TABLE_MODELS_CHANGED = BitUtils.setLongBit(1);

	/** Liste mit den verfügbaren Elementen */
	private List<T> availableEntries = new ArrayList<T>();

	/** Liste mit den verfügbaren Elementen */
	private List<T> cashedAvailableEntries = new ArrayList<T>();

	/** Liste mit den ausgewählten Elementen */
	private List<T> selectedEntries = new ArrayList<T>();

	/** Liste mit den ausgewählten Elementen */
	private List<T> cashedSelectedEntries = new ArrayList<T>();

	/** Prüfsumme der Anzahl der Elemente beim initialisieren */
	private int initialElementCount = 0;

	/** Referenz auf das Model, dem nach der Auswahl das Ergebnis mitgeteilt wird */
	private final IZweiListenCallbackModel<T> callbackModel;

	private AbstractBaseTableModel<T> availableElementsModel;
	private AbstractBaseTableModel<T> selectedElementsModel;

	/**
	 * Erzeugt ein neues Model und setzt das übergebene Callbackmodel.
	 * 
	 * @param callbackModel Ein Callbackmodel. (not null)
	 */
	public ZweiListenAuswahlModel(final IZweiListenCallbackModel<T> callbackModel) {
		super();
		Validate.notNull(callbackModel, "Der Parameter (callbackModel) darf nicht null sein!");

		this.callbackModel = callbackModel;
	}

	/** Diese Methode fügt alle verfügbaren Elemente in die Liste mit den ausgewählten Elementen. */
	public void addAll() {
		assert invariant();

		for (final T elementToAdd : this.availableEntries) {
			elementToAdd.setChoosableState(CHOOSABLE.ADDED);
			this.selectedEntries.add(elementToAdd);
		}
		this.availableEntries.clear();
		Collections.sort(this.selectedEntries);
		this.availableElementsModel.fireTableDataChanged();
		this.selectedElementsModel.fireTableDataChanged();

		assert invariant();
	}

	/**
	 * Diese Methode fügt alle Elemente mit den angegebenen Indices aus der Liste mit den verfügbaren Elementen in die
	 * Liste mit den ausgewählten Elemente.
	 * 
	 * @param indices Indices der zu transferierenden Elemente.
	 */
	public void addIndices(final int[] indices) {
		assert invariant();

		if (indices.length == 0) {
			return;
		}

		final List<T> elementsToRemove = new ArrayList<T>();
		for (final int index : indices) {
			final T elementToAdd = this.availableEntries.get(index);
			elementToAdd.setChoosableState(CHOOSABLE.ADDED);
			this.selectedEntries.add(elementToAdd);
			elementsToRemove.add(elementToAdd);
		}
		for (final T element : elementsToRemove) {
			this.availableEntries.remove(element);
		}
		Collections.sort(this.selectedEntries);
		this.availableElementsModel.fireTableDataChanged();
		this.selectedElementsModel.fireTableDataChanged();

		assert invariant();
	}

	public AbstractBaseTableModel<T> getAvailableElementsModel() {
		return this.availableElementsModel;
	}

	public List<T> getAvailableEntries() {
		return this.availableEntries;
	}

	public AbstractBaseTableModel<T> getSelectedElementsModel() {
		return this.selectedElementsModel;
	}

	public List<T> getSelectedEntries() {
		return this.selectedEntries;
	}

	/**
	 * Diese Methode initialisiert das Model und holt sich die verfügbaren und die bereits ausgewählten Elemente aus dem
	 * Callback Model. Anschließend wird eine Aktualisierung der GUI veranlasst.
	 */
	public void initialize(AbstractBaseTableModel<T> availableElementsModel,
			AbstractBaseTableModel<T> selectedElementsModel) {
		Validate.notNull(availableElementsModel, "Der Parameter (availableElementsModel) darf nicht null sein!");
		Validate.notNull(selectedElementsModel, "Der Parameter (selectedElementsModel) darf nicht null sein!");
		LOG.debug("availableElementsModel: {}, selectedElementsModel: {}", availableElementsModel, selectedElementsModel);

		this.availableElementsModel = availableElementsModel;
		this.selectedElementsModel = selectedElementsModel;

		this.availableEntries = this.callbackModel.getAvailableEntries();
		this.selectedEntries = this.callbackModel.getSelectedEntries();
		for (T item : this.selectedEntries) {
			this.availableEntries.remove(item);
		}

		this.availableElementsModel.setDataList(this.availableEntries);
		this.selectedElementsModel.setDataList(this.selectedEntries);

		this.cashedAvailableEntries = new LinkedList<T>();
		this.cashedAvailableEntries.addAll(this.availableEntries);
		this.cashedSelectedEntries = new LinkedList<T>();
		this.cashedSelectedEntries.addAll(this.selectedEntries);

		Collections.sort(this.availableEntries);
		Collections.sort(this.selectedEntries);
		this.initialElementCount = this.availableEntries.size() + this.selectedEntries.size();

		LOG.debug("availableEntries: {}, selectedEntries: {}, initialElementCount: {}", this.availableEntries,
				this.selectedEntries, this.initialElementCount);

		assert invariant();
	}

	public void notifyCallBackModel() {
		this.callbackModel.twoListDataChanged();
	}

	/**
	 * Diese Methode entfernt alle Elemente aus der Liste mit den ausgewählten Elementen und fügt sie in die Liste mit den
	 * verfügbaren Elementen ein.
	 */
	public void removeAll() {
		assert invariant();

		for (T elementToAdd : this.selectedEntries) {
			elementToAdd.setChoosableState(CHOOSABLE.REMOVED);
		}
		this.availableEntries.addAll(this.selectedEntries);
		this.selectedEntries.clear();
		Collections.sort(this.availableEntries);
		this.availableElementsModel.fireTableDataChanged();
		this.selectedElementsModel.fireTableDataChanged();

		assert invariant();
	}

	/**
	 * Diese Methode fügt alle Elemente mit den angegebenen Indices aus der Liste mit den ausgewählten Elementen in die
	 * Liste mit den verfügbaren Elemente.
	 * 
	 * @param indices Indices der zu transferierenden Elemente.
	 */
	public void removeIndices(final int[] indices) {
		assert invariant();

		if (indices.length == 0) {
			return;
		}

		final List<T> elementsToRemove = new ArrayList<T>();
		for (final int index : indices) {
			T elementToAdd = this.selectedEntries.get(index);
			elementToAdd.setChoosableState(CHOOSABLE.REMOVED);
			this.availableEntries.add(elementToAdd);
			elementsToRemove.add(elementToAdd);
		}
		for (final T element : elementsToRemove) {
			this.selectedEntries.remove(element);
		}
		Collections.sort(this.availableEntries);
		this.availableElementsModel.fireTableDataChanged();
		this.selectedElementsModel.fireTableDataChanged();

		assert invariant();
	}

	/**
	 * Diese Methode setzt die entries auf den Wer zurück, denn sie am Anfang hatten
	 */
	public void revertEntries() {
		this.selectedEntries.clear();
		this.selectedEntries.addAll(this.cashedSelectedEntries);

		this.availableEntries.clear();
		this.availableEntries.addAll(this.cashedAvailableEntries);
	}

	/**
	 * Diese Methode prüft den internen Zustand dieses Objekts. Ist ein ungültiger Zustand vorhanden, so wird ein
	 * entsprechender Fehler gelogt.
	 * 
	 * @return true, falls alle Invarianten erfüllt sind. Ansonsten false.
	 */
	private boolean invariant() {
		final StringBuilder errMsg = new StringBuilder();
		try {
			// Null-Checks
			errMsg.append(this.availableEntries == null ? "availableEntries darf nicht null sein!" : "");
			errMsg.append(this.selectedEntries == null ? "selectedEntries darf nicht null sein!" : "");

			// Prüfe, ob sich die Gesamtanzahl an Elementen verändert
			if ((this.availableEntries != null) && (this.selectedEntries != null)) {
				final int currentElementCount = this.availableEntries.size() + this.selectedEntries.size();
				if (currentElementCount != this.initialElementCount) {
					errMsg.append("Die Anzahl der Elemente in den Listen hat sich verändert!");
				}
			}

			if (errMsg.length() > 0) {
				throw new IllegalStateException(errMsg.toString());
			}
			return true;
		} catch (final IllegalStateException e) {
			e.printStackTrace();
		}
		return false;
	}
}