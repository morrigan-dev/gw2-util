package de.morrigan.dev.gw2.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.PreferencesModel;
import de.morrigan.dev.gw2.client.gui.AbstractGW2Waypoint;
import de.morrigan.dev.gw2.client.gui.components.FilterModel;
import de.morrigan.dev.gw2.client.gui.map.MenuEntry;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Heart;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Map;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Other;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2PointOfInterest;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Resource;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Skill;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Unlock;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Vista;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Waypoint;
import de.morrigan.dev.gw2.client.gw2.api.IThreadCallback;
import de.morrigan.dev.gw2.dto.MapInfoDTO;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.map.WaypointDTO;
import de.morrigan.dev.gw2.dto.map.WaypointListWrapperDTO;
import de.morrigan.dev.gw2.dto.remote.JNDIServiceFactory;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteMapService;
import de.morrigan.dev.gw2.resources.ImageConstants;
import de.morrigan.dev.swing.factories.MessageDialogFactory;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.utils.BitUtil;

public class GW2MapModel extends AbstractModel {

  public enum MapActionMode {
    DEFAULT, SET_WAYPOINT
  }

  private static final GW2MapModel INSTANCE = new GW2MapModel();

  private static final int ICON_SIZE = 15;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(GW2MapModel.class);

  /** Handel auf den ImageManager */
  private static final ImageConstants IMAGE_MANAGER = ImageConstants.getInstance();

  private static final String AC_ORE_PREFIX = "add ore";
  private static final String AC_WOOD_PREFIX = "add wood";
  private static final String AC_PLANT_PREFIX = "add plant";
  private static final String AC_OTHER_PREFIX = "add other";
  public static final String AC_DELETE_WP = "delete waypoint";
  public static final String AC_RICH_WP = "rich waypoint";
  public static final String AC_PERMANENT_WP = "permanent waypoint";

  public static final long MAP_ACTION_MODE_CHANGED = BitUtil.setLongBit(0);

  public static final long WAYPOINTS_CHANGED = BitUtil.setLongBit(1);
  public static final long FILTER_CHANGED = BitUtil.setLongBit(2);

  public static final long ORE_ORICHALCUM = BitUtil.setLongBit(30);
  public static final long ORE_MITHRIL = BitUtil.setLongBit(31);
  public static final long ORE_PLATINUM = BitUtil.setLongBit(32);
  public static final long ORE_GOLD = BitUtil.setLongBit(33);
  public static final long ORE_SILVER = BitUtil.setLongBit(34);
  public static final long ORE_IRON = BitUtil.setLongBit(35);
  public static final long ORE_COPPER = BitUtil.setLongBit(36);
  public static final long WOOD_ANCIENT = BitUtil.setLongBit(40);

  public static final long WOOD_ELDER = BitUtil.setLongBit(41);
  public static final long WOOD_HARD = BitUtil.setLongBit(42);
  public static final long WOOD_SEASONED = BitUtil.setLongBit(43);
  public static final long WOOD_SOFT = BitUtil.setLongBit(44);
  public static final long WOOD_GREEN = BitUtil.setLongBit(45);
  public static final long PLANT_ORICHALCUM_STICKLE = BitUtil.setLongBit(50);

  public static final long PLANT_MITHRIL_STICKLE = BitUtil.setLongBit(51);
  public static final long PLANT_DARKSTEEL_STICKLE = BitUtil.setLongBit(52);
  public static final long PLANT_STEEL_STICKLE = BitUtil.setLongBit(53);
  public static final long PLANT_IRON_STICKLE = BitUtil.setLongBit(54);
  public static final long PLANT_COPPER_STICKLE = BitUtil.setLongBit(55);

  public static GW2MapModel getInstance() {
    return INSTANCE;
  }

  private List<WPGW2Map> mapInfoWPs = new ArrayList<>();
  private List<WPGW2Waypoint> waypointWPs = new ArrayList<>();
  private List<WPGW2PointOfInterest> poiWPs = new ArrayList<>();
  private List<WPGW2Unlock> unlockWPs = new ArrayList<>();
  private List<WPGW2Vista> vistaWPs = new ArrayList<>();
  private List<WPGW2Skill> skillWPs = new ArrayList<>();
  private List<WPGW2Heart> heartWPs = new ArrayList<>();

  // siehe: http://www.journaldev.com/378/
  private List<WPGW2Resource> resWPs = new CopyOnWriteArrayList<>();
  private List<WPGW2Other> otherWPs = new CopyOnWriteArrayList<>();

  private MapActionMode mapActionMode;
  private String command;
  private AbstractGW2Waypoint activeWaypoint;
  private Map<String, MenuEntry> oreMenuEntries = new HashMap<>();
  private Map<String, MenuEntry> woodMenuEntries = new HashMap<>();
  private Map<String, MenuEntry> plantOriMenuEntries = new HashMap<>();
  private Map<String, MenuEntry> plantMithrilMenuEntries = new HashMap<>();
  private Map<String, MenuEntry> plantDarksteelMenuEntries = new HashMap<>();
  private Map<String, MenuEntry> plantSteelMenuEntries = new HashMap<>();
  private Map<String, MenuEntry> plantIronMenuEntries = new HashMap<>();
  private Map<String, MenuEntry> plantCopperMenuEntries = new HashMap<>();
  private Map<String, MenuEntry> otherMenuEntries = new HashMap<>();

  private String lastMapInfoName;
  private MapInfoDTO lastMapInfo;

  private boolean runUpdater;

  private Thread updaterThread;

  private AuthenticationModel authModel = AuthenticationModel.getInstance();
  private FilterModel filterModel = FilterModel.getInstance();

  private IRemoteMapService mapService;

  private GW2MapModel() {
    super();
  }

