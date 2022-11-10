package de.morrigan.dev.swing.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.constants.ColorConstants;
import de.morrigan.dev.swing.constants.GUIConstants;
import de.morrigan.dev.utils.resources.LanguageManager;

public abstract class ADialog extends JDialog implements IObserver {

  private static final long serialVersionUID = 1L;

  /** Stellt Beschriftungen, Nachrichten und Fehlerbeschreibungen bereit */
  protected static final LanguageManager LANGUAGES = LanguageManager.getInstance();

  private JLabel lblHeadline;
  private JLabel lblDescription;

  private JPanel pnlHeader;
  private JPanel pnlContent;

  public ADialog(final Window window, final String title) {
    this(window, title, null, null);
  }

  public ADialog(final Window window, final String title, final IObservable model, final Object listener) {
    super(window, title, ModalityType.APPLICATION_MODAL);
    createAGUI();
    layoutAGUI();
    configureGUI(model);
    configureListener(listener);
    this.setVisible(true);
  }

  public ADialog(final Window window, final String title, final Object listener) {
    this(window, title, null, listener);
  }

  public void setDescriptionText(final String textText) {
    this.lblDescription.setText(textText);
  }

  public void setHeaderText(final String headerText) {
    this.lblHeadline.setText(headerText);
  }

  protected abstract void configureGUI(IObservable model);

  protected abstract void configureListener(Object listener);

  protected abstract void createGUI(JPanel contentPanel);

  protected abstract void layoutGUI(JPanel contentPanel);

  private void createAGUI() {

    this.pnlHeader = new JPanel();
    this.pnlHeader.setLayout(new GridBagLayout());
    this.pnlContent = new JPanel();

    this.lblHeadline = new JLabel();
    this.lblHeadline.setFont(GUIConstants.DIALOG_HEADER_FONT);
    this.lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
    this.pnlHeader.add(this.lblHeadline);
    this.lblDescription = new JLabel();
    this.lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
    this.pnlHeader.add(this.lblDescription);

    createGUI(this.pnlContent);
  }

  private void layoutAGUI() {
    layoutHeaderPanel();

    this.setLayout(new GridBagLayout());

    this.pnlHeader.setBackground(ColorConstants.MODULE_ADMINISTRATION_COLOR);
    this.pnlHeader.setBorder(BorderFactory.createLineBorder(ColorConstants.BORDER_COLOR));

    final GridBagConstraints gbc = new GridBagConstraints();

    GCUtil.configGC(gbc, 0, 0, GCUtil.NORTHWEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
    add(this.pnlHeader, gbc);
    GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.BOTH, 1.0, 1.0, 1, 1, InsetConstants.NO_INSETS);
    add(this.pnlContent, gbc);

    layoutGUI(this.pnlContent);
  }

  private void layoutHeaderPanel() {
    final GridBagConstraints gbc = new GridBagConstraints();

    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.LTR_INSETS);
    this.pnlHeader.add(this.lblHeadline, gbc);
    GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.ALL_INSETS);
    this.pnlHeader.add(this.lblDescription, gbc);
  }
}
