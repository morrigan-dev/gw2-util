package de.morrigan.dev.gw2.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.gui.MainPanel;
import de.morrigan.dev.gw2.client.gui.cards.CompactPanel;
import de.morrigan.dev.gw2.client.gui.cards.ToolbarCard;
import de.morrigan.dev.gw2.client.model.AuthenticationModel;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

/**
 * Diese Klasse beinhaltet den PRogrammeinstieg und verwaltet die Toolbar, sowie das Hauptfenster. Sie ist als Singleton
 * implementiert, damit auf das Hauptfenster jederzeit zugegriffen werden kann. Ferner kümmert sich diese Klasse um das
 * Design des Hauptfensters und passt Größe und Position entsprechend des ausgewähltem Desing an.
 * 
 * @author morrigan
 */
// TODO
// - FontCash erstellen, mit Font Konstanten (String) über die für die einzelnen Bereiche Überschrift, Default Schrift,
// Hover Info, etc. aus dem Cash geladen werden können. Cash wird hier in der Main befüllt.
// (morrigan, 28.10.2013)
public class Main implements IObserver {

	public enum Design {
		NORMAL, SMALL
	}

	/** Größe des Fensters im Toolbar Modus am oberen Rand */
	public static final Dimension WINDOW_BOUNDS_TOP_BAR = new Dimension(300, 55);

	/** Größe des Fensters im normalen Modus */
	public static final Dimension WINDOW_BOUNDS_NORMAL = new Dimension(1024, 768);

	/** Größe des Fensters im kleinen Modus */
	public static final Dimension WINDOW_BOUNDS_SMALL = new Dimension(370, 370);

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	/** Einzige Instanz dieser Klasse */
	private static final Main MAIN = new Main();

	/**
	 * @return einzige Instanz dieser Klasse.
	 */
	public static final Main getInstance() {
		return MAIN;
	}

	public static void main(String[] args) {
		// Main wird statisch initialisiert
	}

	/** Frame für die Toolbar */
	private JFrame toolbarFrame;

	/** Frame für das Hauptfenster */
	private JFrame mainFrame;

	/** Frame für das Hauptfenster im kompakten Modus ohne Navigationsmöglichkeit */
	private JFrame compactFrame;

	/** Aktuelle Mausposition des Cursors um das Fenstern mit der Maus bewegen zu können */
	private Point relativeMousePos;

	/** GW2 Schriftart normal */
	private Font menomonia;

	/** GW2 Schriftart kursiv */
	private Font menomoniaItalic;

	/** Cronos Schriftart regular */
	private Font cronosRegular;

	/** Cronos Schriftart fett */
	private Font cronosBold;

	/** Cronos Schriftart kursiv */
	private Font cronosItalic;

	/**
	 * Startet das SD Utilities Programm und setzt verschiedene Konfigurationen.
	 * <ul>
	 * <li>Konfiguriert Log4j</li>
	 * <li>Konfiguriert GW2 Fonts</li>
	 * <li>Konfiguriert UI Manager</li>
	 * </ul>
	 */
	private Main() {
		try {
			registrerFont();
			initUIManager();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		SwingUtilities.invokeLater(() -> {
			createMainFrame();
			createToolbarFrame();
			createCompactFrame();
			AuthenticationModel.getInstance().addObserver(Main.this);
		});
	}

	public Rectangle getBounds() {
		return this.mainFrame.getBounds();
	}

	public Font getCronosBold() {
		return this.cronosBold;
	}

	public Font getCronosItalic() {
		return this.cronosItalic;
	}

	public Font getCronosRegular() {
		return this.cronosRegular;
	}

	public JFrame getMainFrame() {
		return this.mainFrame;
	}

	public Font getMenomonia() {
		return this.menomonia;
	}

	public Font getMenomoniaItalic() {
		return this.menomoniaItalic;
	}

	public void hideMiniMap() {
		this.compactFrame.setVisible(false);
	}

	public boolean isMiniMapShown() {
		return this.compactFrame.isVisible();
	}

	/**
	 * Zeichnet das Hauptfenster, das Toolbarfenster neu und das kompakte Fenster neu, sofern diese sichtbar sind.
	 */
	public void repaint() {
		if (this.mainFrame.isVisible()) {
			this.mainFrame.repaint();
		}
		if ((this.toolbarFrame != null) && this.toolbarFrame.isVisible()) {
			this.toolbarFrame.repaint();
		}
		if ((this.compactFrame != null) && this.compactFrame.isVisible()) {
			this.compactFrame.repaint();
		}
	}

	public void showMiniMap() {
		this.compactFrame.setVisible(true);
	}

	@Override
	public void update(IObservable obs, long updateFlag) {
		if (obs instanceof AuthenticationModel) {
			if ((updateFlag & AuthenticationModel.SESSION_KEY_CHANGED) != 0) {
				boolean auth = AuthenticationModel.getInstance().isAuthenticated();
				if (!auth) {
					hideMiniMap();
				}
			}
		}
	}

	private void createCompactFrame() {
		try {
			this.compactFrame = new JFrame();
			this.compactFrame.setUndecorated(true);
			this.compactFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			this.compactFrame.setLayout(new BorderLayout());
			CompactPanel compactCard = new CompactPanel(this.mainFrame);
			this.compactFrame.add(compactCard, BorderLayout.CENTER);

			String javaVersion = System.getProperty("java.version");
			if (javaVersion.startsWith("1.7")) {
				// Determine what the default GraphicsDevice can support.
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice gd = ge.getDefaultScreenDevice();
				boolean isUniformTranslucencySupported = gd
						.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSPARENT);
				if (isUniformTranslucencySupported) {
					this.compactFrame.setBackground(new Color(0, 0, 0, 0));
				}
			} else {
				this.compactFrame.setBackground(new Color(0, 0, 0));
			}

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int barWidth = WINDOW_BOUNDS_SMALL.width;
			int barHeight = WINDOW_BOUNDS_SMALL.height;
			this.compactFrame
					.setBounds((dim.width) - barWidth, (dim.height / 2) - (barHeight / 2), barWidth, barHeight);
			this.compactFrame.setAlwaysOnTop(true);
			this.compactFrame.setVisible(false);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialogFactory.handleExcpetion(this.mainFrame, e, null);
		}
	}

