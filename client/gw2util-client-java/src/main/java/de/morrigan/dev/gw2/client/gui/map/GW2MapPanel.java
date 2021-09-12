package de.morrigan.dev.gw2.client.gui.map;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.gui.AbstractGW2Waypoint;
import de.morrigan.dev.gw2.client.gui.components.FilterModel;
import de.morrigan.dev.gw2.client.gui.interfaces.IListenerAction;
import de.morrigan.dev.gw2.client.gui.interfaces.IStructuredView;
import de.morrigan.dev.gw2.client.gui.map.waypoints.HoverInfo;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Map;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Other;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Resource;
import de.morrigan.dev.gw2.client.gw2.api.GW2APIModel;
import de.morrigan.dev.gw2.client.gw2.api.IThreadCallback;
import de.morrigan.dev.gw2.client.model.GW2MapModel;
import de.morrigan.dev.gw2.client.model.GW2MapModel.MapActionMode;
import de.morrigan.dev.gw2.client.model.RightsModel;
import de.morrigan.dev.gw2.dto.MapInfoDTO;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.resources.ImageConstants;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.factories.MessageDialogFactory;

public class GW2MapPanel extends JXMapKit implements IObserver, IStructuredView, ActionListener {

	private enum ListenerAction implements IListenerAction {
		KEY_PRESSED,
		MOUSE_WHEEL_MOVED,
		MOUSE_MOVED,
		MOUSE_DRAGGED,
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_CLICKED,
		MOUSE_ENTERED,
		MENU_ITEM_SELECTED;
	}

	/** automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -2839484025284513168L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(GW2MapPanel.class);

	/** Handle auf den ResourceManager */
	private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

	/** Handel auf den ImageManager */
	private static final ImageConstants IMAGE_MANAGER = ImageConstants.getInstance();

	/** Anzahl der anzuzeigenden Kartenteile in der Horizontalen */
	private static final int SHOWING_TILES_WIDTH = 3;

	/** Anzahl der anzuzeigenden Kartenteile in der Vertikalen */
	private static final int SHOWING_TILES_HEIGHT = 2;

	/** ID des Default Continents, der gerendet wird (Tyria) */
	private static final int DEFAULT_CONTINENT_ID = 1;

	/** Default Floor-Wert mit der die Karte gerendert wird (= 0) */
	private static final int DEFAULT_FLOOR = 1;

	/** Default Schrittweite in Pixel, die die Karte per Tastatur bewegt wird */
	private static final int DEFAULT_STEP_SIZE = 5;

	/** Gibt den Versatz nach oben an, der bei der Anzeige des Tooltipps über dem Mauscursor angewendet wird */
	private static final int ROLLOVER_Y_OFFSET = 20;

	/** Zoom Level ab dem Markierungen auf der Karte angezeigt werden */
	private static final int MARKERS_ZOOM_LEVEL = 3;

	/**
	 * Gibt die Reichweite zum Mittelpunkt eines Wegpunktes für Tooltips an. Befindet sich der Mauscursor innerhalb dieser
	 * Reichweite, so wird ein Tooltip angezeigt.
	 */
	private static final int ROLLOVER_ACTION_SIZE = 10;

	private GW2MapPainter painter;
	private JXMapViewer mapViewer;
	private GW2MapInfoHoverPanel pnlMapHoverPanel;
	private GW2HoverPanel pnlHover;
	private GW2ResourceHoverPanel pnlResHover;

	private GeoPosition dragStartMousePos;
	private boolean draggingMode;

	private GW2MapModel model = GW2MapModel.getInstance();
	private FilterModel filterModel = FilterModel.getInstance();