  public void deleteActiveWaypoint() throws ServiceException {
    LOG.debug("activeWaypoint: {}", this.activeWaypoint);
    this.mapService.deleteWaypoint(this.authModel.getAuthDTO(), this.activeWaypoint.getId());
    if ((this.activeWaypoint.getWpType() == WPType.ORE) //
        || (this.activeWaypoint.getWpType() == WPType.WOOD) //
        || (this.activeWaypoint.getWpType() == WPType.PLANT)) {
      this.resWPs.remove(this.activeWaypoint);
    } else if (this.activeWaypoint.getWpType() == WPType.OTHER) {
      this.otherWPs.remove(this.activeWaypoint);
    }

    long syncFlag = 0;
    syncFlag |= WAYPOINTS_CHANGED;

    // Daten haben sich geändert, daher müssen die MaoInfos auch neu geladen werden bei der nächsten Anzeige
    this.lastMapInfoName = null;

    if ((syncFlag != 0) && !isChanging()) {
      syncViews(syncFlag);
    }
  }

  public void deleteWaypoints(String mapName, double fromLatitude, double fromLongitude, double toLatitude,
      double toLongitude) throws ServiceException {
    LOG.debug("fromLatitude: {}, fromLongitude: {}, toLatitude: {}, toLongitude: {}", fromLatitude, fromLongitude,
        toLatitude, toLongitude);
    this.mapService.deleteWaypoints(this.authModel.getAuthDTO(), mapName, fromLatitude, fromLongitude, toLatitude,
        toLongitude);

    long syncFlag = 0;
    reloadMapData();
    syncFlag |= WAYPOINTS_CHANGED;

    // Daten haben sich geändert, daher müssen die MaoInfos auch neu geladen werden bei der nächsten Anzeige
    this.lastMapInfoName = null;

    if ((syncFlag != 0) && !isChanging()) {
      syncViews(syncFlag);
    }
  }

  public Waypoint getActiveWaypoint() {
    return this.activeWaypoint;
  }

  public MapInfoDTO getLastMapInfo() {
    return this.lastMapInfo;
  }

  public MapActionMode getMapActionMode() {
    return this.mapActionMode;
  }

  public MapInfoDTO getMapInfo(final String mapName, final double fromLatitude, final double fromLongitude,
      final double toLatitude, final double toLongitude, final IThreadCallback callback) {
    if (checkValueChanged(this.lastMapInfoName, mapName)) {
      this.lastMapInfoName = mapName;
      Thread t = new Thread(() -> {
        try {
          GW2MapModel.this.lastMapInfo = GW2MapModel.this.mapService.getMapInfo(
              GW2MapModel.this.authModel.getAuthDTO(), mapName, fromLatitude, fromLongitude,
              toLatitude, toLongitude);
          LOG.info("lastMapInfoName = {}, mapName = {}", GW2MapModel.this.lastMapInfoName, mapName);
          if (!checkValueChanged(GW2MapModel.this.lastMapInfoName, mapName)) {
            callback.basicDataAvailable();
          }
        } catch (ServiceException e) {
          LOG.error(e.getMessage(), e);
        }
      });
      t.start();
    }
    return this.lastMapInfo;
  }

  public Map<String, MenuEntry> getOreMenuEntries() {
    return this.oreMenuEntries;
  }

  public Map<String, MenuEntry> getOtherMenuEntries() {
    return this.otherMenuEntries;
  }

  public Map<String, MenuEntry> getPlantCopperMenuEntries() {
    return this.plantCopperMenuEntries;
  }

  public Map<String, MenuEntry> getPlantDarksteelMenuEntries() {
    return this.plantDarksteelMenuEntries;
  }

  public Map<String, MenuEntry> getPlantIronMenuEntries() {
    return this.plantIronMenuEntries;
  }

  public Map<String, MenuEntry> getPlantMithrilMenuEntries() {
    return this.plantMithrilMenuEntries;
  }

  public Map<String, MenuEntry> getPlantOriMenuEntries() {
    return this.plantOriMenuEntries;
  }

  public Map<String, MenuEntry> getPlantSteelMenuEntries() {
    return this.plantSteelMenuEntries;
  }

