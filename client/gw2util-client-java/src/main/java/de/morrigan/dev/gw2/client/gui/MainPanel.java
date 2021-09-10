package de.morrigan.dev.gw2.client.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.gui.cards.AdminCard;
import de.morrigan.dev.gw2.client.gui.cards.DynamicMapCard;
import de.morrigan.dev.gw2.client.gui.cards.InformationCard;
import de.morrigan.dev.gw2.client.gui.components.GW2Label;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gui.interfaces.INavigation;
import de.morrigan.dev.gw2.client.gw2.api.GW2APIModel;
import de.morrigan.dev.gw2.client.gw2.api.IThreadCallback;
import de.morrigan.dev.gw2.client.model.GW2MapModel;
import de.morrigan.dev.gw2.client.model.MainPanelModel;
import de.morrigan.dev.gw2.client.model.NavigationModel;
import de.morrigan.dev.gw2.client.model.RightsModel;
import de.morrigan.dev.gw2.resources.ImageManager;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.annotations.RightCheck;
import de.morrigan.dev.gw2.utils.annotations.RightCheck.Type;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.components.MessageDialog;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class MainPanel extends JPanel implements IObserver, INavigation, IThreadCallback {

  private enum ListenerAction implements IListenerAction {
    CLOSE_ENTERED, CLOSE_LEFT, CLOSE_CLICKED, //
    INFO_CLICKED, MAP_CLICKED, ADMINISTRATION_CLICKED
  }

  /** automatisch generierte serialVersionUID */
  private static final long serialVersionUID = 6377677619212330185L;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(MainPanel.class);

  /** Handle auf den ResourceManager */
  private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

  /** Handel auf den ImageManager */
  private static final ImageManager IMAGE_MANAGER = ImageManager.getInstance();

  private static final Image BACKGROUND = IMAGE_MANAGER.getImage(ImageManager.BACKGROUND_IMAGE);

  private GW2Label lblTitle;

  private JLabel lblClose;

  private JLabel lblInformation;
  @RightCheck(rightKeys = {
      "showMap"
  }, type = Type.FIELD)
  private JLabel lblDynamicMap;
  @RightCheck(rightKeys = {
      "administrate"
  }, type = Type.FIELD)
  private JLabel lblAdministration;

  private JPanel menuPanel;
  private JPanel buttonPanel;

  private DynamicMapCard dynamicMapCard;
  private InformationCard informationCard;
  private AdminCard adminCard;

  private MainPanelModel model;
  private Window mainWindow;

  /** Schriftart für den Titel des Fensters */
  private static final Font TITLE_FONT = Main.getInstance().getMenomonia().deriveFont(26f).deriveFont(Font.BOLD);

  /** Schriftfarbe für den Titel des Fensters */
  private static final Color TITLE_COLOR = new Color(255, 238, 187);

  public MainPanel(Window mainWindow) {
    super();

    try {

      this.mainWindow = mainWindow;
      this.model = new MainPanelModel();

      createGUI();
      configureGUI();
      layoutGUI();
      configureListener();
      updateLanguage();

      //      this.model.getNavModel().setSelectedCard(INavigation.CARD_INFORMATION);

      this.model.addObserver(this);
      this.model.getAuthModel().addObserver(this);
      this.model.getNavModel().addObserver(this);
      this.model.getRightsModel().addObserver(this);

      //      this.gw2APIModel.addCallback(this);
      //      this.gw2APIModel.loadDataFromAPI();

    } catch (final Exception e) {
      LOG.error(e.getMessage(), e);
      MessageDialog.handleExcpetion(this, e);
    }
  }

  @Override
  public void basicDataAvailable() {
    this.model.getGw2APIModel().loadWaypointsFromAPI(this.dynamicMapCard.getMapViewer());
    this.dynamicMapCard.updateLanguage();
  }

  public DynamicMapCard getDynamicMapCard() {
    return this.dynamicMapCard;
  }

  @Override
  public void paintComponent(Graphics g) {
    g.drawImage(BACKGROUND, 0, 0, BACKGROUND.getWidth(null), BACKGROUND.getHeight(null), null);
  }

  @Override
  public void showCard() {
    int cardIndex = this.model.getNavModel().getSelectedCard();

    this.lblInformation.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.INFO_ICON));
    this.lblDynamicMap.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.MAP_ICON));
    this.lblAdministration.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BLUE_BALL_ICON));

    this.informationCard.setVisible(false);
    this.dynamicMapCard.setVisible(false);
    this.adminCard.setVisible(false);

    try {
      switch (cardIndex) {
        case CARD_INFORMATION:
          this.lblInformation.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.SELECTED_INFO_ICON));
          this.informationCard.setVisible(true);
        break;
        case CARD_MAP:
          this.lblDynamicMap.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.SELECTED_MAP_ICON));
          this.dynamicMapCard.initialize();
          this.dynamicMapCard.setVisible(true);
        break;
        case CARD_ADMINISTRATION:
          this.lblAdministration.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.BLUE_BALL_ICON));
          this.adminCard.initialize();
          this.adminCard.setVisible(true);
        break;

        default:
          LOG.warn("Der Kartenindex {} ist nicht gemappt!", cardIndex);
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

    if (obs instanceof NavigationModel) {
      showCard();
    }

    if (obs instanceof RightsModel) {
      if ((updateFlag & RightsModel.RIGHTS_CHANGED) != 0) {
        this.model.getRightsModel().updateViewByRights(this);
      }
    }
  }

  @Override
  public void waypointsAvailable() {
    GW2APIModel gw2apiModel = model.getGw2APIModel();
    GW2MapModel.getInstance().setStaticWaypoints(gw2apiModel.getGw2Waypoints(), gw2apiModel.getGw2POIs(),
        gw2apiModel.getGw2Unlock(), gw2apiModel.getGw2Vista(), gw2apiModel.getGw2Skill(),
        gw2apiModel.getGw2Heart(), gw2apiModel.getGw2MapInfo());
  }

  private void configureGUI() {
    this.lblTitle.setFont(TITLE_FONT);
    this.lblTitle.setForeground(TITLE_COLOR);
    this.lblClose.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.CLOSE_ICON));

    this.lblDynamicMap.setVisible(false);
    this.lblAdministration.setVisible(false);
  }

  private void configureListener() {
    this.lblClose.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        handleListenerEvent(ListenerAction.CLOSE_CLICKED, event);
      }

      @Override
      public void mouseEntered(MouseEvent event) {
        handleListenerEvent(ListenerAction.CLOSE_ENTERED, event);
      }

      @Override
      public void mouseExited(MouseEvent event) {
        handleListenerEvent(ListenerAction.CLOSE_LEFT, event);
      }
    });
    this.lblInformation.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        handleListenerEvent(ListenerAction.INFO_CLICKED, event);
      }
    });
    this.lblDynamicMap.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        handleListenerEvent(ListenerAction.MAP_CLICKED, event);
      }
    });
    this.lblAdministration.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        handleListenerEvent(ListenerAction.ADMINISTRATION_CLICKED, event);
      }
    });
  }

  private void createGUI() {
    this.lblTitle = new GW2Label();
    this.lblClose = new JLabel();
    this.lblInformation = new JLabel();
    this.lblDynamicMap = new JLabel();
    this.lblAdministration = new JLabel();
    this.menuPanel = new JPanel();
    this.buttonPanel = new JPanel();

    this.informationCard = new InformationCard(this.mainWindow);
    this.dynamicMapCard = new DynamicMapCard(this.mainWindow, true);
    this.adminCard = new AdminCard(this.mainWindow);
  }

  private void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
    LOG.debug("listenerAction: {}, event: {}", listenerAction, event);
    try {
      ListenerAction action = (ListenerAction) listenerAction;
      switch (action) {
        case CLOSE_ENTERED:
          this.lblClose.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.CLOSE_HOVER_ICON));
        break;
        case CLOSE_LEFT:
          this.lblClose.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.CLOSE_ICON));
        break;
        case CLOSE_CLICKED:
          Main.getInstance().getMainFrame().setVisible(false);
        break;
        case INFO_CLICKED:
          this.model.getNavModel().setSelectedCard(INavigation.CARD_INFORMATION);
        break;
        case MAP_CLICKED:
          this.model.getNavModel().setSelectedCard(INavigation.CARD_MAP);
        break;
        case ADMINISTRATION_CLICKED:
          this.model.getNavModel().setSelectedCard(INavigation.CARD_ADMINISTRATION);
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

  private void layoutGUI() {
    setLayout(null);

    add(this.lblTitle);
    add(this.buttonPanel);
    add(this.menuPanel);
    add(this.informationCard);
    add(this.dynamicMapCard);
    add(this.adminCard);

    this.lblTitle.setBounds(100, 30, 400, 30);

    this.buttonPanel.setLayout(new GridLayout(1, 3));
    this.buttonPanel.setOpaque(false);
    this.buttonPanel.setBounds(960, 33, 50, 22);
    this.buttonPanel.add(this.lblClose);

    this.menuPanel.setLayout(new GridLayout(5, 1));
    this.menuPanel.setOpaque(false);
    this.menuPanel.setBounds(7, 100, 100, 300);
    this.menuPanel.add(this.lblInformation, 0);
    this.menuPanel.add(this.lblDynamicMap, 1);
    this.menuPanel.add(this.lblAdministration, 2);

    this.informationCard.setBounds(96, 76, 890, 640);
    this.dynamicMapCard.setBounds(96, 76, 890, 640);
    this.adminCard.setBounds(96, 76, 890, 640);
  }

  private void updateLanguage() {
    this.lblTitle.setText(RESOURCE_MANAGER.getLabel("utilTitle"));
  }
}
