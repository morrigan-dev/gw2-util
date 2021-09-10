package de.morrigan.dev.gw2.client.gui.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.EventObject;

import javax.ejb.NoSuchEJBException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.PreferencesModel;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gui.interfaces.IStructuredView;
import de.morrigan.dev.gw2.client.model.AuthenticationModel;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.factories.ComponentFactory;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class LoginPanel extends JPanel implements IStructuredView, IObserver {

  private enum ListenerAction implements IListenerAction {
    LOGIN_CLICKED, LOGOUT_CLICKED, STAY_LOGGED_ON
  }

  private static final long serialVersionUID = 1L;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(LoginPanel.class);

  /** Handle auf den ResourceManager */
  private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

  private JPanel pnlBevorLogin;
  private GW2Label lblUsername;
  private JTextField tfUsername;
  private GW2Label lblPassword;
  private JPasswordField pfPassword;
  private JButton btLogin;

  private JPanel pnlAfterLogin;
  private GW2Label lblLoggedInAs;
  private JButton btLogout;

  private JCheckBox chkStyLoggedOn;

  private AuthenticationModel model = AuthenticationModel.getInstance();
  private PreferencesModel prefModel = PreferencesModel.getInstance();

  public LoginPanel() {
    super();

    try {
      createGUI();
      configureGUI();
      layoutGUI();
      configureListener();
      updateLanguage();

      this.model.addObserver(this);
      this.model.loadDataFromPreferences();
    } catch (ServiceException | NoSuchEJBException e) {
      LOG.error(e.getMessage(), e);
      MessageDialogFactory.handleExcpetion(Main.getInstance().getMainFrame(), e, null);
    }
  }

  @Override
  public void configureGUI() {
    setOpaque(false);
    this.pnlBevorLogin.setOpaque(false);
    this.pnlAfterLogin.setOpaque(false);
    this.btLogin.setOpaque(false);
    this.btLogin.setBackground(new Color(0, 0, 0, 0));
    this.btLogout.setBackground(new Color(0, 0, 0, 0));
    this.tfUsername.setBackground(new Color(29, 28, 24, 150));
    this.tfUsername.setForeground(new Color(222, 222, 222));
    this.tfUsername.setBorder(new LineBorder(Color.BLACK, 2));
    this.pfPassword.setForeground(new Color(222, 222, 222));
    this.pfPassword.setBackground(new Color(29, 28, 24, 150));
    this.pfPassword.setBorder(new LineBorder(Color.BLACK, 2));
    this.chkStyLoggedOn.setOpaque(false);
    this.chkStyLoggedOn.setFont(Main.getInstance().getMenomonia().deriveFont(14f));
    this.chkStyLoggedOn.setForeground(Color.WHITE);
  }

  @Override
  public void configureListener() {
    this.btLogin.addActionListener(event -> handleListenerEvent(ListenerAction.LOGIN_CLICKED, event));
    this.btLogout.addActionListener(event -> handleListenerEvent(ListenerAction.LOGOUT_CLICKED, event));
    this.chkStyLoggedOn.addActionListener(event -> handleListenerEvent(ListenerAction.STAY_LOGGED_ON, event));
  }

  @Override
  public void createGUI() {
    this.pnlBevorLogin = new JPanel();
    this.lblUsername = new GW2Label();
    this.tfUsername = ComponentFactory.getDefaultJTextField();
    this.lblPassword = new GW2Label();
    this.pfPassword = ComponentFactory.getDefaultJPasswordField();
    this.btLogin = new JButton();

    this.pnlAfterLogin = new JPanel();
    this.lblLoggedInAs = new GW2Label();
    this.btLogout = new JButton();

    this.chkStyLoggedOn = new JCheckBox();
  }

  @Override
  public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
    LOG.debug("listenerAction: {}, event: {}", listenerAction, event);
    try {
      final ListenerAction action = (ListenerAction) listenerAction;
      switch (action) {
        case LOGIN_CLICKED:
          doLogin();
        break;
        case LOGOUT_CLICKED:
          this.model.doLogout();
        break;
        case STAY_LOGGED_ON:
          this.prefModel.setStayLoggedOn(true);
        break;

        default:
          LOG.warn("Die Aktion {} ist nicht gemappt!", action);
        break;
      }
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      MessageDialogFactory.handleExcpetion(Main.getInstance().getMainFrame(), e, null);
    }
  }

  @Override
  public void layoutGUI() {
    setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();

    this.pnlBevorLogin.setLayout(new GridBagLayout());
    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    this.pnlBevorLogin.add(this.lblUsername, gbc);
    GCUtil.configGC(gbc, 1, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
    this.pnlBevorLogin.add(this.tfUsername, gbc);
    GCUtil.configGC(gbc, 2, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
    this.pnlBevorLogin.add(this.lblPassword, gbc);
    GCUtil.configGC(gbc, 3, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
    this.pnlBevorLogin.add(this.pfPassword, gbc);
    GCUtil.configGC(gbc, 4, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
    this.pnlBevorLogin.add(this.btLogin, gbc);
    GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 5, 1, InsetConstants.NO_INSETS);
    this.pnlBevorLogin.add(this.chkStyLoggedOn, gbc);

    this.pnlAfterLogin.setLayout(new GridBagLayout());
    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    this.pnlAfterLogin.add(this.lblLoggedInAs, gbc);
    GCUtil.configGC(gbc, 1, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
    this.pnlAfterLogin.add(this.btLogout, gbc);

    showLoginMask();
  }

  @Override
  public void update(IObservable obs, long updateFlag) {
    LOG.debug("obs: {}, updateFlag: {}", obs, updateFlag);

    if (obs instanceof AuthenticationModel) {
      if ((updateFlag & AuthenticationModel.SESSION_KEY_CHANGED) != 0) {
        if (this.model.isAuthenticated()) {
          showLogoutMask();
        } else {
          showLoginMask();
        }
      }
      if ((updateFlag & AuthenticationModel.USERNAME_KEY_CHANGED) != 0) {
        this.lblLoggedInAs.setText(RESOURCE_MANAGER.getLabelWithSeparator("loggedInAs") + " "
            + this.model.getUsername());
      }
    }
  }

  @Override
  public void updateLanguage() {
    this.lblUsername.setText(RESOURCE_MANAGER.getLabelWithSeparator("username"));
    this.lblPassword.setText(RESOURCE_MANAGER.getLabelWithSeparator("password"));
    this.btLogin.setText(RESOURCE_MANAGER.getButtonLabel("login"));
    this.btLogout.setText(RESOURCE_MANAGER.getButtonLabel("logout"));
    this.chkStyLoggedOn.setText(RESOURCE_MANAGER.getLabel("stayLoggedOn"));
  }

  private void doLogin() throws ServiceException {
    String username = this.tfUsername.getText();
    String password = new String(this.pfPassword.getPassword());
    this.model.authenticate(username, password);
  }

  private void showLoginMask() {
    GridBagConstraints gbc = new GridBagConstraints();

    removeAll();
    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    add(this.pnlBevorLogin, gbc);
    GCUtil.configGC(gbc, 1, 0, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    add(new JLabel(), gbc);
  }

  private void showLogoutMask() {
    GridBagConstraints gbc = new GridBagConstraints();

    removeAll();
    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    add(this.pnlAfterLogin, gbc);
    GCUtil.configGC(gbc, 1, 0, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    add(new JLabel(), gbc);
  }
}
