package de.morrigan.dev.gw2.client.gui.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.EventObject;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import de.morrigan.dev.gw2.client.gui.components.GW2Label;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gui.interfaces.IStructuredView;
import de.morrigan.dev.gw2.resources.ImageConstants;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.utils.resources.LanguageManager;

public class GW2HoverPanel extends JDialog implements IStructuredView {

  /** automatisch generierte serialVersionUID */
  private static final long serialVersionUID = -8296584091692029189L;

  /** Stellt Beschriftungen, Nachrichten und Fehlerbeschreibungen bereit */
  protected static final LanguageManager LANGUAGES = LanguageManager.getInstance();

  /** Handel auf den ImageManager */
  protected static final ImageConstants IMAGE_MANAGER = ImageConstants.getInstance();

  /** Hintergrundfarbe des Panels */
  private static final Color BACKGROUND_COLOR = new Color(30, 30, 50);

  /** Rahmenfarbe des Panels */
  private static final LineBorder LINE_BORDER = new LineBorder(new Color(0, 0, 0), 2, false);

  protected JPanel pnlContent;
  protected GW2Label lblInfo;

  public GW2HoverPanel() {
    super();

    createGUI();
    configureGUI();
    layoutGUI();
    configureListener();
    updateLanguage();
  }

  @Override
  public void configureGUI() {
    setAlwaysOnTop(true);
    setUndecorated(true);
    this.pnlContent.setBorder(LINE_BORDER);
    this.pnlContent.setBackground(BACKGROUND_COLOR);
  }

  @Override
  public void configureListener() {
    // keine Listener vorhanden
  }

  @Override
  public void createGUI() {
    this.pnlContent = new JPanel();
    this.lblInfo = new GW2Label();
  }

  @Override
  public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
    // keine Listener vorhanden
  }

  @Override
  public void layoutGUI() {
    this.pnlContent.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 10, 1, InsetConstants.ALL_INSETS);
    this.pnlContent.add(this.lblInfo, gbc);

    setLayout(new BorderLayout());
    add(this.pnlContent, BorderLayout.CENTER);
  }

  public void setInfo(String info) {
    this.lblInfo.setText(info);
    pack();
  }

  @Override
  public void updateLanguage() {
    // keine GUI-Elemente mit Beschriftung vorhanden
  }
}