	public GW2MapPanel() {
		super();

		try {

			createGUI();
			configureGUI();
			layoutGUI();
			configureListener();
			updateLanguage();

			this.model.addObserver(this);
			this.filterModel.addObserver(this);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		handleListenerEvent(ListenerAction.MENU_ITEM_SELECTED, event);
	}

	@Override
	public void configureGUI() {
		int continentId = DEFAULT_CONTINENT_ID;
		int floor = DEFAULT_FLOOR;
		TileFactory gw2TileFactory = new DefaultTileFactory(new GW2TileFactoryInfo(continentId, floor));
		setTileFactory(gw2TileFactory);
		setMiniMapVisible(false);
		setZoomSliderVisible(false);
		setZoomButtonsVisible(false);
		setZoom(GW2TileFactoryInfo.MAX_AVAILABLE_ZOOM);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(SHOWING_TILES_WIDTH * GW2TileFactoryInfo.TILE_SIZE, SHOWING_TILES_HEIGHT
				* GW2TileFactoryInfo.TILE_SIZE));
		setFocusable(true);

		this.mapViewer.setOverlayPainter(this.painter);
		this.mapViewer.setHorizontalWrapped(false);

		this.painter.setRenderer(new GW2MapRenderer(this.mapViewer, MARKERS_ZOOM_LEVEL));

		hideAllHovers();
	}

