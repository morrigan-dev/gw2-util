package de.morrigan.dev.gw2.client.gui.interfaces;

public interface INavigation {

	public static final int CARD_TOP_MENU_BAR = -1;
	public static final int CARD_INFORMATION = 0;
	public static final int CARD_MAP = 1;
	public static final int CARD_ADMINISTRATION = 2;

	void showCard();
}
