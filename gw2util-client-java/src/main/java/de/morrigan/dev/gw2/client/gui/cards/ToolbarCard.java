package de.morrigan.dev.gw2.client.gui.cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JLabel;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.PreferencesModel;
import de.morrigan.dev.gw2.client.gui.AbstractView;
import de.morrigan.dev.gw2.client.gui.components.GW2Label;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gui.interfaces.INavigation;
import de.morrigan.dev.gw2.client.model.AuthenticationModel;
import de.morrigan.dev.gw2.client.model.InfoMessageModel;
import de.morrigan.dev.gw2.client.model.NavigationModel;
import de.morrigan.dev.gw2.client.model.RightsModel;
import de.morrigan.dev.gw2.client.model.TopMenuBarModel;
import de.morrigan.dev.gw2.resources.ImageManager;
import de.morrigan.dev.gw2.utils.annotations.RightCheck;
import de.morrigan.dev.gw2.utils.annotations.RightCheck.Type;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.components.MessageDialog;
import de.morrigan.dev.swing.enums.DialogMessage;
import de.morrigan.dev.swing.factories.MessageDialogFactory;
import de.morrigan.dev.swing.models.dialogs.ConfirmationDialog;
import de.morrigan.dev.swing.models.dialogs.IDefaultDialogResult;
import de.morrigan.dev.swing.models.dialogs.MessageDialogModel;
import de.morrigan.dev.swing.wrapper.ListenerWrapper;

public class ToolbarCard extends AbstractView<TopMenuBarModel> {

	private enum ListenerAction implements IListenerAction {
		INFO_ENTERED,
		INFO_LEFT,
		INFO_CLICKED, //
		MAP_ENTERED,
		MAP_LEFT,
		MAP_CLICKED, //
		EXIT_ENTERED,
		EXIT_LEFT,
		EXIT_CLICKED, //
	}

	/** automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -6889861073121636495L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(ToolbarCard.class);

	/** Handel auf den ImageManager */
	private static final ImageManager IMAGE_MANAGER = ImageManager.getInstance();

	/** Schriftart des Infotextes */
	private static final Font titleFont = Main.getInstance().getMenomonia().deriveFont(14f);

	/** Farbe des Infotextes */
	private static final Color INFO_TEXT_COLOR = new Color(255, 238, 187);

	private NavigationModel navModel = NavigationModel.getInstance();
	private PreferencesModel prefModel = PreferencesModel.getInstance();
	private AuthenticationModel authModel = AuthenticationModel.getInstance();

	private JLabel barInfo;
	@RightCheck(rightKeys = { "showMap" }, type = Type.FIELD)
	private JLabel barMap;
	private JLabel barExit;
	private GW2Label lblInfo;

	public ToolbarCard(Window mainWindow) {
		super(mainWindow);

		try {

			this.model = new TopMenuBarModel();

			createGUI();
			configureGUI();
			layoutGUI();
			configureListener();
			updateLanguage();

			RightsModel.getInstance().updateViewByRights(this);

			this.model.addObserver(this);
			this.authModel.addObserver(this);
			InfoMessageModel.getInstance().addObserver(this);
			RightsModel.getInstance().addObserver(this);

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialogFactory.handleExcpetion(getMainWindow(), e, null);
		}
	}