	private Color getBackgroundColor() {
		String javaVersion = System.getProperty("java.version");
		int minorVersion = NumberUtils.createInteger(javaVersion.split("\\.")[1]);
		Color backgroundColor = new Color(0, 0, 0);
		if (minorVersion >= 7) {
			// Determine what the default GraphicsDevice can support.
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			boolean isUniformTranslucencySupported = gd
					.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSPARENT);
			if (isUniformTranslucencySupported) {
				backgroundColor = new Color(0, 0, 0, 0);
			}
		}
		return backgroundColor;
	}

	private void createMainFrame() {
		try {
			this.mainFrame = new JFrame();
			this.mainFrame.setUndecorated(true);
			this.mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			this.mainFrame.setLayout(new BorderLayout());
			this.mainFrame.add(new MainPanel(this.mainFrame), BorderLayout.CENTER);
			this.mainFrame.setBackground(getBackgroundColor());

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int width = WINDOW_BOUNDS_NORMAL.width;
			int height = WINDOW_BOUNDS_NORMAL.height;
			this.mainFrame.setBounds((dim.width / 2) - (width / 2), (dim.height / 2) - (height / 2), width, height);
			this.mainFrame.setAlwaysOnTop(true);
			this.mainFrame.setVisible(true);

			this.mainFrame.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					Main.this.relativeMousePos = e.getPoint();
				}
			});
			this.mainFrame.addMouseMotionListener(new MouseMotionAdapter() {

				@Override
				public void mouseDragged(MouseEvent e) {
					Point newWindowLocation = e.getLocationOnScreen();
					newWindowLocation.x -= Main.this.relativeMousePos.x;
					newWindowLocation.y -= Main.this.relativeMousePos.y;
					Main.this.mainFrame.setLocation(newWindowLocation);
				}
			});

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialogFactory.handleExcpetion(this.mainFrame, e, null);
			System.exit(1);
		}
	}

	private void createToolbarFrame() {
		try {
			this.toolbarFrame = new JFrame();
			this.toolbarFrame.setLayout(new BorderLayout());
			this.toolbarFrame.add(new ToolbarCard(this.mainFrame), BorderLayout.CENTER);
			this.toolbarFrame.setUndecorated(true);
			this.toolbarFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			this.toolbarFrame.setBackground(getBackgroundColor());

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int barWidth = WINDOW_BOUNDS_TOP_BAR.width;
			int barHeight = WINDOW_BOUNDS_TOP_BAR.height;
			this.toolbarFrame.setBounds((dim.width / 2) - (barWidth / 2), 5, barWidth, barHeight);
			this.toolbarFrame.setAlwaysOnTop(true);
			this.toolbarFrame.setVisible(true);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			MessageDialogFactory.handleExcpetion(this.mainFrame, e, null);
			System.exit(1);
		}
	}

	private void initUIManager() {
		LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo lookAndFeelInfo : installedLookAndFeels) {
			if (lookAndFeelInfo.getName().contains("WindowsLookAndFeel")) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
	}

	private void registrerFont() throws FontFormatException, IOException {
		this.menomonia = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/menomonia.ttf"));
		this.menomoniaItalic = Font.createFont(Font.TRUETYPE_FONT,
				getClass().getResourceAsStream("/font/menomonia-italic.ttf"));
		this.cronosRegular = Font.createFont(Font.TRUETYPE_FONT,
				getClass().getResourceAsStream("/font/cronos-pro-regular.ttf"));
		this.cronosBold = Font.createFont(Font.TRUETYPE_FONT,
				getClass().getResourceAsStream("/font/cronos-pro-bold.ttf"));
		this.cronosItalic = Font.createFont(Font.TRUETYPE_FONT,
				getClass().getResourceAsStream("/font/cronos-pro-italic.ttf"));
		LOG.info("Schriftarten erfolgreich konfiguriert...");
	}
}