  public List<Waypoint> getWaypoints() {
    List<Waypoint> waypointsToShow = new ArrayList<>();
    long oreFilter = this.filterModel.getOreFilter();
    long woodFilter = this.filterModel.getWoodFilter();
    long plantFilter = this.filterModel.getPlantFilter();
    long otherFilter = this.filterModel.getOtherFilter();

    if ((otherFilter & FilterModel.OTHER_WAYPOINT) != 0) {
      for (Waypoint waypoint : this.waypointWPs) {
        waypointsToShow.add(waypoint);
      }
    }
    if ((otherFilter & FilterModel.OTHER_POI) != 0) {
      for (Waypoint waypoint : this.poiWPs) {
        waypointsToShow.add(waypoint);
      }
    }
    if ((otherFilter & FilterModel.OTHER_UNLOCK) != 0) {
      for (Waypoint waypoint : this.unlockWPs) {
        waypointsToShow.add(waypoint);
      }
    }
    if ((otherFilter & FilterModel.OTHER_VISTA) != 0) {
      for (Waypoint waypoint : this.vistaWPs) {
        waypointsToShow.add(waypoint);
      }
    }
    if ((otherFilter & FilterModel.OTHER_SKILL_CHALLENGE) != 0) {
      for (Waypoint waypoint : this.skillWPs) {
        waypointsToShow.add(waypoint);
      }
    }
    if ((otherFilter & FilterModel.OTHER_HEART) != 0) {
      for (Waypoint waypoint : this.heartWPs) {
        waypointsToShow.add(waypoint);
      }
    }

    for (WPGW2Resource resWaypoint : this.resWPs) {
      WPSubType wpSubType = resWaypoint.getWPSubType();
      boolean addWP = false;
      addWP |= ((wpSubType == WPSubType.ORICHALCUM) && ((oreFilter & FilterModel.ORE_ORICHALCUM) != 0));
      addWP |= ((wpSubType == WPSubType.MITHRIL) && ((oreFilter & FilterModel.ORE_MITHRIL) != 0));
      addWP |= ((wpSubType == WPSubType.PLATINUM) && ((oreFilter & FilterModel.ORE_PLATINUM) != 0));
      addWP |= ((wpSubType == WPSubType.GOLD) && ((oreFilter & FilterModel.ORE_GOLD) != 0));
      addWP |= ((wpSubType == WPSubType.SILVER) && ((oreFilter & FilterModel.ORE_SILVER) != 0));
      addWP |= ((wpSubType == WPSubType.IRON) && ((oreFilter & FilterModel.ORE_IRON) != 0));
      addWP |= ((wpSubType == WPSubType.COPPER) && ((oreFilter & FilterModel.ORE_COPPER) != 0));

      addWP |= ((wpSubType == WPSubType.ORRIAN) && ((woodFilter & FilterModel.WOOD_ORRIAN) != 0));
      addWP |= ((wpSubType == WPSubType.ANCIENT) && ((woodFilter & FilterModel.WOOD_ANCIENT) != 0));
      addWP |= ((wpSubType == WPSubType.REDOAK) && ((woodFilter & FilterModel.WOOD_REDOAK) != 0));
      addWP |= ((wpSubType == WPSubType.CYPRESS) && ((woodFilter & FilterModel.WOOD_CYPRESS) != 0));
      addWP |= ((wpSubType == WPSubType.BAOBA) && ((woodFilter & FilterModel.WOOD_BAOBA) != 0));
      addWP |= ((wpSubType == WPSubType.BANYAN) && ((woodFilter & FilterModel.WOOD_BANYAN) != 0));
      addWP |= ((wpSubType == WPSubType.PINE) && ((woodFilter & FilterModel.WOOD_PINE) != 0));
      addWP |= ((wpSubType == WPSubType.INGLEWOOD) && ((woodFilter & FilterModel.WOOD_INGLEWOOD) != 0));
      addWP |= ((wpSubType == WPSubType.TUKAWA) && ((woodFilter & FilterModel.WOOD_TUKAWA) != 0));
      addWP |= ((wpSubType == WPSubType.FIR) && ((woodFilter & FilterModel.WOOD_FIR) != 0));
      addWP |= ((wpSubType == WPSubType.GUMMO) && ((woodFilter & FilterModel.WOOD_GUMMO) != 0));
      addWP |= ((wpSubType == WPSubType.MIMOSA) && ((woodFilter & FilterModel.WOOD_MIMOSA) != 0));
      addWP |= ((wpSubType == WPSubType.SNOWCHERRY) && ((woodFilter & FilterModel.WOOD_SNOWCHERRY) != 0));
      addWP |= ((wpSubType == WPSubType.ASPEN) && ((woodFilter & FilterModel.WOOD_ASPEN) != 0));
      addWP |= ((wpSubType == WPSubType.KERTCH) && ((woodFilter & FilterModel.WOOD_KERTCH) != 0));
      addWP |= ((wpSubType == WPSubType.EKKU) && ((woodFilter & FilterModel.WOOD_EKKU) != 0));

      addWP |= ((wpSubType == WPSubType.BLACK_CROCUS) && ((plantFilter & FilterModel.PLANT_BLACK_CROCUS) != 0));
      addWP |= ((wpSubType == WPSubType.GHOST_PEPPER) && ((plantFilter & FilterModel.PLANT_GHOST_PEPPER) != 0));
      addWP |= ((wpSubType == WPSubType.LOTUS) && ((plantFilter & FilterModel.PLANT_LOTUS) != 0));
      addWP |= ((wpSubType == WPSubType.OMNOMBERRIES) && ((plantFilter & FilterModel.PLANT_OMNOMBERRIES) != 0));
      addWP |= ((wpSubType == WPSubType.ORRIAN_TRUFFLE) && ((plantFilter & FilterModel.PLANT_ORRIAN_TRUFFLE) != 0));
      addWP |= ((wpSubType == WPSubType.SEAWEED) && ((plantFilter & FilterModel.PLANT_SEAWEED) != 0));
      addWP |= ((wpSubType == WPSubType.SNOW_TRUFFLE) && ((plantFilter & FilterModel.PLANT_SNOW_TRUFFLE) != 0));

      addWP |= ((wpSubType == WPSubType.ARTICHOKE) && ((plantFilter & FilterModel.PLANT_ARTICHOKE) != 0));
      addWP |= ((wpSubType == WPSubType.ASPARAGUS) && ((plantFilter & FilterModel.PLANT_ASPARAGUS) != 0));
      addWP |= ((wpSubType == WPSubType.BLOOMING_PASSIFLORA)
          && ((plantFilter & FilterModel.PLANT_BLOOMING_PASSIFLORA) != 0));
      addWP |= ((wpSubType == WPSubType.BUTTERNUT_SQUASH) && ((plantFilter & FilterModel.PLANT_BUTTERNUT_SQUASH) != 0));
      addWP |= ((wpSubType == WPSubType.CAYENNE_PEPPER) && ((plantFilter & FilterModel.PLANT_CAYENNE_PEPPER) != 0));
      addWP |= ((wpSubType == WPSubType.HERB_PATCH) && ((plantFilter & FilterModel.PLANT_HERB_PATCH) != 0));
      addWP |= ((wpSubType == WPSubType.LEEKS) && ((plantFilter & FilterModel.PLANT_LEEKS) != 0));
      addWP |= ((wpSubType == WPSubType.PASSIFLORA) && ((plantFilter & FilterModel.PLANT_PASSIFLORA) != 0));
      addWP |= ((wpSubType == WPSubType.RASPBERRIES) && ((plantFilter & FilterModel.PLANT_RASPBERRIES) != 0));
      addWP |= ((wpSubType == WPSubType.VERDANT_HERBS) && ((plantFilter & FilterModel.PLANT_VERDANT_HERBS) != 0));
      addWP |= ((wpSubType == WPSubType.WINTER_ROOT_VEGETABLES)
          && ((plantFilter & FilterModel.PLANT_WINTER_ROOT_VEGETABLES) != 0));

      addWP |= ((wpSubType == WPSubType.BLACKBERRIES) && ((plantFilter & FilterModel.PLANT_BLACKBERRIES) != 0));
      addWP |= ((wpSubType == WPSubType.CAULIFLOWER) && ((plantFilter & FilterModel.PLANT_CAULIFLOWER) != 0));
      addWP |= ((wpSubType == WPSubType.CORAL) && ((plantFilter & FilterModel.PLANT_CORAL) != 0));
      addWP |= ((wpSubType == WPSubType.MATURE_HERBS) && ((plantFilter & FilterModel.PLANT_MATURE_HERBS) != 0));
      addWP |= ((wpSubType == WPSubType.PORTOBELLO_MUSHROOMS)
          && ((plantFilter & FilterModel.PLANT_PORTOBELLO_MUSHROOMS) != 0));
      addWP |= ((wpSubType == WPSubType.SCALLIONS) && ((plantFilter & FilterModel.PLANT_SCALLIONS) != 0));
      addWP |= ((wpSubType == WPSubType.SUGAR_PUMPKIN) && ((plantFilter & FilterModel.PLANT_SUGAR_PUMPKIN) != 0));
      addWP |= ((wpSubType == WPSubType.VARIEGATED_TAPROOTS)
          && ((plantFilter & FilterModel.PLANT_VARIEGATED_TAPROOTS) != 0));

      addWP |= ((wpSubType == WPSubType.CABBAGE) && ((plantFilter & FilterModel.PLANT_CABBAGE) != 0));
      addWP |= ((wpSubType == WPSubType.GRAPES) && ((plantFilter & FilterModel.PLANT_GRAPES) != 0));
      addWP |= ((wpSubType == WPSubType.KALE) && ((plantFilter & FilterModel.PLANT_KALE) != 0));
      addWP |= ((wpSubType == WPSubType.ROOT_VEGETABLES) && ((plantFilter & FilterModel.PLANT_ROOT_VEGETABLES) != 0));
      addWP |= ((wpSubType == WPSubType.VARIED_MUSHROOMS) && ((plantFilter & FilterModel.PLANT_VARIED_MUSHROOMS) != 0));
      addWP |= ((wpSubType == WPSubType.YOUNG_HERBS) && ((plantFilter & FilterModel.PLANT_YOUNG_HERBS) != 0));
      addWP |= ((wpSubType == WPSubType.ZUCCHINI) && ((plantFilter & FilterModel.PLANT_ZUCCHINI) != 0));
      addWP |= ((wpSubType == WPSubType.VARIEGATED_TAPROOTS)
          && ((plantFilter & FilterModel.PLANT_VARIEGATED_TAPROOTS) != 0));

      addWP |= ((wpSubType == WPSubType.CLAM) && ((plantFilter & FilterModel.PLANT_CLAM) != 0));
      addWP |= ((wpSubType == WPSubType.HERB_SPROUTS) && ((plantFilter & FilterModel.PLANT_HERB_SPROUTS) != 0));
      addWP |= ((wpSubType == WPSubType.SPINACH) && ((plantFilter & FilterModel.PLANT_SPINACH) != 0));
      addWP |= ((wpSubType == WPSubType.STRAWBERRY_PATCH) && ((plantFilter & FilterModel.PLANT_STRAWBERRY_PATCH) != 0));
      addWP |= ((wpSubType == WPSubType.TAPROOTS) && ((plantFilter & FilterModel.PLANT_TAPROOTS) != 0));

      addWP |= ((wpSubType == WPSubType.BLUEBERRY_BUSH) && ((plantFilter & FilterModel.PLANT_BLUEBERRY_BUSH) != 0));
      addWP |= ((wpSubType == WPSubType.BUTTON_MUSHROOMS) && ((plantFilter & FilterModel.PLANT_BUTTON_MUSHROOMS) != 0));
      addWP |= ((wpSubType == WPSubType.CARROTS) && ((plantFilter & FilterModel.PLANT_CARROTS) != 0));
      addWP |= ((wpSubType == WPSubType.HERB_PATCH_LOW) && ((plantFilter & FilterModel.PLANT_HERB_PATCH_LOW) != 0));
      addWP |= ((wpSubType == WPSubType.HERB_SEEDLINGS) && ((plantFilter & FilterModel.PLANT_HERB_SEEDLINGS) != 0));
      addWP |= ((wpSubType == WPSubType.LETTUCE) && ((plantFilter & FilterModel.PLANT_LETTUCE) != 0));
      addWP |= ((wpSubType == WPSubType.ONIONS) && ((plantFilter & FilterModel.PLANT_ONIONS) != 0));
      addWP |= ((wpSubType == WPSubType.POTATO) && ((plantFilter & FilterModel.PLANT_POTATO) != 0));

      if (addWP) {
        waypointsToShow.add(resWaypoint);
      }

      for (WPGW2Other otherWaypoint : this.otherWPs) {
        if ((otherWaypoint.getWPSubType() == WPSubType.CHEST) && ((otherFilter & FilterModel.OTHER_CHEST) != 0)) {
          waypointsToShow.add(otherWaypoint);
        }
        if ((otherWaypoint.getWPSubType() == WPSubType.GUILD_BOUNTY)
            && ((otherFilter & FilterModel.OTHER_GUILD_BOUNTY) != 0)) {
          waypointsToShow.add(otherWaypoint);
        }
      }
    }
    if ((otherFilter & FilterModel.OTHER_MAP_NAME) != 0) {
      for (Waypoint waypoint : this.mapInfoWPs) {
        waypointsToShow.add(waypoint);
      }
    }

    return waypointsToShow;
  }

