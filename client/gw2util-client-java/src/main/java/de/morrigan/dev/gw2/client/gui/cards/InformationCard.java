package de.morrigan.dev.gw2.client.gui.cards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Version;
import de.morrigan.dev.gw2.client.gui.AbstractView;
import de.morrigan.dev.gw2.client.gui.components.GW2Label;
import de.morrigan.dev.gw2.client.gui.components.GW2MenuBar;
import de.morrigan.dev.gw2.client.gui.components.LoginPanel;
import de.morrigan.dev.gw2.client.gui.components.RegisterPanel;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gw2.api.GW2APIModel;
import de.morrigan.dev.gw2.client.gw2.api.IThreadCallback;
import de.morrigan.dev.gw2.client.model.InformationModel;
import de.morrigan.dev.gw2.client.model.MainPanelModel;
import de.morrigan.dev.gw2.resources.FontConstants;
import de.morrigan.dev.gw2.resources.ImageConstants;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.factories.ComponentFactory;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class InformationCard extends AbstractView<InformationModel> implements IThreadCallback {

  private static final long serialVersionUID = 1L;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(InformationCard.class);

  /** Handel auf das GW2 API Modell */
  private static final GW2APIModel GW2_API_MODEL = GW2APIModel.getInstance();

  /** Schriftfarbe für den Applicationnamen und die Version */
  private static final Color APP_COLOR = new Color(255, 238, 187);

  private JPanel pnlContent;
  private JScrollPane scContent;
  private GW2Label lblApplicationName;
  private GW2Label lblApplicationVersion;
  private GW2Label lblGW2APIBuilt;
  private GW2Label lblNewVersion;

  private GW2MenuBar mbLogin;
  private LoginPanel pnlLogin;
  private GW2MenuBar mbRegister;
  private RegisterPanel pnlRegister;

  private GW2MenuBar mbDeveloper;
  private JPanel pnlDeveloper;
  private GW2Label lblDeveloperName;
  private JLabel lblFacebook;
  private JLabel lblGooglePlus;
  private JLabel lblTwitter;

  private GW2MenuBar mbSDNote;
  private JTextArea taSDNote;

  public InformationCard(Window mainWindow, MainPanelModel mainModel) {
    super(mainWindow, mainModel);

    try {

      this.model = new InformationModel();

      createGUI();
      configureGUI();
      layoutGUI();
      configureListener();

      mainModel.addObserver(this);
      this.model.addObserver(this);
      GW2_API_MODEL.addCallback(this);

    } catch (final Exception e) {
      LOG.error(e.getMessage(), e);
      MessageDialogFactory.handleExcpetion(getMainWindow(), e, null);
    }
  }

  @Override
  public void basicDataAvailable() {
    this.lblGW2APIBuilt.setText(LANGUAGES.getMessage("gw2APIBuild", GW2_API_MODEL.getBuild()));
  }

  @Override
  public void configureGUI() {
    setOpaque(false);
    this.pnlContent.setOpaque(false);
    this.scContent.setViewportView(this.pnlContent);
    this.scContent.setOpaque(false);
    this.scContent.getViewport().setOpaque(false);
    this.scContent.setBorder(null);
    this.scContent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    this.scContent.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
    this.lblApplicationName.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 30f, Font.BOLD).get());
    this.lblApplicationName.setForeground(APP_COLOR);
    this.lblApplicationVersion.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 24f).get());
    this.lblApplicationVersion.setForeground(APP_COLOR);
    this.lblGW2APIBuilt.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 18f).get());
    this.lblGW2APIBuilt.setForeground(APP_COLOR);
    this.lblNewVersion.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 18f).get());
    this.lblNewVersion.setForeground(Color.RED);
    this.lblDeveloperName.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 16f).get());
    this.lblFacebook.setIcon(IMAGE_MANAGER.getImageIcon(ImageConstants.FACEBOOK_ICON, 24, 24));
    this.lblGooglePlus.setIcon(IMAGE_MANAGER.getImageIcon(ImageConstants.GOOGLE_PLUS_ICON, 24, 24));
    this.lblTwitter.setIcon(IMAGE_MANAGER.getImageIcon(ImageConstants.TWITTER_ICON, 24, 24));

    this.taSDNote.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 16f).get());
    this.taSDNote.setBorder(new EmptyBorder(0, 0, 0, 0));
    this.taSDNote.setForeground(Color.WHITE);

    this.pnlDeveloper.setOpaque(false);

    this.mbLogin.getContent().add(this.pnlLogin);
    this.mbRegister.getContent().add(this.pnlRegister);
    this.mbDeveloper.getContent().add(this.pnlDeveloper);
    this.mbSDNote.getContent().add(this.taSDNote);
  }

  @Override
  public void configureListener() {
    // keine Listener vorhanden
  }

  @Override
  public void createGUI() {
    this.pnlContent = new JPanel();
    this.scContent = new JScrollPane();
    this.lblApplicationName = new GW2Label();
    this.lblApplicationVersion = new GW2Label();
    this.lblGW2APIBuilt = new GW2Label();
    this.lblNewVersion = new GW2Label();

    this.mbLogin = new GW2MenuBar();
    this.pnlLogin = new LoginPanel();
    this.mbRegister = new GW2MenuBar();
    this.pnlRegister = new RegisterPanel();

    this.mbDeveloper = new GW2MenuBar();
    this.pnlDeveloper = new JPanel();
    this.lblDeveloperName = new GW2Label();
    this.lblFacebook = new JLabel();
    this.lblGooglePlus = new JLabel();
    this.lblTwitter = new JLabel();

    this.mbSDNote = new GW2MenuBar();
    this.taSDNote = ComponentFactory.getMultiLineLabel();
  }

  @Override
  public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
    // keine Listener vorhanden
  }

  @Override
  public void layoutGUI() {
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    Insets ltrInsets = (Insets) InsetConstants.LTR_INSETS.clone();
    ltrInsets.left = 15;
    Insets ltrInsets2 = (Insets) InsetConstants.LTR_INSETS.clone();
    ltrInsets2.top = 20;

    // Developer Panel
    this.pnlDeveloper.setLayout(new GridBagLayout());
    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.NO_INSETS);
    this.pnlDeveloper.add(this.lblDeveloperName, gbc);
    GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
    this.pnlDeveloper.add(this.lblFacebook, gbc);
    GCUtil.configGC(gbc, 1, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
    this.pnlDeveloper.add(this.lblGooglePlus, gbc);
    GCUtil.configGC(gbc, 2, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
    this.pnlDeveloper.add(this.lblTwitter, gbc);
    GCUtil.configGC(gbc, 3, 0, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 2, InsetConstants.NO_INSETS);
    this.pnlDeveloper.add(new JLabel(), gbc);

    int y = 0;
    this.pnlContent.setLayout(new GridBagLayout());
    GCUtil.configGC(gbc, 0, y++, GCUtil.NORTH, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
    this.pnlContent.add(this.lblApplicationName, gbc);
    GCUtil.configGC(gbc, 0, y++, GCUtil.NORTH, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
    this.pnlContent.add(this.lblApplicationVersion, gbc);
    GCUtil.configGC(gbc, 0, y++, GCUtil.NORTH, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
    this.pnlContent.add(this.lblGW2APIBuilt, gbc);
    GCUtil.configGC(gbc, 0, y++, GCUtil.NORTH, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
    this.pnlContent.add(this.lblNewVersion, gbc);

    GCUtil.configGC(gbc, 0, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
    this.pnlContent.add(this.mbLogin, gbc);
    GCUtil.configGC(gbc, 0, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
    this.pnlContent.add(this.mbRegister, gbc);

    GCUtil.configGC(gbc, 0, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
    this.pnlContent.add(this.mbDeveloper, gbc);

    GCUtil.configGC(gbc, 0, y++, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 3, 1, InsetConstants.LTR_INSETS);
    this.pnlContent.add(this.mbSDNote, gbc);
    GCUtil.configGC(gbc, 0, y, GCUtil.WEST, GCUtil.BOTH, 1.0, 1.0, 3, 1, InsetConstants.NO_INSETS);
    this.pnlContent.add(new JLabel(), gbc);
  }

  @Override
  public void update(IObservable obs, long updateFlag) {
    updateLanguage();
  }

  @Override
  public void updateLanguage() {
    this.lblApplicationName.setText(LANGUAGES.getLabel("utilTitle"));
    this.lblApplicationVersion.setText("Version " + Version.NUMBER);
    this.mbLogin.setHeadertext(LANGUAGES.getLabel("login"));
    this.mbRegister.setHeadertext(LANGUAGES.getLabel("register"));
    this.mbDeveloper.setHeadertext(LANGUAGES.getLabel("developer"));
    this.lblDeveloperName.setText(LANGUAGES.getLabel("developerName"));
    this.mbSDNote.setHeadertext(LANGUAGES.getLabel("hint"));
    this.taSDNote.setText(LANGUAGES.getMessage("silver_drachenkrieger_hint"));

    // FIXME: Serveroperation für Überprüfung ob neue Version vorhanden. Nimmt Client Version entgegen und liefert boolean.
    // Version als String behandeln.
    // Erstellt von morrigan am 08.11.2020
    //    BigDecimal newVersion = this.getMainModel().getVersion();
    //    if (newVersion.doubleValue() > Double.parseDouble(Version.NUMBER)) {
    //      String formattedVersion = "Version " + NumberFormat.getCurrencyInstance(Locale.US).format(newVersion);
    //      this.lblNewVersion.setText(RESOURCE_MANAGER
    //          .getMessageWithArguments("newVersionAvailable", formattedVersion));
    //    }
  }

  @Override
  public void waypointsAvailable() {
    // nicht benötigt
  }

}
