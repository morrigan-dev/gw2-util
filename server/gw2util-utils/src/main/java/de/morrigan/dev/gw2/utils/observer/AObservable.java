package de.morrigan.dev.gw2.utils.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Diese Klasse implementiert das {@link IObservable} Interface.
 * 
 * @author morrigan
 */
public abstract class AObservable implements IObservable {

	/** Gibt an, ob gerade eine Aktualisierung der GUI durchgeführt wird */
	private boolean isChanging;

	/** Liste mit den angemeldeten Observern */
	private final List<IObserver> observers = new ArrayList<>();

	@Override
	public void addObserver(final IObserver obs) {
		this.observers.add(obs);
	}

	/** Setzt das isChanging Flag zurück */
	public final void clearChanged() {
		this.isChanging = false;
	}

	@Override
	public final void deleteObservers() {
		this.observers.clear();
	}

	/** Gibt an, ob gerade eine Aktualisierung der GUI durchgeführt wird */
	public final boolean isChanging() {
		return this.isChanging;
	}

	@Override
	public final void notifyObservers(final IObservable obs) {
		notifyObservers(obs, -1L);
	}

	@Override
	public final void notifyObservers(final IObservable obs, final long updateFlag) {
		if (this.isChanging) {
			final Iterator<IObserver> iter = this.observers.iterator();
			while (iter.hasNext()) {
				final IObserver observer = iter.next();
				observer.update(obs, updateFlag);
			}
		}
	}

	@Override
	public final void removeObserver(final IObserver obs) {
		this.observers.remove(obs);

	}

	/** Setzt das isChanging Flag */
	public final void setChanged() {
		this.isChanging = true;
	}
}