  public Map<String, MenuEntry> getWoodMenuEntries() {
    return this.woodMenuEntries;
  }

  @Override
  public void initialize() throws ServiceException {
    this.mapActionMode = MapActionMode.DEFAULT;
    this.mapService = JNDIServiceFactory.getInstance().getRemoteMapService();

    reloadMapData();
    initMenuEntries();

    if (this.updaterThread == null) {
      this.updaterThread = new Thread(() -> {
        try {
          while (true) {
            Thread.sleep(PreferencesModel.getInstance().getUpdateInterval());
            if (GW2MapModel.this.runUpdater) {
              reloadMapData();
            }
          }
        } catch (ServiceException | InterruptedException e) {
          LOG.error(e.getMessage(), e);
          MessageDialogFactory.handleExcpetion(Main.getInstance().getMainFrame(), e, null);
        }
      });
      this.updaterThread.start();
    }
  }

  public boolean isActiveWaypointAvailable() {
    return this.activeWaypoint != null;
  }

  public void prepareWaypoint(String command) {
    LOG.debug("command: {}", command);
    this.command = command;
    this.mapActionMode = MapActionMode.SET_WAYPOINT;

    if (!isChanging()) {
      syncViews(MAP_ACTION_MODE_CHANGED);
    }
  }

  public void resetWaypointMode() {
    this.command = "";
    this.mapActionMode = MapActionMode.DEFAULT;

    if (!isChanging()) {
      syncViews(MAP_ACTION_MODE_CHANGED);
    }
  }

