package de.morrigan.dev.gw2.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.gui.interfaces.IStructuredView;
import de.morrigan.dev.gw2.client.model.MainPanelModel;
import de.morrigan.dev.gw2.resources.ImageConstants;
import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.utils.resources.FontManager;
import de.morrigan.dev.utils.resources.LanguageManager;

/**
 * Diese Klasse stellt verschiedene Daten und Methoden bereit, die von allen Swing-Views verwendet werden können. Jede
 * Swing-View muss von dieser Klasse erben.
 * <p>
 * <b>GUI Content</b><br>
 * Die Unterklassen bauen lediglich ihre GUI zusammen und müssen diese dann über setContent() dieser Klasse übergeben,
 * damit dieser in das bestehende Layout, dass diese Klasse vorgibt eingebettet werden kann.
 *
 * @author morrigan
 */
public abstract class AbstractView<T extends AbstractModel> extends JPanel implements IObserver, IStructuredView {

  /** automatisch generierte serialVersionUID */
  private static final long serialVersionUID = 6377864728715347551L;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(AbstractView.class);

  /** FontManager der verschiedene Schriftarten OS unabhängig bereitstellt */
  protected static final FontManager FONT_MANAGER = FontManager.getInstance();

  /** Stellt Beschriftungen, Nachrichten und Fehlerbeschreibungen bereit */
  protected static final LanguageManager LANGUAGES = LanguageManager.getInstance();

  /** Handel auf den ImageManager */
  protected static final ImageConstants IMAGE_MANAGER = ImageConstants.getInstance();

  /** Hauptfenster auf dem diese View liegt */
  private final Window mainWindow;

  /** Hauptmodel, welches alle anderen Models zur Verfügung stellt */
  private final MainPanelModel mainModel;

  /** Panel mit dem eigentlichen Inhalt dieser View */
  private JPanel pnlContent;

  /** Gibt an, ob alle Listener aktiv sind */
  private boolean listenerEnabled;

  /** Gibt an, ob die View initialisiert wurde */
  private boolean initialized;

  /** Model dieser View */
  protected T model;

  /**
   * Erzeugt ein neues Panel und setzt die übergebenen Parameter.
   *
   * @param mainWindow Hauptfenster auf dem diese View liegt
   * @param mainModel Hauptmodel, in dem alle anderen bereitsgestellt werden
   */
  public AbstractView(final Window mainWindow, MainPanelModel mainModel) {
    super();

    this.mainWindow = mainWindow;
    this.mainModel = mainModel;

    this.initialized = false;
  }

  public Window getMainWindow() {
    return this.mainWindow;
  }

  public MainPanelModel getMainModel() {
    return this.mainModel;
  }

  public T getModel() {
    return this.model;
  }

  /**
   * Diese Methode erzeugt die GUI, die Listener und konfiguriert diese für die Oberklasse. Die Erzeugung und die
   * Konfiguration in den Unterklassen wird über entsprechende Methoden geregelt. Diese Methode behandelt keine
   * Exceptions. Diese müssen in der Unterklasse behandelt werden.
   *
   * @param model Model für dieses Panel.
   * @throws Exception
   */
  public void initializeSuper(final T model) throws AbstractException {
    LOG.debug("model: {}", model);

    this.listenerEnabled = true;
    this.model = model;

    createSuperGUI();
    createGUI();

    configureSuperGUI();
    configureGUI();

    layoutSuperGUI();
    layoutGUI();

    configureSuperListener();
    configureListener();

    updateSuperLanguage();
    updateLanguage();

    setLayout(new BorderLayout());
    add(this.pnlContent, BorderLayout.CENTER);

    this.model.addObserver(this);
    this.model.initialize();

    this.initialized = true;
  }

  public boolean isInitialized() {
    return this.initialized;
  }

  public boolean isListenerEnabled() {
    return this.listenerEnabled;
  }

  public void setListenerEnabled(final boolean listenerEnabled) {
    this.listenerEnabled = listenerEnabled;
  }

  protected void setContent(final JPanel content) {
    final GridBagConstraints gbc = new GridBagConstraints();

    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.BOTH, 1.0, 1.0, 1, 1, InsetConstants.NO_INSETS);
    this.pnlContent.add(content, gbc);
  }

  private void configureSuperGUI() {
    this.pnlContent.setLayout(new GridBagLayout());
    this.pnlContent.setBorder(new LineBorder(Color.BLACK));
  }

  private void configureSuperListener() {}

  private void createSuperGUI() {
    this.pnlContent = new JPanel();
  }

  private void layoutSuperGUI() {}

  private void updateSuperLanguage() {}
}
