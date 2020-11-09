package de.morrigan.dev.gw2.client.gui.map;

import javax.swing.ImageIcon;

import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;

public class MenuEntry implements Comparable<MenuEntry> {

	public String label;
	public ImageIcon icon;
	public WPType wpType;
	public WPSubType wpSubType;
	public String actionCommand;
	public Integer order;

	public MenuEntry(String label, ImageIcon icon, WPType wpType, WPSubType wpSubType, String actionCommand, int order) {
		super();
		this.label = label;
		this.icon = icon;
		this.wpType = wpType;
		this.wpSubType = wpSubType;
		this.actionCommand = actionCommand;
		this.order = order;
	}

	@Override
	public int compareTo(MenuEntry other) {
		return this.order.compareTo(other.order);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[label: ").append(this.label);
		sb.append(", order: ").append(this.order);
		sb.append(", wpSubType: ").append(this.wpSubType);
		sb.append("]");
		return sb.toString();
	}
}
