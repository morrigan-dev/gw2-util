package de.morrigan.dev.gw2.client.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gui.interfaces.IStructuredView;
import de.morrigan.dev.gw2.resources.FontConstants;
import de.morrigan.dev.gw2.resources.ImageManager;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.utils.resources.FontManager;

public class GW2MenuBar extends JPanel implements IStructuredView {

  /** automatisch generierte serialVersionUID */
  private static final long serialVersionUID = -3936710675430412248L;

  /** FontManager der verschiedene Schriftarten OS unabhängig bereitstellt */
  private static final FontManager FONT_MANAGER = FontManager.getInstance();

  /** Handel auf den ImageManager */
  private static final ImageManager IMAGE_MANAGER = ImageManager.getInstance();

  private static final Image BACKGROUND = IMAGE_MANAGER.getImage(ImageManager.MENU_BAR_ICON);

  private GW2Label lblHeadertext;
  private JLabel lblSpacer;
  private JLabel lblArrows;
  private JPanel pnlHeader;
  private JPanel pnlContent;

  public GW2MenuBar() {
    super();

    createGUI();
    configureGUI();
    layoutGUI();
    configureListener();
    updateLanguage();
  }

  @Override
  public void configureGUI() {
    setOpaque(false);
    this.lblHeadertext.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 16f, Font.BOLD).get());
    this.pnlContent.setOpaque(false);
    this.pnlContent.setLayout(new BorderLayout());
    this.lblArrows.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.ARROWS_UP_ICON, 20, 20));
  }

  @Override
  public void configureListener() {
    this.lblHeadertext.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        switchState();
      }
    });
    this.lblSpacer.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        switchState();
      }
    });
    this.lblArrows.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        switchState();
      }
    });
  }

  @Override
  public void createGUI() {
    this.lblHeadertext = new GW2Label();
    this.lblSpacer = new JLabel();
    this.lblArrows = new JLabel();
    this.pnlHeader = new JPanel() {

      private static final long serialVersionUID = 1L;

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(BACKGROUND.getWidth(null), BACKGROUND.getHeight(null));
      }

      @Override
      public void paintComponent(Graphics g) {
        g.drawImage(BACKGROUND, 0, 0, BACKGROUND.getWidth(null), BACKGROUND.getHeight(null), null);
      }

    };
    this.pnlContent = new JPanel();
  }

  public JPanel getContent() {
    return this.pnlContent;
  }

  @Override
  public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
    // keine Listener vorhanden
  }

  @Override
  public void layoutGUI() {
    setLayout(new GridBagLayout());
    this.pnlHeader.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();

    int defaultDist = InsetConstants.DEFAULT_DISTANCE;
    Insets insets = new Insets(defaultDist, 15, defaultDist, defaultDist);
    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, insets);
    this.pnlHeader.add(this.lblHeadertext, gbc);
    GCUtil.configGC(gbc, 1, 0, GCUtil.WEST, GCUtil.BOTH, 1.0, 1.0, 1, 1, InsetConstants.NO_INSETS);
    this.pnlHeader.add(this.lblSpacer, gbc);
    GCUtil.configGC(gbc, 2, 0, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    this.pnlHeader.add(this.lblArrows, gbc);

    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    add(this.pnlHeader, gbc);
    GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 1, insets);
    add(this.pnlContent, gbc);
  }

  public void setExpanded(boolean expand) {
    if (expand) {
      this.pnlContent.setVisible(true);
      this.lblArrows.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.ARROWS_UP_ICON, 20, 20));
    } else {
      this.pnlContent.setVisible(false);
      this.lblArrows.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.ARROWS_DOWN_ICON, 20, 20));
    }
  }

  public void setHeadertext(String headertext) {
    this.lblHeadertext.setText(headertext);
  }

  @Override
  public void updateLanguage() {
    // keine GUI-Elemente vorhanden mit Beschriftung
  }

  private void switchState() {
    boolean visible = this.pnlContent.isVisible();
    if (visible) {
      this.pnlContent.setVisible(false);
      this.lblArrows.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.ARROWS_DOWN_ICON, 20, 20));
    } else {
      this.pnlContent.setVisible(true);
      this.lblArrows.setIcon(IMAGE_MANAGER.getImageIcon(ImageManager.ARROWS_UP_ICON, 20, 20));
    }
  }
}