  public void setActiveWaypoint(AbstractGW2Waypoint activeWaypoint) {
    LOG.debug("activeWaypoint: {}", activeWaypoint);
    this.activeWaypoint = activeWaypoint;
  }

  public void setActiveWaypointAsPermanent() throws ServiceException {
    LOG.debug("activeWaypoint: {}", this.activeWaypoint);
    if (this.activeWaypoint == null) {
      return;
    }

    this.mapService.markAsPermanent(this.authModel.getAuthDTO(), this.activeWaypoint.getId());
    if (this.activeWaypoint instanceof WPGW2Resource) {
      ((WPGW2Resource) this.activeWaypoint).setPermanent(true);
    }

    long syncFlag = 0;
    syncFlag |= WAYPOINTS_CHANGED;

    if ((syncFlag != 0) && !isChanging()) {
      syncViews(syncFlag);
    }
  }

  public void setActiveWaypointAsRich() throws ServiceException {
    LOG.debug("activeWaypoint: {}", this.activeWaypoint);
    if (this.activeWaypoint == null) {
      return;
    }

    this.mapService.markAsRich(this.authModel.getAuthDTO(), this.activeWaypoint.getId());
    if (this.activeWaypoint instanceof WPGW2Resource) {
      ((WPGW2Resource) this.activeWaypoint).setRich(true);
    }

    long syncFlag = 0;
    syncFlag |= WAYPOINTS_CHANGED;

    if ((syncFlag != 0) && !isChanging()) {
      syncViews(syncFlag);
    }
  }

  public void setRunUpdater(boolean runUpdater) {
    this.runUpdater = runUpdater;
  }

  public void setStaticWaypoints(List<WPGW2Waypoint> waypointWPs, List<WPGW2PointOfInterest> poiWPs,
      List<WPGW2Unlock> unlockWPs, List<WPGW2Vista> vistaWPs, List<WPGW2Skill> skillWPs,
      List<WPGW2Heart> heartWPs, List<WPGW2Map> mapInfos) {
    this.waypointWPs = waypointWPs;
    this.poiWPs = poiWPs;
    this.unlockWPs = unlockWPs;
    this.vistaWPs = vistaWPs;
    this.skillWPs = skillWPs;
    this.heartWPs = heartWPs;
    this.mapInfoWPs = mapInfos;

    if (!isChanging()) {
      syncViews(WAYPOINTS_CHANGED);
    }
  }

  public void setWaypoint(GeoPosition geoPosition) throws ServiceException {
    LOG.debug("geoPosition: {}", geoPosition);
    WPType wpType = null;
    WPSubType wpSubType = null;
    MenuEntry entry = null;
    if (this.command.startsWith(AC_ORE_PREFIX)) {
      entry = this.oreMenuEntries.get(this.command);
    }
    if (this.command.startsWith(AC_WOOD_PREFIX)) {
      entry = this.woodMenuEntries.get(this.command);
    }
    if (this.command.startsWith(AC_PLANT_PREFIX)) {
      entry = this.plantOriMenuEntries.get(this.command);
      if (entry == null) {
        entry = this.plantMithrilMenuEntries.get(this.command);
      }
      if (entry == null) {
        entry = this.plantDarksteelMenuEntries.get(this.command);
      }
      if (entry == null) {
        entry = this.plantSteelMenuEntries.get(this.command);
      }
      if (entry == null) {
        entry = this.plantIronMenuEntries.get(this.command);
      }
      if (entry == null) {
        entry = this.plantCopperMenuEntries.get(this.command);
      }
    }
    if (this.command.startsWith(AC_OTHER_PREFIX)) {
      entry = this.otherMenuEntries.get(this.command);
    }
    wpType = entry.wpType;
    wpSubType = entry.wpSubType;

    long syncFlag = 0;
    WaypointDTO newWaypointDTO = new WaypointDTO(0, null, "", wpType, wpSubType, geoPosition.getLongitude(),
        geoPosition.getLatitude(), "", false, false);
    WaypointDTO savedWaypointDTO = this.mapService.createNewWaypoint(this.authModel.getAuthDTO(), newWaypointDTO);

    if ((savedWaypointDTO.getWpType() == WPType.ORE)) {
      WPGW2Resource newWaypoint = buildWPGW2Resource(savedWaypointDTO);
      this.resWPs.add(newWaypoint);
      syncFlag |= WAYPOINTS_CHANGED;

      this.filterModel.addOreFilter(newWaypoint.getWPSubType());
      syncFlag |= FILTER_CHANGED;
    } else if (savedWaypointDTO.getWpType() == WPType.WOOD) {
      WPGW2Resource newWaypoint = buildWPGW2Resource(savedWaypointDTO);
      this.resWPs.add(newWaypoint);
      syncFlag |= WAYPOINTS_CHANGED;

      this.filterModel.addWoodFilter(newWaypoint.getWPSubType());
      syncFlag |= FILTER_CHANGED;
    } else if (savedWaypointDTO.getWpType() == WPType.PLANT) {
      WPGW2Resource newWaypoint = buildWPGW2Resource(savedWaypointDTO);
      this.resWPs.add(newWaypoint);
      syncFlag |= WAYPOINTS_CHANGED;

      this.filterModel.addPlantFilter(newWaypoint.getWPSubType());
      syncFlag |= FILTER_CHANGED;
    } else if (savedWaypointDTO.getWpType() == WPType.OTHER) {
      WPGW2Other newWaypoint = buildWPGW2Other(savedWaypointDTO);
      this.otherWPs.add(newWaypoint);
      syncFlag |= WAYPOINTS_CHANGED;

      this.filterModel.addOtherFilter(newWaypoint.getWPSubType());
      syncFlag |= FILTER_CHANGED;
    }

    syncFlag |= MAP_ACTION_MODE_CHANGED;

    // Daten haben sich geändert, daher müssen die MaoInfos auch neu geladen werden bei der nächsten Anzeige
    this.lastMapInfoName = null;

    if ((syncFlag != 0) && !isChanging()) {
      syncViews(syncFlag);
    }
  }