	@Override
	public void configureGUI() {
		setOpaque(false);

		this.barInfo.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_INFO_ICON));
		this.barMap.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_MAP_ICON));
		this.barExit.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_EXIT_ICON));

		this.barMap.setVisible(false);

		this.lblInfo.setFont(titleFont);
		this.lblInfo.setForeground(INFO_TEXT_COLOR);
	}

	@Override
	public void configureListener() {
		this.barInfo.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				handleListenerEvent(ListenerAction.INFO_CLICKED, event);
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				handleListenerEvent(ListenerAction.INFO_ENTERED, event);
			}

			@Override
			public void mouseExited(MouseEvent event) {
				handleListenerEvent(ListenerAction.INFO_LEFT, event);
			}
		});
		this.barMap.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				handleListenerEvent(ListenerAction.MAP_CLICKED, event);
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				handleListenerEvent(ListenerAction.MAP_ENTERED, event);
			}

			@Override
			public void mouseExited(MouseEvent event) {
				handleListenerEvent(ListenerAction.MAP_LEFT, event);
			}
		});
		this.barExit.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				handleListenerEvent(ListenerAction.EXIT_CLICKED, event);
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				handleListenerEvent(ListenerAction.EXIT_ENTERED, event);
			}

			@Override
			public void mouseExited(MouseEvent event) {
				handleListenerEvent(ListenerAction.EXIT_LEFT, event);
			}
		});
	}

	@Override
	public void createGUI() {
		this.barInfo = new JLabel();
		this.barMap = new JLabel();
		this.barExit = new JLabel();
		this.lblInfo = new GW2Label();
	}

	@Override
	public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
		LOG.debug("listenerAction: {}, event: {}", listenerAction, event);
		try {
			ListenerAction action = (ListenerAction) listenerAction;
			switch (action) {
				case INFO_ENTERED:
					this.barInfo.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_INFO_HOVER_ICON));
					InfoMessageModel.getInstance().setMessage("Silver Drachenkrieger Utilities");
					InfoMessageModel.getInstance().startTimer();
				break;
				case INFO_LEFT:
					this.barInfo.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_INFO_ICON));
					Main.getInstance().repaint();
				break;
				case INFO_CLICKED:
					this.navModel.setSelectedCard(INavigation.CARD_INFORMATION);
				break;

				case MAP_ENTERED:
					this.barMap.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_MAP_HOVER_ICON));
					InfoMessageModel.getInstance().setMessage("Silver Drachenkrieger Utilities");
					InfoMessageModel.getInstance().startTimer();
				break;
				case MAP_LEFT:
					this.barMap.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_MAP_ICON));
					Main.getInstance().repaint();
				break;
				case MAP_CLICKED:
					this.navModel.setSelectedCard(INavigation.CARD_MAP);
				break;
				case EXIT_ENTERED:
					this.barExit.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_EXIT_HOVER_ICON));
					InfoMessageModel.getInstance().setMessage("Silver Drachenkrieger Utilities");
					InfoMessageModel.getInstance().startTimer();
				break;
				case EXIT_LEFT:
					this.barExit.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BAR_EXIT_ICON));
					Main.getInstance().repaint();
				break;
				case EXIT_CLICKED:
					askForExit();
				break;

				default:
					LOG.warn("Die Aktion {} ist nicht gemappt!", action);
				break;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialog.handleExcpetion(this, e);
		}
	}

	@Override
	public void layoutGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.HORI, 0.5, 0.0, 1, 1, InsetConstants.NO_INSETS);
		add(new JLabel(), gbc);
		GCUtil.configGC(gbc, 1, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
		add(this.barInfo, gbc);
		GCUtil.configGC(gbc, 2, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
		add(this.barMap, gbc);
		GCUtil.configGC(gbc, 3, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
		add(this.barExit, gbc);
		GCUtil.configGC(gbc, 4, 0, GCUtil.WEST, GCUtil.HORI, 0.5, 0.0, 1, 1, InsetConstants.NO_INSETS);
		add(new JLabel(), gbc);

		GCUtil.configGC(gbc, 0, 1, GCUtil.CENTER, GCUtil.NONE, 0.0, 0.0, 5, 1, InsetConstants.TOP_INSETS);
		add(this.lblInfo, gbc);
	}

	@Override
	public void update(IObservable obs, long updateFlag) {
		LOG.debug("obs: {}, updateFlag: {}", obs, updateFlag);
		if (obs instanceof InfoMessageModel) {
			InfoMessageModel infoMsgModel = InfoMessageModel.getInstance();
			if ((updateFlag & InfoMessageModel.INFO_CHANGED) != 0) {
				this.lblInfo.setText(infoMsgModel.getMessage());
			}
			if ((updateFlag & InfoMessageModel.ALPHA_CHANGED) != 0) {
				this.lblInfo.setForeground(new Color(255, 238, 187, infoMsgModel.getAlpha()));
			}
		}

		if (obs instanceof RightsModel) {
			if ((updateFlag & RightsModel.RIGHTS_CHANGED) != 0) {
				RightsModel.getInstance().updateViewByRights(this);
			}
		}
	}

	@Override
	public void updateLanguage() {
		// keine GUI Elemente mit Beschrifung vorhanden
	}

	private void askForExit() {
		IDefaultDialogResult resultListener = msg -> {
			if (msg == DialogMessage.YES) {
				savePreferences();
				System.exit(0);
			}
			return false;
		};
		String title = RESOURCE_MANAGER.getLabel("question");
		String dialogHeaderText = RESOURCE_MANAGER.getLabel("question");
		String errorMsg = "";
		String detailMsg = RESOURCE_MANAGER.getMessage("exit_confirmation");
		MessageDialogModel dialogModel = new MessageDialogModel(
				IMAGE_MANAGER.getImageIcon(ImageManager.CONFIRMATION_ICON), title, dialogHeaderText, errorMsg,
				detailMsg);
		new ConfirmationDialog(getMainWindow(), dialogModel, new ListenerWrapper<Object>(resultListener));
	}

	private void savePreferences() {
		try {
			if (this.prefModel.isStayLoggedOn()) {
				this.prefModel.setSessionKey(this.authModel.getSessionKey());
				this.prefModel.setUsername(this.authModel.getUsername());
			} else {
				this.prefModel.setSessionKey("");
				this.prefModel.setUsername("");
			}
			this.prefModel.savePreferences();
		} catch (ConfigurationException e) {
			LOG.error(e.getMessage(), e);
			MessageDialog.handleExcpetion(ToolbarCard.this, e);
		}
	}
}
