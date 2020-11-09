package de.morrigan.dev.swing.wrapper;

public class ListenerWrapper<T> {

	private final T listener;

	public ListenerWrapper(final T listener) {

		this.listener = listener;
	}

	public T getListener() {
		return this.listener;
	}

}