	@Override
	public void configureListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
			if (event.getID() == KeyEvent.KEY_PRESSED) {
				handleListenerEvent(ListenerAction.KEY_PRESSED, event);
			}
			return false;
		});
		addMouseWheelListener(event -> handleListenerEvent(ListenerAction.MOUSE_WHEEL_MOVED, event));
		this.mapViewer.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				handleListenerEvent(ListenerAction.MOUSE_CLICKED, event);
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				handleListenerEvent(ListenerAction.MOUSE_ENTERED, event);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hideAllHovers();
			}

			@Override
			public void mousePressed(MouseEvent event) {
				handleListenerEvent(ListenerAction.MOUSE_PRESSED, event);
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				handleListenerEvent(ListenerAction.MOUSE_RELEASED, event);
			}
		});
		this.mapViewer.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent event) {
				handleListenerEvent(ListenerAction.MOUSE_DRAGGED, event);
			}

			@Override
			public void mouseMoved(MouseEvent event) {
				handleListenerEvent(ListenerAction.MOUSE_MOVED, event);
			}
		});
	}

	@Override
	public void createGUI() {
		this.mapViewer = getMainMap();
		this.pnlMapHoverPanel = new GW2MapInfoHoverPanel();
		this.pnlHover = new GW2HoverPanel();
		this.pnlResHover = new GW2ResourceHoverPanel();
		this.painter = new GW2MapPainter(this.mapViewer);
	}

	public JXMapViewer getMapViewer() {
		return this.mapViewer;
	}

	public GW2MapModel getModel() {
		return this.model;
	}

	@Override
	public void handleListenerEvent(IListenerAction listenerAction, EventObject event) {
		if (LOG.isDebugEnabled() && (((ListenerAction) listenerAction) != ListenerAction.MOUSE_MOVED)
				&& (((ListenerAction) listenerAction) != ListenerAction.MOUSE_DRAGGED)) {
			LOG.debug("listenerAction: {}, event: {}", listenerAction, event);
		}
		try {
			final ListenerAction action = (ListenerAction) listenerAction;
			switch (action) {
				case KEY_PRESSED:
					int keyCode = ((KeyEvent) event).getKeyCode();
					if (keyCode == KeyEvent.VK_PLUS) {
						zoomMap(-1, null);
					} else if (keyCode == KeyEvent.VK_MINUS) {
						zoomMap(1, null);
					}
				break;
				case MOUSE_ENTERED:
				break;
				case MOUSE_WHEEL_MOVED:
					MouseWheelEvent mouseWheelEvent = (MouseWheelEvent) event;
					zoomMap(mouseWheelEvent.getWheelRotation(), mouseWheelEvent.getPoint());
				break;
				case MOUSE_MOVED:
					checkRollover((MouseEvent) event);
				break;
				case MOUSE_DRAGGED:
					MouseEvent mouseEvent = (MouseEvent) event;
					if (this.draggingMode) {
						Point2D currentMousePos = mouseEvent.getPoint();
						GeoPosition currentMouseGeoPos = this.mapViewer.convertPointToGeoPosition(currentMousePos);
						double latitudeDist = this.dragStartMousePos.getLatitude() - currentMouseGeoPos.getLatitude();
						double longitudeDist = this.dragStartMousePos.getLongitude()
								- currentMouseGeoPos.getLongitude();
						GeoPosition currentCenter = this.mapViewer.getCenterPosition();
						GeoPosition newCenter = new GeoPosition(currentCenter.getLatitude() + latitudeDist,
								currentCenter.getLongitude() + longitudeDist);
						this.mapViewer.setCenterPosition(newCenter);
						this.dragStartMousePos = this.mapViewer.convertPointToGeoPosition(mouseEvent.getPoint());
					}
				break;
				case MOUSE_PRESSED:
					MouseEvent mousepEvent = (MouseEvent) event;
					if (((mousepEvent.getButton() == MouseEvent.BUTTON1)
							&& (this.model.getMapActionMode() == MapActionMode.DEFAULT))
							|| (mousepEvent.getButton() == MouseEvent.BUTTON3)) {
						this.dragStartMousePos = this.mapViewer.convertPointToGeoPosition(mousepEvent.getPoint());
						this.draggingMode = true;
					}
				break;
				case MOUSE_RELEASED:
					this.draggingMode = false;
				break;
				case MOUSE_CLICKED:
					MouseEvent mousecEvent = (MouseEvent) event;
					if (mousecEvent.getButton() == MouseEvent.BUTTON3) {
						hideAllHovers();
						if (this.model.getMapActionMode() == MapActionMode.SET_WAYPOINT) {
							this.model.resetWaypointMode();
						} else {
							if (this.mapViewer.getZoom() <= MARKERS_ZOOM_LEVEL) {
								if (this.model.isActiveWaypointAvailable()) {
									showEditMenu(mousecEvent);
								} else {
									showCreateMenu(mousecEvent);
								}
							} else {
								showMapEditMenu(mousecEvent);
							}
						}
					} else if (mousecEvent.getButton() == MouseEvent.BUTTON1) {
						if (this.model.getMapActionMode() == MapActionMode.SET_WAYPOINT) {
							GeoPosition newWPPosition = this.mapViewer
									.convertPointToGeoPosition(mousecEvent.getPoint());
							this.model.setWaypoint(newWPPosition);
						}
					}
				break;
				case MENU_ITEM_SELECTED:
					ActionEvent actionEvent = (ActionEvent) event;
					String command = actionEvent.getActionCommand();
					if (GW2MapModel.AC_DELETE_WP.equals(command)) {
						this.model.deleteActiveWaypoint();
					} else if (GW2MapModel.AC_RICH_WP.equals(command)) {
						this.model.setActiveWaypointAsRich();
					} else if (GW2MapModel.AC_PERMANENT_WP.equals(command)) {
						this.model.setActiveWaypointAsPermanent();
					} else {
						this.model.prepareWaypoint(command);
					}
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

	public void hideAllHovers() {
		this.pnlMapHoverPanel.setVisible(false);
		this.pnlHover.setVisible(false);
		this.pnlResHover.setVisible(false);
	}

	public void initialize() throws ServiceException {
		this.model.initialize();
	}

	@Override
	public void layoutGUI() {
		// keine GUI-Elemente für ein Layout vorhanden
	}

	@Override
	public void update(IObservable obs, long updateFlag) {
		LOG.debug("obs: {}, updateFlag: {}", obs, updateFlag);
		if (obs instanceof GW2MapModel) {
			if ((updateFlag & GW2MapModel.MAP_ACTION_MODE_CHANGED) != 0) {
				switch (this.model.getMapActionMode()) {
					case DEFAULT:
						setCursor(Cursor.getDefaultCursor());
					break;
					case SET_WAYPOINT:
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					break;

					default:
					break;
				}
			}
			boolean waypointChanged = (updateFlag & GW2MapModel.WAYPOINTS_CHANGED) != 0;
			if (waypointChanged) {
				this.painter.setWaypoints(this.model.getWaypoints());
				this.mapViewer.repaint();
			}
		}
		if (obs instanceof FilterModel) {
			this.painter.setWaypoints(this.model.getWaypoints());
			this.mapViewer.repaint();
		}
	}

	@Override
	public void updateLanguage() {
		// keine GUI-Elemente mit Beschriftung vorhanden
	}

	private void checkRollover(MouseEvent mouseMoveEvent) {
		boolean shownHover = false;
		boolean shownResHover = false;
		boolean shownMapInfoHover = false;
		final Point hoverPoint = mouseMoveEvent.getLocationOnScreen();
		final Point absoluteMousePoint = mouseMoveEvent.getLocationOnScreen();
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (this.mapViewer.getZoom() > MARKERS_ZOOM_LEVEL) {
			final WPGW2Map wpgw2Map = getMapByMousePosition(mouseMoveEvent);
			if (wpgw2Map != null) {
				shownMapInfoHover = true;
				GeoPosition topLeft = wpgw2Map.getTopLeft();
				GeoPosition bottomRight = wpgw2Map.getBottomRight();
				this.model.getMapInfo(wpgw2Map.getMapName(), topLeft.getLatitude(), topLeft.getLongitude(),
						bottomRight.getLatitude(), bottomRight.getLongitude(), new IThreadCallback() {

							@Override
							public void basicDataAvailable() {
								MapInfoDTO mapInfo = GW2MapPanel.this.model.getLastMapInfo();
								if (mapInfo != null) {
									Map<WPSubType, Integer> resourceAmount = mapInfo.getResourceAmount();
									GW2MapPanel.this.pnlMapHoverPanel.setData(wpgw2Map.getMapName(), resourceAmount);
								}
							}

							@Override
							public void waypointsAvailable() {
								// nicht benötigt
							}
						});

				if (absoluteMousePoint.y < 360) {
					hoverPoint.y = hoverPoint.y + ROLLOVER_Y_OFFSET;
				} else {
					hoverPoint.y = hoverPoint.y - GW2MapPanel.this.pnlMapHoverPanel.getHeight() - ROLLOVER_Y_OFFSET;
				}
				if (absoluteMousePoint.x > (screenSize.width - 360)) {
					hoverPoint.x = hoverPoint.x - GW2MapPanel.this.pnlMapHoverPanel.getWidth();
				}
				GW2MapPanel.this.pnlMapHoverPanel.setLocation(hoverPoint);
			}

		} else {
			List<Waypoint> wps = new ArrayList<>(this.painter.getWaypoints());
			for (int i = 0; i < wps.size(); i++) {
				final Waypoint waypoint = wps.get(i);
				if (waypoint instanceof AbstractGW2Waypoint) {
					AbstractGW2Waypoint gw2Waypoint = (AbstractGW2Waypoint) waypoint;
					if (gw2Waypoint.isHoverEnabled()) {
						// location of Java
						GeoPosition gp = waypoint.getPosition();
						// convert to world bitmap
						Point2D gpPT = this.mapViewer.convertGeoPositionToPoint(gp);
						// convert to screen
						Point convertedGpPT = new Point((int) gpPT.getX(), (int) gpPT.getY());
						// check if near the mouse
						Point mousePoint = mouseMoveEvent.getPoint();
						if (convertedGpPT.distance(mousePoint) < ROLLOVER_ACTION_SIZE) {
							if ((gw2Waypoint.getWpType() == WPType.POI) //
									|| (gw2Waypoint.getWpType() == WPType.UNLOCK)
									|| (gw2Waypoint.getWpType() == WPType.WAYPOINT)
									|| (gw2Waypoint.getWpType() == WPType.HEART)) {
								this.pnlHover.setInfo(gw2Waypoint.getHoverInfo());
								if (absoluteMousePoint.y < 360) {
									hoverPoint.y = hoverPoint.y + ROLLOVER_Y_OFFSET;
								} else {
									hoverPoint.y = hoverPoint.y - this.pnlHover.getHeight() - ROLLOVER_Y_OFFSET;
								}
								if (absoluteMousePoint.x > (screenSize.width - 360)) {
									hoverPoint.x = hoverPoint.x - this.pnlHover.getWidth();
								}
								this.pnlHover.setLocation(hoverPoint);
								shownHover = true;
							} else if ((gw2Waypoint.getWpType() == WPType.ORE)
									|| (gw2Waypoint.getWpType() == WPType.PLANT)
									|| (gw2Waypoint.getWpType() == WPType.WOOD)) {
								WPGW2Resource resWaypoint = (WPGW2Resource) gw2Waypoint;
								this.model.setActiveWaypoint(resWaypoint);
								StringBuilder createInfo = new StringBuilder();
								createInfo.append(
										new SimpleDateFormat("dd.MM.yy - HH:mm", Locale.getDefault()).format(resWaypoint.getUpdateDate()));
								createInfo.append(" ");
								createInfo.append(RESOURCE_MANAGER.getLabel("by"));
								createInfo.append(" ");
								createInfo.append(resWaypoint.getUsername());
								HoverInfo info = WPGW2Resource
										.getHoverInfoBySubType(resWaypoint.getWPSubType(), -1, -1);
								this.pnlResHover.setData(resWaypoint.getHoverInfo(), info.resourceIcon,
										info.resourceLabel, info.gatheringIcon, info.gatheringLabel,
										createInfo.toString());
								hoverPoint.y = hoverPoint.y - this.pnlResHover.getHeight() - ROLLOVER_Y_OFFSET;
								if (absoluteMousePoint.x > (screenSize.width - 360)) {
									hoverPoint.x = hoverPoint.x - this.pnlResHover.getWidth();
								}
								this.pnlResHover.setLocation(hoverPoint);
								shownResHover = true;
							} else if (gw2Waypoint.getWpType() == WPType.OTHER) {
								WPGW2Other otherWP = (WPGW2Other) gw2Waypoint;
								if (otherWP.getWPSubType() == WPSubType.GUILD_BOUNTY) {
									this.model.setActiveWaypoint(otherWP);
									this.pnlHover.setInfo(gw2Waypoint.getHoverInfo());
									hoverPoint.y = hoverPoint.y - this.pnlHover.getHeight() - ROLLOVER_Y_OFFSET;
									if (absoluteMousePoint.x > (screenSize.width - 360)) {
										hoverPoint.x = hoverPoint.x - this.pnlHover.getWidth();
									}
									this.pnlHover.setLocation(hoverPoint);
									shownHover = true;
								}
							}
							break;
						}
					}
				}
			}
		}
		this.pnlMapHoverPanel.setVisible(shownMapInfoHover);
		this.pnlHover.setVisible(shownHover);
		this.pnlResHover.setVisible(shownResHover);
		if (!shownHover && !shownResHover) {
			this.model.setActiveWaypoint(null);
		}
	}

	private WPGW2Map getMapByMousePosition(MouseEvent mouseMoveEvent) {
		WPGW2Map result = null;
		List<WPGW2Map> gw2MapInfo = GW2APIModel.getInstance().getGw2MapInfo();
		for (WPGW2Map wpgw2Map : gw2MapInfo) {
			// location of Java
			GeoPosition topLeft = wpgw2Map.getTopLeft();
			GeoPosition bottomRight = wpgw2Map.getBottomRight();
			// convert to world bitmap
			Point2D topLeftPoint = this.mapViewer.convertGeoPositionToPoint(topLeft);
			Point2D bottomRightPoint = this.mapViewer.convertGeoPositionToPoint(bottomRight);
			// convert to screen
			Point convertedTopLeftPoint = new Point((int) topLeftPoint.getX(), (int) topLeftPoint.getY());
			Point convertedBottomRightPoint = new Point((int) bottomRightPoint.getX(), (int) bottomRightPoint.getY());
			// check if near the mouse
			Point mousePoint = mouseMoveEvent.getPoint();
			if ((mousePoint.getX() > convertedTopLeftPoint.getX())
					&& (mousePoint.getX() < convertedBottomRightPoint.getX())
					&& (mousePoint.getY() > convertedTopLeftPoint.getY())
					&& (mousePoint.getY() < convertedBottomRightPoint.getY())) {
				result = wpgw2Map;
				break;
			}
		}
		return result;
	}

	private JMenuItem getShowHideMiniMapMenuItem() {
		final Main main = Main.getInstance();
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem();
		if (main.isMiniMapShown()) {
			menuItem.setSelected(true);
			menuItem.setText(RESOURCE_MANAGER.getLabel("hideMiniMap"));
		} else {
			menuItem.setSelected(false);
			menuItem.setText(RESOURCE_MANAGER.getLabel("showMiniMap"));
		}
		menuItem.addActionListener(event -> {
			if (main.isMiniMapShown()) {
				main.hideMiniMap();
			} else {
				main.showMiniMap();
			}

		});
		return menuItem;
	}

	private void showCreateMenu(MouseEvent event) {
		ImageIcon oreIcon = IMAGE_MANAGER.getImageIcon(ImageConstants.ORE_ICON, 15, 15);
		ImageIcon woodIcon = IMAGE_MANAGER.getImageIcon(ImageConstants.WOOD_ICON, 15, 15);
		ImageIcon plantIcon = IMAGE_MANAGER.getImageIcon(ImageConstants.PLANT_ICON, 15, 15);

		JPopupMenu menu = new JPopupMenu();
		JMenu menuOre = new JMenu(RESOURCE_MANAGER.getLabel("ore"));
		JMenu menuWood = new JMenu(RESOURCE_MANAGER.getLabel("wood"));
		JMenu menuPlant = new JMenu(RESOURCE_MANAGER.getLabel("plant"));
		JMenu menuOther = new JMenu(RESOURCE_MANAGER.getLabel("other"));
		menuOre.setIcon(oreIcon);
		menuWood.setIcon(woodIcon);
		menuPlant.setIcon(plantIcon);
		menu.add(menuOre);
		menu.add(menuWood);
		menu.add(menuPlant);
		menu.add(menuOther);
		menu.addSeparator();
		menu.add(getShowHideMiniMapMenuItem());

		JMenu menuOriPlant = new JMenu(RESOURCE_MANAGER.getLabel("orichalcumHarvestingSickle"));
		JMenu menuMithrilPlant = new JMenu(RESOURCE_MANAGER.getLabel("mithrilHarvestingSickle"));
		JMenu menuDarksteelPlant = new JMenu(RESOURCE_MANAGER.getLabel("darksteelHarvestingSickle"));
		JMenu menuSteelPlant = new JMenu(RESOURCE_MANAGER.getLabel("steelHarvestingSickle"));
		JMenu menuIronPlant = new JMenu(RESOURCE_MANAGER.getLabel("ironHarvestingSickle"));
		JMenu menuCopperPlant = new JMenu(RESOURCE_MANAGER.getLabel("copperHarvestingSickle"));
		menuPlant.add(menuOriPlant);
		menuPlant.add(menuMithrilPlant);
		menuPlant.add(menuDarksteelPlant);
		menuPlant.add(menuSteelPlant);
		menuPlant.add(menuIronPlant);
		menuPlant.add(menuCopperPlant);

		// Erzadern
		Map<String, MenuEntry> oreMenuEntries = this.model.getOreMenuEntries();
		List<MenuEntry> entries = new ArrayList<>(oreMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuOre.add(item);
		}

		// Hölzer
		Map<String, MenuEntry> woodMenuEntries = this.model.getWoodMenuEntries();
		entries = new ArrayList<>(woodMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuWood.add(item);
		}

		// Planzen
		Map<String, MenuEntry> plantOriMenuEntries = this.model.getPlantOriMenuEntries();
		entries = new ArrayList<>(plantOriMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuOriPlant.add(item);
		}
		Map<String, MenuEntry> plantMithrilMenuEntries = this.model.getPlantMithrilMenuEntries();
		entries = new ArrayList<>(plantMithrilMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuMithrilPlant.add(item);
		}
		Map<String, MenuEntry> plantDarksteelMenuEntries = this.model.getPlantDarksteelMenuEntries();
		entries = new ArrayList<>(plantDarksteelMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuDarksteelPlant.add(item);
		}
		Map<String, MenuEntry> plantSteelMenuEntries = this.model.getPlantSteelMenuEntries();
		entries = new ArrayList<>(plantSteelMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuSteelPlant.add(item);
		}
		Map<String, MenuEntry> plantIronMenuEntries = this.model.getPlantIronMenuEntries();
		entries = new ArrayList<>(plantIronMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuIronPlant.add(item);
		}
		Map<String, MenuEntry> plantCopperMenuEntries = this.model.getPlantCopperMenuEntries();
		entries = new ArrayList<>(plantCopperMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuCopperPlant.add(item);
		}

		// Sonstige
		Map<String, MenuEntry> otherMenuEntries = this.model.getOtherMenuEntries();
		entries = new ArrayList<>(otherMenuEntries.values());
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++) {
			MenuEntry entry = entries.get(i);
			JMenuItem item = new JMenuItem(RESOURCE_MANAGER.getLabel(entry.label), entry.icon);
			item.setActionCommand(entry.actionCommand);
			item.addActionListener(this);
			menuOther.add(item);
		}

		menu.show(this.mapViewer, event.getX(), event.getY());
	}

	private void showEditMenu(MouseEvent event) {
		JPopupMenu menu = new JPopupMenu();

		JMenuItem itemRich = new JMenuItem(RESOURCE_MANAGER.getLabel("rich"), IMAGE_MANAGER.getImageIcon(
				ImageConstants.RICH_ICON, 10, 10));
		itemRich.setActionCommand(GW2MapModel.AC_RICH_WP);
		itemRich.addActionListener(this);
		JMenuItem itemPermanent = new JMenuItem(RESOURCE_MANAGER.getLabel("permanent"), IMAGE_MANAGER.getImageIcon(
				ImageConstants.PERMANENT_ICON, 10, 10));
		itemPermanent.setActionCommand(GW2MapModel.AC_PERMANENT_WP);
		itemPermanent.addActionListener(this);

		JMenuItem itemDelete = new JMenuItem(RESOURCE_MANAGER.getLabel("delete"), IMAGE_MANAGER.getImageIcon(
				ImageConstants.DELETE_ICON, 10, 10));
		itemDelete.setActionCommand(GW2MapModel.AC_DELETE_WP);
		itemDelete.addActionListener(this);

		menu.add(itemRich);
		menu.add(itemPermanent);
		menu.addSeparator();
		menu.add(itemDelete);
		menu.addSeparator();
		menu.add(getShowHideMiniMapMenuItem());
		menu.show(this.mapViewer, event.getX(), event.getY());
	}

	private void showMapEditMenu(final MouseEvent event) {
		JPopupMenu menu = new JPopupMenu();

		JMenuItem itemDelete = new JMenuItem(RESOURCE_MANAGER.getLabel("resetSpots"), IMAGE_MANAGER.getImageIcon(
				ImageConstants.DELETE_ICON, 10, 10));
		itemDelete.setActionCommand(GW2MapModel.AC_DELETE_WP);
		itemDelete.addActionListener(aEvent -> {
			WPGW2Map wpgw2Map = getMapByMousePosition(event);
			if (wpgw2Map != null) {
				GeoPosition topLeft = wpgw2Map.getTopLeft();
				GeoPosition bottomRight = wpgw2Map.getBottomRight();
				double fromLatitude = topLeft.getLatitude();
				double fromLongitude = topLeft.getLongitude();
				double toLatitude = bottomRight.getLatitude();
				double toLongitude = bottomRight.getLongitude();
				try {
					GW2MapModel.getInstance().deleteWaypoints(wpgw2Map.getMapName(), fromLatitude, fromLongitude,
							toLatitude, toLongitude);
				} catch (ServiceException e) {
					LOG.error(e.getMessage(), e);
					MessageDialogFactory.handleExcpetion(Main.getInstance().getMainFrame(), e, null);
				}
			}
		});

		if (RightsModel.getInstance().hasRight("deleteWaypointByMapName")) {
			menu.add(itemDelete);
			menu.addSeparator();
		}
		menu.add(getShowHideMiniMapMenuItem());
		menu.show(this.mapViewer, event.getX(), event.getY());
	}

	private void zoomMap(int direction, Point mousePosition) {
		if (direction < 0) {
			if ((mousePosition != null) && (this.mapViewer.getZoom() > GW2TileFactoryInfo.MIN_AVAILABLE_ZOOM)) {
				GeoPosition zoomPoint = this.mapViewer.convertPointToGeoPosition(mousePosition);
				setCenterPosition(zoomPoint);
			}
			getZoomOutAction().actionPerformed(
					new ActionEvent(this.mapViewer, ActionEvent.ACTION_PERFORMED, "mouseWheelRotated"));
		} else {
			getZoomInAction().actionPerformed(
					new ActionEvent(this.mapViewer, ActionEvent.ACTION_PERFORMED, "mouseWheelRotated"));
		}
	}
}
