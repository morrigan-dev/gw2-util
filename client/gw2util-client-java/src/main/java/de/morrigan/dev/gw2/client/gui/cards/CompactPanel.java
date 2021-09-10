package de.morrigan.dev.gw2.client.gui.cards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Window;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.gui.interfaces.INavigation;
import de.morrigan.dev.gw2.client.model.AuthenticationModel;
import de.morrigan.dev.gw2.client.model.NavigationModel;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.components.MessageDialog;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class CompactPanel extends JPanel implements IObserver, INavigation {

	/** automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -3852890672413276158L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(CompactPanel.class);

	private DynamicMapCard dynamicMapCard;

	private NavigationModel navModel = NavigationModel.getInstance();
	private AuthenticationModel authModel = AuthenticationModel.getInstance();

	private Window mainWindow;

	public CompactPanel(Window mainWindow) {
		super();

		try {

			this.mainWindow = mainWindow;

			createGUI();
			configureGUI();
			layoutGUI();
			configureListener();

			this.authModel.addObserver(this);
			this.navModel.addObserver(this);

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialog.handleExcpetion(this, e);
		}
	}

	@Override
	public void showCard() {
		int cardIndex = this.navModel.getSelectedCard();

		this.dynamicMapCard.setVisible(false);

		try {
			switch (cardIndex) {
				case CARD_MAP:
					layoutGUI();
					this.dynamicMapCard.initialize();
					this.dynamicMapCard.setVisible(true);
				break;

				default:
					LOG.warn("Der Kartenindex " + cardIndex + " ist nicht gemappt!");
				break;
			}
		} catch (Exception e) {
			MessageDialogFactory.handleExcpetion(Main.getInstance().getMainFrame(), e, null);
		}
		Main.getInstance().getMainFrame().setVisible(true);

		Container parent = getParent();
		if (parent != null) {
			parent.repaint();
		}
	}

	@Override
	public void update(IObservable obs, long updateFlag) {
		LOG.debug("obs: {}, updateFlag: {}", obs, updateFlag);
	}

	private void configureGUI() {
		setOpaque(false);
		setBorder(new LineBorder(Color.BLACK));
	}

	private void configureListener() {
		// keine Listener vorhanden
	}

	private void createGUI() {
		this.dynamicMapCard = new DynamicMapCard(this.mainWindow, false);
	}

	private void layoutGUI() {
		setLayout(new BorderLayout());

		add(this.dynamicMapCard, BorderLayout.CENTER);
	}
}
