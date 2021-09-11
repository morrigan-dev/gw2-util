package de.morrigan.dev.gw2.client.gui.cards;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.util.EventObject;

import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.gui.AbstractView;
import de.morrigan.dev.gw2.client.gui.admin.UserAdminPanel;
import de.morrigan.dev.gw2.client.gui.components.GW2MenuBar;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.model.AdminModel;
import de.morrigan.dev.gw2.client.model.MainPanelModel;
import de.morrigan.dev.gw2.client.model.RightsModel;
import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class AdminCard extends AbstractView<AdminModel> {

  private static final long serialVersionUID = 1L;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(AdminCard.class);

  private RightsModel rightsModel = RightsModel.getInstance();

  private GW2MenuBar mbUserAdmin;

  public AdminCard(Window mainWindow, MainPanelModel mainModel) {
    super(mainWindow, mainModel);

    try {

      this.model = new AdminModel();

      createGUI();
      configureGUI();
      layoutGUI();
      configureListener();
      updateLanguage();

      this.rightsModel.updateViewByRights(this);

      this.rightsModel.addObserver(this);
      this.model.addObserver(this);

    } catch (final Exception e) {
      LOG.error(e.getMessage(), e);
      MessageDialogFactory.handleExcpetion(getMainWindow(), e, null);
    }
  }

  @Override
  public void configureGUI() {
    setOpaque(false);

    this.mbUserAdmin.getContent().add(new UserAdminPanel(this.model));
  }

  @Override
  public void configureListener() {
    // keine Listener vorhanden
  }

  @Override
  public void createGUI() {
    this.mbUserAdmin = new GW2MenuBar();
  }

  @Override
  public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
    // keine Listener vorhanden
  }

  public void initialize() throws AbstractException {
    this.model.initialize();
  }

  @Override
  public void layoutGUI() {
    setLayout(new GridBagLayout());
    final GridBagConstraints gbc = new GridBagConstraints();

    GCUtil.configGC(gbc, 0, 0, GCUtil.NORTHWEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    add(this.mbUserAdmin, gbc);
    GCUtil.configGC(gbc, 1, 1, GCUtil.NORTHWEST, GCUtil.BOTH, 1.0, 1.0, 1, 1, InsetConstants.NO_INSETS);
    add(new JLabel(), gbc);
  }

  @Override
  public void update(IObservable obs, long updateFlag) {
    // keine GUI Elemente die zu befüllen wären
  }

  @Override
  public void updateLanguage() {
    this.mbUserAdmin.setHeadertext(RESOURCE_MANAGER.getLabel("userAdmin"));
  }
}
