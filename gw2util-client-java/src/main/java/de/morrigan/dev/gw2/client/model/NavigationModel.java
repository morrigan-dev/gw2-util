package de.morrigan.dev.gw2.client.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.gui.interfaces.INavigation;
import de.morrigan.dev.gw2.utils.BitUtils;
import de.morrigan.dev.swing.models.AbstractModel;

public class NavigationModel extends AbstractModel {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(NavigationModel.class);

	public static final long CARD_CHANGED = BitUtils.setLongBit(0);

	private static final NavigationModel INSTANCE = new NavigationModel();

	public static NavigationModel getInstance() {
		return INSTANCE;
	}

	private int selectedCard;

	private NavigationModel() {
		super();
		this.selectedCard = INavigation.CARD_TOP_MENU_BAR;
	}

	public int getSelectedCard() {
		return this.selectedCard;
	}

	public void setSelectedCard(int selectedCard) {
		LOG.debug("this.selectedCard: {}, selectedCard: {}", this.selectedCard, selectedCard);
		this.selectedCard = selectedCard;
		if (!isChanging()) {
			syncViews(CARD_CHANGED);
		}
	}
}
