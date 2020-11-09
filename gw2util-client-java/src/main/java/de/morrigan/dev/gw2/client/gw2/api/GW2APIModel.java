package de.morrigan.dev.gw2.client.gw2.api;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zweistein.gw2.api.GW2API;
import cz.zweistein.gw2.api.dto.AreaMap;
import cz.zweistein.gw2.api.dto.Color;
import cz.zweistein.gw2.api.dto.Continent;
import cz.zweistein.gw2.api.dto.Heart;
import cz.zweistein.gw2.api.dto.MapAreaFloor;
import cz.zweistein.gw2.api.dto.MapFloor;
import cz.zweistein.gw2.api.dto.MapRectangle;
import cz.zweistein.gw2.api.dto.MapRegion;
import cz.zweistein.gw2.api.dto.Point2D;
import cz.zweistein.gw2.api.dto.PointOfInterest;
import cz.zweistein.gw2.api.dto.Recipe;
import cz.zweistein.gw2.api.dto.SkillChallenge;
import cz.zweistein.gw2.api.dto.items.Item;
import cz.zweistein.gw2.api.util.SupportedLanguage;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Heart;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Map;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2PointOfInterest;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Skill;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Unlock;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Vista;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Waypoint;
import de.morrigan.dev.gw2.client.model.InfoMessageModel;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.swing.models.AbstractModel;

public final class GW2APIModel extends AbstractModel {

	/** Handel auf den LabelManager */
	private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(GW2APIModel.class);

	public static final Long TYRIA_ID = 1L;
	public static final Long DEFAULT_FLOOR = 1L;

	private static final GW2APIModel INSTANCE = new GW2APIModel();

	public static GW2APIModel getInstance() {
		return INSTANCE;
	}

	private SupportedLanguage language;

	private GW2API gw2API;
	private Long build;
	private List<GW2Color> colors = new ArrayList<>();
	private List<GW2Continent> continents = new ArrayList<GW2Continent>();
	private List<GW2MapName> maps = new ArrayList<GW2MapName>();
	private List<GW2EventName> eventNames = new ArrayList<GW2EventName>();
	private List<GW2WorldName> worldNames = new ArrayList<GW2WorldName>();
	private List<GW2MapRegion> mapRegions = new ArrayList<GW2MapRegion>();
	private List<WPGW2Waypoint> gw2Waypoints = new ArrayList<WPGW2Waypoint>();
	private List<WPGW2PointOfInterest> gw2POIs = new ArrayList<WPGW2PointOfInterest>();
	private List<WPGW2Unlock> gw2Unlock = new ArrayList<WPGW2Unlock>();
	private List<WPGW2Vista> gw2Vista = new ArrayList<WPGW2Vista>();
	private List<WPGW2Skill> gw2Skill = new ArrayList<WPGW2Skill>();
	private List<WPGW2Heart> gw2Heart = new ArrayList<WPGW2Heart>();
	private List<WPGW2Map> gw2MapInfo = new CopyOnWriteArrayList<WPGW2Map>();

	private Map<Long, Item> items;
	private Map<Long, Recipe> recipes;

	private InfoMessageModel infoMsgModel = InfoMessageModel.getInstance();
	private List<IThreadCallback> callbacks = new ArrayList<IThreadCallback>();