  private WPGW2Other buildWPGW2Other(WaypointDTO dto) {
    GeoPosition geoPos = new GeoPosition(dto.getLatitude(), dto.getLongitude());
    return new WPGW2Other(dto.getId(), geoPos, dto.getWpType(), dto.getWpSubType(), dto.getUpdateDate(),
        dto.getUpdateUsername());
  }

  private WPGW2Resource buildWPGW2Resource(WaypointDTO dto) {
    GeoPosition geoPos = new GeoPosition(dto.getLatitude(), dto.getLongitude());
    return new WPGW2Resource(dto.getId(), geoPos, dto.getWpType(), dto.getWpSubType(), dto.getUpdateDate(),
        dto.getUpdateUsername(), dto.isRich(), dto.isPermanent());
  }

  private void initMenuEntries() {
    MenuEntry entry;
    String ac;
    ImageIcon icon;
    // Erze
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.ORICHALCUM, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_ORE_PREFIX + " orichalcumVein";
    entry = new MenuEntry("orichalcumVein", icon, WPType.ORE, WPSubType.ORICHALCUM, ac, 1);
    this.oreMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.MITHRIL, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_ORE_PREFIX + " mithrilVein";
    entry = new MenuEntry("mithrilVein", icon, WPType.ORE, WPSubType.MITHRIL, ac, 2);
    this.oreMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.PLATINUM, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_ORE_PREFIX + " platinumVein";
    entry = new MenuEntry("platinumVein", icon, WPType.ORE, WPSubType.PLATINUM, ac, 3);
    this.oreMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.GOLD, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_ORE_PREFIX + " goldVein";
    entry = new MenuEntry("goldVein", icon, WPType.ORE, WPSubType.GOLD, ac, 4);
    this.oreMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.SILVER, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_ORE_PREFIX + " silverVein";
    entry = new MenuEntry("silverVein", icon, WPType.ORE, WPSubType.SILVER, ac, 5);
    this.oreMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.IRON, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_ORE_PREFIX + " ironVein";
    entry = new MenuEntry("ironVein", icon, WPType.ORE, WPSubType.IRON, ac, 6);
    this.oreMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.COPPER, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_ORE_PREFIX + " copperVein";
    entry = new MenuEntry("copperVein", icon, WPType.ORE, WPSubType.COPPER, ac, 7);
    this.oreMenuEntries.put(ac, entry);

    // Hölzer
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.ORRIAN, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " orrian";
    entry = new MenuEntry("orrian", icon, WPType.WOOD, WPSubType.ORRIAN, ac, 1);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.ANCIENT, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " ancient";
    entry = new MenuEntry("ancient", icon, WPType.WOOD, WPSubType.ANCIENT, ac, 2);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.BAOBA, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " baoba";
    entry = new MenuEntry("baoba", icon, WPType.WOOD, WPSubType.BAOBA, ac, 3);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.REDOAK, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " redOak";
    entry = new MenuEntry("redOak", icon, WPType.WOOD, WPSubType.REDOAK, ac, 4);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.CYPRESS, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " cypress";
    entry = new MenuEntry("cypress", icon, WPType.WOOD, WPSubType.CYPRESS, ac, 5);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.BANYAN, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " banyan";
    entry = new MenuEntry("banyan", icon, WPType.WOOD, WPSubType.BANYAN, ac, 6);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.INGLEWOOD, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " inglewood";
    entry = new MenuEntry("inglewood", icon, WPType.WOOD, WPSubType.INGLEWOOD, ac, 7);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.PINE, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " pine";
    entry = new MenuEntry("pine", icon, WPType.WOOD, WPSubType.PINE, ac, 8);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.FIR, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " fir";
    entry = new MenuEntry("fir", icon, WPType.WOOD, WPSubType.FIR, ac, 9);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.TUKAWA, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " tukawa";
    entry = new MenuEntry("tukawa", icon, WPType.WOOD, WPSubType.TUKAWA, ac, 10);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.SNOWCHERRY, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " snowCherry";
    entry = new MenuEntry("snowCherry", icon, WPType.WOOD, WPSubType.SNOWCHERRY, ac, 11);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.MIMOSA, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " mimosa";
    entry = new MenuEntry("mimosa", icon, WPType.WOOD, WPSubType.MIMOSA, ac, 12);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.GUMMO, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " gummo";
    entry = new MenuEntry("gummo", icon, WPType.WOOD, WPSubType.GUMMO, ac, 13);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.ASPEN, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " aspen";
    entry = new MenuEntry("aspen", icon, WPType.WOOD, WPSubType.ASPEN, ac, 14);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.EKKU, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " ekku";
    entry = new MenuEntry("ekku", icon, WPType.WOOD, WPSubType.EKKU, ac, 15);
    this.woodMenuEntries.put(ac, entry);
    icon = WPGW2Resource.getHoverInfoBySubType(WPSubType.KERTCH, ICON_SIZE, ICON_SIZE).resourceIcon.get(0);
    ac = AC_WOOD_PREFIX + " kertch";
    entry = new MenuEntry("kertch", icon, WPType.WOOD, WPSubType.KERTCH, ac, 16);
    this.woodMenuEntries.put(ac, entry);

    // Planzen
    int order = 1;
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.PLANT_SAFFRON_THREAD_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_PLANT_PREFIX + " blackCrocus";
    entry = new MenuEntry("blackCrocus", icon, WPType.PLANT, WPSubType.BLACK_CROCUS, ac, order++);
    this.plantOriMenuEntries.put(ac, entry);
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.PLANT_GHOST_PEPPER_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_PLANT_PREFIX + " ghostPepper";
    entry = new MenuEntry("ghostPepper", icon, WPType.PLANT, WPSubType.GHOST_PEPPER, ac, order++);
    this.plantOriMenuEntries.put(ac, entry);
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.PLANT_LOTUS_ROOT_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_PLANT_PREFIX + " lotus";
    entry = new MenuEntry("lotus", icon, WPType.PLANT, WPSubType.LOTUS, ac, order++);
    this.plantOriMenuEntries.put(ac, entry);
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.PLANT_OMNOMBERRY_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_PLANT_PREFIX + " omnomberries";
    entry = new MenuEntry("omnomberries", icon, WPType.PLANT, WPSubType.OMNOMBERRIES, ac, order++);
    this.plantOriMenuEntries.put(ac, entry);
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.PLANT_ORRIAN_TRUFFLE_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_PLANT_PREFIX + " orrianTruffle";
    entry = new MenuEntry("orrianTruffle", icon, WPType.PLANT, WPSubType.ORRIAN_TRUFFLE, ac, order++);
    this.plantOriMenuEntries.put(ac, entry);
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.PLANT_SEAWEED_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_PLANT_PREFIX + " seaweed";
    entry = new MenuEntry("seaweed", icon, WPType.PLANT, WPSubType.SEAWEED, ac, order++);
    this.plantOriMenuEntries.put(ac, entry);
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.PLANT_SNOW_TRUFFLE_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_PLANT_PREFIX + " snowTruffle";
    entry = new MenuEntry("snowTruffle", icon, WPType.PLANT, WPSubType.SNOW_TRUFFLE, ac, order++);
    this.plantOriMenuEntries.put(ac, entry);

    order = 1;
    ac = AC_PLANT_PREFIX + " artichoke";
    entry = new MenuEntry("artichoke", null, WPType.PLANT, WPSubType.ARTICHOKE, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " asparagus";
    entry = new MenuEntry("asparagus", null, WPType.PLANT, WPSubType.ASPARAGUS, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " bloomingPassiflora";
    entry = new MenuEntry("bloomingPassiflora", null, WPType.PLANT, WPSubType.BLOOMING_PASSIFLORA, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " butternutSquash";
    entry = new MenuEntry("butternutSquash", null, WPType.PLANT, WPSubType.BUTTERNUT_SQUASH, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " cayennePepper";
    entry = new MenuEntry("cayennePepper", null, WPType.PLANT, WPSubType.CAYENNE_PEPPER, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " herbPatch";
    entry = new MenuEntry("herbPatch", null, WPType.PLANT, WPSubType.HERB_PATCH, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " leaks";
    entry = new MenuEntry("leaks", null, WPType.PLANT, WPSubType.LEEKS, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " passiflora";
    entry = new MenuEntry("passiflora", null, WPType.PLANT, WPSubType.PASSIFLORA, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " raspberries";
    entry = new MenuEntry("raspberries", null, WPType.PLANT, WPSubType.RASPBERRIES, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " verdantHerbs";
    entry = new MenuEntry("verdantHerbs", null, WPType.PLANT, WPSubType.VERDANT_HERBS, ac, order++);
    this.plantMithrilMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " winterRootVegetables";
    entry = new MenuEntry("winterRootVegetables", null, WPType.PLANT, WPSubType.WINTER_ROOT_VEGETABLES, ac, order);
    this.plantMithrilMenuEntries.put(ac, entry);

    order = 1;
    ac = AC_PLANT_PREFIX + " blackberries";
    entry = new MenuEntry("blackberries", null, WPType.PLANT, WPSubType.BLACKBERRIES, ac, order++);
    this.plantDarksteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " cauliflower";
    entry = new MenuEntry("cauliflower", null, WPType.PLANT, WPSubType.CAULIFLOWER, ac, order++);
    this.plantDarksteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " coral";
    entry = new MenuEntry("coral", null, WPType.PLANT, WPSubType.CORAL, ac, order++);
    this.plantDarksteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " matureHerbs";
    entry = new MenuEntry("matureHerbs", null, WPType.PLANT, WPSubType.MATURE_HERBS, ac, order++);
    this.plantDarksteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " portobelloMushrooms";
    entry = new MenuEntry("portobelloMushrooms", null, WPType.PLANT, WPSubType.PORTOBELLO_MUSHROOMS, ac, order++);
    this.plantDarksteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " scallions";
    entry = new MenuEntry("scallions", null, WPType.PLANT, WPSubType.SCALLIONS, ac, order++);
    this.plantDarksteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " sugarPumpkin";
    entry = new MenuEntry("sugarPumpkin", null, WPType.PLANT, WPSubType.SUGAR_PUMPKIN, ac, order++);
    this.plantDarksteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " variegatedTaproots";
    entry = new MenuEntry("variegatedTaproots", null, WPType.PLANT, WPSubType.VARIEGATED_TAPROOTS, ac, order);
    this.plantDarksteelMenuEntries.put(ac, entry);

    order = 1;
    ac = AC_PLANT_PREFIX + " cabbage";
    entry = new MenuEntry("cabbage", null, WPType.PLANT, WPSubType.CABBAGE, ac, order++);
    this.plantSteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " grapes";
    entry = new MenuEntry("grapes", null, WPType.PLANT, WPSubType.GRAPES, ac, order++);
    this.plantSteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " kale";
    entry = new MenuEntry("kale", null, WPType.PLANT, WPSubType.KALE, ac, order++);
    this.plantSteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " rootVegetables";
    entry = new MenuEntry("rootVegetables", null, WPType.PLANT, WPSubType.ROOT_VEGETABLES, ac, order++);
    this.plantSteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " variedMushrooms";
    entry = new MenuEntry("variedMushrooms", null, WPType.PLANT, WPSubType.VARIED_MUSHROOMS, ac, order++);
    this.plantSteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " youngHerbs";
    entry = new MenuEntry("youngHerbs", null, WPType.PLANT, WPSubType.YOUNG_HERBS, ac, order++);
    this.plantSteelMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " zucchini";
    entry = new MenuEntry("zucchini", null, WPType.PLANT, WPSubType.ZUCCHINI, ac, order);
    this.plantSteelMenuEntries.put(ac, entry);

    order = 1;
    ac = AC_PLANT_PREFIX + " clam";
    entry = new MenuEntry("clam", null, WPType.PLANT, WPSubType.CLAM, ac, order++);
    this.plantIronMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " herbSprouts";
    entry = new MenuEntry("herbSprouts", null, WPType.PLANT, WPSubType.HERB_SPROUTS, ac, order++);
    this.plantIronMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " spinach";
    entry = new MenuEntry("spinach", null, WPType.PLANT, WPSubType.SPINACH, ac, order++);
    this.plantIronMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " strawberryPatch";
    entry = new MenuEntry("strawberryPatch", null, WPType.PLANT, WPSubType.STRAWBERRY_PATCH, ac, order++);
    this.plantIronMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " taproots";
    entry = new MenuEntry("taproots", null, WPType.PLANT, WPSubType.TAPROOTS, ac, order);
    this.plantIronMenuEntries.put(ac, entry);

    order = 1;
    ac = AC_PLANT_PREFIX + " blueberryBush";
    entry = new MenuEntry("blueberryBush", null, WPType.PLANT, WPSubType.BLUEBERRY_BUSH, ac, order++);
    this.plantCopperMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " buttonMushrooms";
    entry = new MenuEntry("buttonMushrooms", null, WPType.PLANT, WPSubType.BUTTON_MUSHROOMS, ac, order++);
    this.plantCopperMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " herbPatchLowLevel";
    entry = new MenuEntry("herbPatchLowLevel", null, WPType.PLANT, WPSubType.HERB_PATCH_LOW, ac, order++);
    this.plantCopperMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " herbSeedlings";
    entry = new MenuEntry("herbSeedlings", null, WPType.PLANT, WPSubType.HERB_SEEDLINGS, ac, order++);
    this.plantCopperMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " lettuce";
    entry = new MenuEntry("lettuce", null, WPType.PLANT, WPSubType.LETTUCE, ac, order++);
    this.plantCopperMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " onions";
    entry = new MenuEntry("onions", null, WPType.PLANT, WPSubType.ONIONS, ac, order++);
    this.plantCopperMenuEntries.put(ac, entry);
    ac = AC_PLANT_PREFIX + " potato";
    entry = new MenuEntry("potato", null, WPType.PLANT, WPSubType.POTATO, ac, order);
    this.plantCopperMenuEntries.put(ac, entry);

    // Sonstige
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.GUILD_BOUNTY_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_OTHER_PREFIX + " guildBounty";
    entry = new MenuEntry("guildBounty", icon, WPType.OTHER, WPSubType.GUILD_BOUNTY, ac, 1);
    this.otherMenuEntries.put(ac, entry);
    icon = IMAGE_MANAGER.getImageIcon(ImageConstants.CHEST_ICON, ICON_SIZE, ICON_SIZE);
    ac = AC_OTHER_PREFIX + " chest";
    entry = new MenuEntry("chest", icon, WPType.OTHER, WPSubType.CHEST, ac, 1);
    this.otherMenuEntries.put(ac, entry);
  }

  private void reloadMapData() throws ServiceException {
    WaypointListWrapperDTO waypointWrapper = this.mapService.getAllAvailableWaypoints(this.authModel.getAuthDTO());
    List<WaypointDTO> resourceWPs = waypointWrapper.getResourceWPs();
    this.resWPs = new CopyOnWriteArrayList<>();
    for (WaypointDTO wp : resourceWPs) {
      this.resWPs.add(buildWPGW2Resource(wp));
    }
    List<WaypointDTO> otherWpDtos = waypointWrapper.getOtherWPs();
    this.otherWPs = new CopyOnWriteArrayList<>();
    for (WaypointDTO wp : otherWpDtos) {
      this.otherWPs.add(buildWPGW2Other(wp));
    }
    if (!isChanging()) {
      syncViews(WAYPOINTS_CHANGED);
    }
    if (LOG.isInfoEnabled()) {
      LOG.info("Wegpunkte vom Server geladen");
    }
  }
}
