package cz.zweistein.gw2.app.eventwatch.gui;

import java.util.Map.Entry;

public class CodeTableItem {
	
	private Entry<? extends Object, String> item;

	public CodeTableItem(Entry<? extends Object, String> item) {
		super();
		this.item = item;
	}
	
	public Entry<? extends Object, String> getItem() {
		return this.item;
	}

	@Override
	public String toString() {
		return item.getValue();
	}

}