	private GW2APIModel() {
		super();

		this.language = SupportedLanguage.DEUTCH;

		try {
			this.gw2API = new GW2API();
		} catch (RemoteException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public void addCallback(IThreadCallback callback) {
		this.callbacks.add(callback);
	}

	public Long getBuild() {
		return this.build;
	}

	public List<GW2Color> getColors() {
		return this.colors;
	}

	public List<GW2Continent> getContinents() {
		return this.continents;
	}

	public List<GW2EventName> getEventNames() {
		return this.eventNames;
	}

	public List<WPGW2Heart> getGw2Heart() {
		return this.gw2Heart;
	}

	public List<WPGW2Map> getGw2MapInfo() {
		return this.gw2MapInfo;
	}

	public List<WPGW2PointOfInterest> getGw2POIs() {
		return this.gw2POIs;
	}

	public List<WPGW2Skill> getGw2Skill() {
		return this.gw2Skill;
	}

	public List<WPGW2Unlock> getGw2Unlock() {
		return this.gw2Unlock;
	}

	public List<WPGW2Vista> getGw2Vista() {
		return this.gw2Vista;
	}

	public List<WPGW2Waypoint> getGw2Waypoints() {
		return this.gw2Waypoints;
	}

	public SupportedLanguage getLanguage() {
		return this.language;
	}

	public AreaMap getMapDetails(Long mapId) {
		Map<Long, AreaMap> mapDetail = null;
		try {
			mapDetail = this.gw2API.getMapDetail(mapId, this.language);
		} catch (RemoteException e) {
			LOG.error(e.getMessage(), e);
		}
		return mapDetail.get(mapId);
	}

	public MapFloor getMapFloor(Long continentId, Long floor) {
		MapFloor mapFloor = null;
		try {
			mapFloor = this.gw2API.getMapFloor(continentId, floor, this.language);
		} catch (RemoteException e) {
			LOG.error(e.getMessage(), e);
		}
		return mapFloor;
	}

	public List<GW2MapName> getMapNames() {
		return this.maps;
	}

	public List<GW2MapRegion> getMapRegions() {
		return this.mapRegions;
	}

	public List<GW2WorldName> getWorldNames() {
		return this.worldNames;
	}

	public void loadDataFromAPI() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					loadData();

					for (IThreadCallback callback : GW2APIModel.this.callbacks) {
						callback.basicDataAvailable();
					}
				} catch (RemoteException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}).start();
	}

	public void loadMapRegions(Long continentId) throws RemoteException {
		LOG.debug("continentId: {}", continentId);
		LOG.info("Lade Map-Regionen");
		this.infoMsgModel.setMessage(RESOURCE_MANAGER.getMessage("load_map_areas"));
		// List<Long> floors = new ArrayList<Long>();
		// for (GW2Continent con : this.continents) {
		// if (con.getId() == continentId) {
		// floors = con.getContinent().getFloors();
		// }
		// }
		this.mapRegions = new ArrayList<GW2MapRegion>();
		MapFloor mapFloor = this.gw2API.getMapFloor(continentId, DEFAULT_FLOOR, this.language);
		Map<Long, MapRegion> mapRegionsFromAPI = mapFloor.getRegions();
		List<PointOfInterest> addedPOIs = new ArrayList<>();
		for (Long id : mapRegionsFromAPI.keySet()) {
			if (id == 8) {
				// In dieser Region sind noch weitere Maps enthalten, die nicht zu Tyria gehören. Daher werden nur
				// die beiden verbliebenen Maps 'Mahlstromgipfel' und 'Funkenschwärmersumpf' geladen.
				MapRegion mapRegion = mapRegionsFromAPI.get(id);
				Map<Long, MapAreaFloor> newMaps = new HashMap<>();
				Map<Long, MapAreaFloor> maps = mapRegion.getMaps();
				for (Long key : maps.keySet()) {
					List<PointOfInterest> pois = maps.get(key).getPois();
					List<PointOfInterest> poisToAdd = new ArrayList<>();
					for (PointOfInterest pointOfInterest : pois) {
						boolean found = false;
						for (PointOfInterest addedPOI : addedPOIs) {
							if (addedPOI.getId().equals(pointOfInterest.getId())) {
								found = true;
								break;
							}
						}
						if (!found) {
							poisToAdd.add(pointOfInterest);
						}
					}
					MapAreaFloor curMAFloor = maps.get(key);
					MapAreaFloor newMAFloor = new MapAreaFloor(curMAFloor.getName(), curMAFloor.getMinLevel(),
							curMAFloor.getMaxLevel(), curMAFloor.getMapRectangle(), curMAFloor.getContinentRectangle(),
							curMAFloor.getDefaultFloor(), curMAFloor.getSkillChallenges(), poisToAdd,
							curMAFloor.getTasks(), curMAFloor.getSectors());
					newMaps.put(key, newMAFloor);
				}
				mapRegionsFromAPI.get(id).setMaps(newMaps);
			}
			Map<Long, MapAreaFloor> maps = mapRegionsFromAPI.get(id).getMaps();
			for (MapAreaFloor mapAreaFoor : maps.values()) {
				addedPOIs.addAll(mapAreaFoor.getPois());
			}
			GW2MapRegion gw2MapRegion = new GW2MapRegion(id, mapRegionsFromAPI.get(id));
			if (!this.mapRegions.contains(gw2MapRegion)) {
				LOG.debug("MapRegion hinzugefügt, id: {}, name: {}", id, mapRegionsFromAPI.get(id).getName());
				this.mapRegions.add(gw2MapRegion);
			} else {
				LOG.debug("MapRegion existiert bereits, id: {}, name: {}", id, mapRegionsFromAPI.get(id).getName());
			}
		}

	}

	public void loadWaypointsFromAPI(final JXMapViewer mapViewer) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				loadWaypoints(mapViewer);

				for (IThreadCallback callback : GW2APIModel.this.callbacks) {
					callback.waypointsAvailable();
				}
			}
		}).start();
	}

	private void loadColors() throws RemoteException {
		LOG.info("Laden Farben");
		this.infoMsgModel.setMessage(RESOURCE_MANAGER.getMessage("load_colors"));
		Map<Long, Color> colorsFromAPI = this.gw2API.getColors();
		for (Long id : colorsFromAPI.keySet()) {
			this.colors.add(new GW2Color(id, colorsFromAPI.get(id)));
		}
	}

	private void loadContinents() throws RemoteException {
		LOG.info("Lade Kontinente");
		this.infoMsgModel.setMessage(RESOURCE_MANAGER.getMessage("load_continents"));
		Map<Long, Continent> continentsFromAPI = this.gw2API.getContinents(this.language);
		for (Long id : continentsFromAPI.keySet()) {
			this.continents.add(new GW2Continent(id, continentsFromAPI.get(id)));
		}
	}

	private void loadData() throws RemoteException {
		long startTime = System.currentTimeMillis();
		LOG.info("Lade Daten von ArenaNet API");
		this.build = this.gw2API.getBuild();

		loadColors();
		loadContinents();
		loadMapNames();
		//		loadEventNames(); //Disabled
		loadWorldNames();
		loadMapRegions(TYRIA_ID);

		// List<Long> itemIds = this.gw2API.getItems();
		// LOG.info("itemIds = " + itemIds);
		// this.items = new HashMap<Long, Item>();
		// for (Long id : itemIds) {
		// try {
		// this.items.put(id, this.gw2API.getItemDetails(id, this.language));
		// } catch (IllegalArgumentException e) {
		// LOG.warn("Unbekanntes Item, id: " + id);
		// }
		// }
		// LOG.info("Items geladen");
		// List<Long> recipeIds = this.gw2API.getRecipes();
		// this.recipes = new HashMap<Long, Recipe>();
		// for (Long id : recipeIds) {
		// this.recipes.put(id, this.gw2API.getRecipeDetails(id, this.language));
		// }
		// LOG.info("Rezepte geladen");
		long endTime = System.currentTimeMillis();
		if (LOG.isInfoEnabled()) {
			LOG.info("Daten erfolgreich geladen in: {} Sekunden",
					DurationFormatUtils.formatDuration(endTime - startTime, "mm:ss"));
		}
	}

	private void loadEventNames() throws RemoteException {
		LOG.info("Lade Eventnamen");
		this.infoMsgModel.setMessage(RESOURCE_MANAGER.getMessage("load_event_names"));
		Map<String, String> eventNamesFromAPI = this.gw2API.getEventNames(this.language);
		for (String id : eventNamesFromAPI.keySet()) {
			this.eventNames.add(new GW2EventName(id, eventNamesFromAPI.get(id)));
		}
	}

	private void loadMapNames() throws RemoteException {
		LOG.info("Lade Kartennamen");
		this.infoMsgModel.setMessage(RESOURCE_MANAGER.getMessage("load_map_names"));
		Map<Long, String> mapNamesFromAPI = this.gw2API.getMapNames(this.language);
		for (Long id : mapNamesFromAPI.keySet()) {
			this.maps.add(new GW2MapName(id, mapNamesFromAPI.get(id)));
		}
	}

	private void loadWaypoints(JXMapViewer mapViewer) {
		LOG.debug("mapViewer: {}", mapViewer);
		long startTime = System.currentTimeMillis();
		LOG.info("Lade Kartenmarkierungen");
		this.infoMsgModel.setMessage(RESOURCE_MANAGER.getMessage("load_map_waypoints"));

		List<String> mapNames = new ArrayList<String>();
		for (GW2MapName mapName : this.maps) {
			mapNames.add(mapName.getName());
		}

		this.gw2Waypoints = new ArrayList<WPGW2Waypoint>();
		this.gw2POIs = new ArrayList<WPGW2PointOfInterest>();
		this.gw2Unlock = new ArrayList<WPGW2Unlock>();
		this.gw2Vista = new ArrayList<WPGW2Vista>();
		this.gw2Skill = new ArrayList<WPGW2Skill>();
		this.gw2Heart = new ArrayList<WPGW2Heart>();
		for (GW2MapRegion region : this.mapRegions) {
			Map<Long, MapAreaFloor> mapAreaFloors = region.getMapRegion().getMaps();
			for (MapAreaFloor mapAreaFloor : mapAreaFloors.values()) {
				if (mapNames.contains(mapAreaFloor.getName())) {
					List<PointOfInterest> pois = mapAreaFloor.getPois();
					for (PointOfInterest pointOfInterest : pois) {
						java.awt.geom.Point2D wp = new Point();
						wp.setLocation(pointOfInterest.getLocation().getX(), pointOfInterest.getLocation().getY());
						GeoPosition wpToAdd = mapViewer.getTileFactory().pixelToGeo(wp, 0);
						switch (pointOfInterest.getType()) {
							case POINT_OF_INTEREST:
								this.gw2POIs.add(new WPGW2PointOfInterest(wpToAdd, pointOfInterest.getName()));
							break;
							case UNLOCK:
								this.gw2Unlock.add(new WPGW2Unlock(wpToAdd, pointOfInterest.getName()));
							break;
							case VISTA:
								this.gw2Vista.add(new WPGW2Vista(wpToAdd, pointOfInterest.getName()));
							break;
							case WAYPOINT:
								this.gw2Waypoints.add(new WPGW2Waypoint(wpToAdd, pointOfInterest.getName()));
							break;

							default:
							break;
						}
					}

					List<SkillChallenge> skillChallenges = mapAreaFloor.getSkillChallenges();
					for (SkillChallenge skillChallenge : skillChallenges) {
						Point2D location = skillChallenge.getLocation();
						java.awt.geom.Point2D wp = new Point();
						wp.setLocation(location.getX(), location.getY());
						GeoPosition wpToAdd = mapViewer.getTileFactory().pixelToGeo(wp, 0);
						this.gw2Skill.add(new WPGW2Skill(wpToAdd));
					}

					List<Heart> tasks = mapAreaFloor.getTasks();
					for (Heart heart : tasks) {
						Long id = heart.getId();
						Long level = heart.getLevel();
						Point2D location = heart.getLocation();
						StringBuilder hoverInfo = new StringBuilder(heart.getObjective());

						hoverInfo.append(" (");
						hoverInfo.append(RESOURCE_MANAGER.getLabel("level"));
						hoverInfo.append(" ").append(Long.toString(level));
						hoverInfo.append(")");

						java.awt.geom.Point2D wp = new Point();
						wp.setLocation(location.getX(), location.getY());
						GeoPosition wpToAdd = mapViewer.getTileFactory().pixelToGeo(wp, 0);
						this.gw2Heart.add(new WPGW2Heart(wpToAdd, hoverInfo.toString()));
					}

					MapRectangle continentRectangle = mapAreaFloor.getContinentRectangle();
					Point2D topLeft = continentRectangle.getTopLeft();
					Point2D rightBottom = continentRectangle.getRightBottom();
					Point tlPoint = new Point(topLeft.getX().intValue(), topLeft.getY().intValue());
					Point brPoint = new Point(rightBottom.getX().intValue(), rightBottom.getY().intValue());
					GeoPosition wpToAdd = mapViewer.getTileFactory().pixelToGeo(tlPoint, 0);
					GeoPosition tlPos = mapViewer.getTileFactory().pixelToGeo(tlPoint, 0);
					GeoPosition brPos = mapViewer.getTileFactory().pixelToGeo(brPoint, 0);
					this.gw2MapInfo.add(new WPGW2Map(wpToAdd, mapAreaFloor.getName(), mapAreaFloor.getMinLevel(),
							mapAreaFloor.getMaxLevel(), tlPos, brPos));
				}
			}
		}

		long endTime = System.currentTimeMillis();
		if (LOG.isInfoEnabled()) {
			LOG.info("Daten erfolgreich geladen in: {} Sekunden",
					DurationFormatUtils.formatDuration(endTime - startTime, "mm:ss"));
		}
		this.infoMsgModel.setMessage(RESOURCE_MANAGER.getMessage("load_process_finished"));
		this.infoMsgModel.startTimer();
	}

	private void loadWorldNames() throws RemoteException {
		LOG.info("Lade Weltnamen");
		this.infoMsgModel.setMessage(RESOURCE_MANAGER.getMessage("load_world_names"));
		Map<Long, String> worldNamesFromAPI = this.gw2API.getWorldNames(this.language);
		for (Long id : worldNamesFromAPI.keySet()) {
			this.worldNames.add(new GW2WorldName(id, worldNamesFromAPI.get(id)));
		}
	}
}
