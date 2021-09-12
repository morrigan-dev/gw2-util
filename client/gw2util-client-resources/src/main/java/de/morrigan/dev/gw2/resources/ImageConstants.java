package de.morrigan.dev.gw2.resources;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dieser Manager bietet Zugriff auf alle Bilder, die auf dem Client verfügbar sind.
 *
 * @author morrigan
 */
public final class ImageConstants {

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(ImageConstants.class);

  /** Einzige Instanz dieses Managers */
  private static final ImageConstants INSTANCE = new ImageConstants();

  public static final String EMPTY_ICON = "emptyIcon";
  public static final String BLUE_BALL_ICON = "blueBallIcon";
  public static final String ARROWS_UP_ICON = "arrowsUpIcon";
  public static final String ARROWS_DOWN_ICON = "arrowsDownIcon";
  public static final String ARROW_LEFT_ICON = "arrowLeftIcon";
  public static final String ARROWS_LEFT_ICON = "arrowsLeftIcon";
  public static final String ARROW_RIGHT_ICON = "arrowRightIcon";
  public static final String ARROWS_RIGHT_ICON = "arrowsRightIcon";
  public static final String ERROR_ICON = "errorIcon";
  public static final String CONFIRMATION_ICON = "confirmationIcon";
  public static final String NOTIFICATION_ICON = "notificationIcon";
  public static final String UNCHANGED_ICON = "applyIcon";
  public static final String MODIFIED_ICON = "modifiedIcon";
  public static final String DELETED_ICON = "deletedIcon";
  public static final String DELETE_ICON = "deleteIcon";
  public static final String RICH_ICON = "richIcon";
  public static final String PERMANENT_ICON = "permanentIcon";
  public static final String FACEBOOK_ICON = "facebookIcon";
  public static final String GOOGLE_PLUS_ICON = "googlePlusIcon";
  public static final String TWITTER_ICON = "twitterIcon";
  public static final String BACKGROUND_IMAGE = "backgroundImage";
  public static final String SELECTED_DRAGON_ICON = "selectedDragonIcon";
  public static final String SELECTED_MAP_ICON = "selectedMapIcon";
  public static final String SELECTED_INFO_ICON = "selectedInfoIcon";
  public static final String DRAGON_ICON = "dragonIcon";
  public static final String MAP_ICON = "mapIcon";
  public static final String INFO_ICON = "infoIcon";
  public static final String BAR_MAP_ICON = "barMapIcon";
  public static final String BAR_MAP_HOVER_ICON = "barMapHoverIcon";
  public static final String BAR_EXIT_ICON = "barExitIcon";
  public static final String BAR_EXIT_HOVER_ICON = "barExitHoverIcon";
  public static final String BAR_INFO_ICON = "barInfoIcon";
  public static final String BAR_INFO_HOVER_ICON = "barInfoHoverIcon";
  public static final String SELECTED_STATS_ICON = "selectedStatsIcon";
  public static final String STATS_ICON = "statsIcon";
  public static final String CLOSE_ICON = "closeIcon";
  public static final String CLOSE_HOVER_ICON = "closeHoverIcon";
  public static final String MIN_MAX_TO_TOP_ICON = "minMaxToTopIcon";
  public static final String MIN_MAX_TO_TOP_HOVER_ICON = "minMaxToTopHoverIcon";
  public static final String MIN_MAX_TO_SIDE_ICON = "minMaxToSideIcon";
  public static final String MIN_MAX_TO_SIDE_HOVER_ICON = "minMaxToSideHoverIcon";
  public static final String MENU_BAR_ICON = "menuBarIcon";
  public static final String VISTA_ICON = "vistaIcon";
  public static final String SKILL_CHALLEGENE_ICON = "skillChallengeIcon";
  public static final String HEART_ICON = "heartIcon";
  public static final String CHEST_ICON = "chestIcon";
  public static final String CHEST_PERMANENT_ICON = "chestPermanentIcon";
  public static final String COPPER_ICON = "copperIcon";
  public static final String GOLD_ICON = "goldIcon";
  public static final String IRON_ICON = "ironIcon";
  public static final String MITHRIL_ICON = "mithrilIcon";
  public static final String ORICHALCUM_ICON = "orichalcumIcon";
  public static final String PLATINUM_ICON = "platinumIcon";
  public static final String SILVER_ICON = "silverIcon";
  public static final String GREEN_WOOD_ICON = "greenWoodIcon";
  public static final String SOFT_WOOD_ICON = "softWoodIcon";
  public static final String SEASONED_WOOD_ICON = "seasonedWoodIcon";
  public static final String HARD_WOOD_ICON = "hardWoodIcon";
  public static final String ELDER_WOOD_ICON = "elderWoodIcon";
  public static final String ANCIENT_WOOD_ICON = "ancientWoodIcon";
  public static final String AXE_ICON = "axeIcon";
  public static final String PICK_ICON = "pickIcon";
  public static final String SICKLES_ICON = "sicklesWoodIcon";
  public static final String ORE_ICON = "oreIcon";
  public static final String ORE_RICH_ICON = "oreRichIcon";
  public static final String ORE_RICH_PERMANENT_ICON = "oreRichPermanentIcon";
  public static final String ORE_PERMANENT_ICON = "orePermanentIcon";
  public static final String WOOD_ICON = "woodIcon";
  public static final String WOOD_RICH_ICON = "woodRichIcon";
  public static final String WOOD_RICH_PERMANENT_ICON = "woodRichPermanentIcon";
  public static final String WOOD_PERMANENT_ICON = "woodPermanentIcon";
  public static final String PLANT_ICON = "plantIcon";
  public static final String PLANT_RICH_ICON = "plantRichIcon";
  public static final String PLANT_RICH_PERMANENT_ICON = "plantRichPermanentIcon";
  public static final String PLANT_PERMANENT_ICON = "plantPermanentIcon";
  public static final String PLANT_SAFFRON_THREAD_ICON = "plantSaffronThreadIcon";
  public static final String PLANT_GHOST_PEPPER_ICON = "plantGhostPepperIcon";
  public static final String PLANT_LOTUS_ROOT_ICON = "plantLotusRootIcon";
  public static final String PLANT_OMNOMBERRY_ICON = "plantOmnomberryIcon";
  public static final String PLANT_ORRIAN_TRUFFLE_ICON = "plantOrrianTruffleIcon";
  public static final String PLANT_SEAWEED_ICON = "plantSeaweedIcon";
  public static final String PLANT_SNOW_TRUFFLE_ICON = "plantSnowTruffleIcon";

  public static final String PLANT_ARTICHOKE_ICON = "plantArtichokeIcon";
  public static final String PLANT_ASPARAGUS_SPEAR_ICON = "plantAsparagusSpearIcon";
  public static final String PLANT_PASSION_FRUIT_ICON = "plantPassionFruitIcon";
  public static final String PLANT_PASSION_FLOWER_ICON = "plantPassionFlowerIcon";
  public static final String PLANT_BUTTERNUT_SQUASH_ICON = "plantButternutSquashIcon";
  public static final String PLANT_CAYENNE_PEPPER_ICON = "plantCayennePepperIcon";
  public static final String PLANT_HEAD_OF_GARLIC_ICON = "plantHeadofGarlicIcon";
  public static final String PLANT_LEMONGRASS_ICON = "plantLemongrassIcon";
  public static final String PLANT_CORIANDER_SEED_ICON = "plantCorianderSeedIcon";
  public static final String PLANT_TARRAGON_LEAVES_ICON = "plantTarragonLeavesIcon";
  public static final String PLANT_GREEN_ONION_ICON = "plantGreenOnionIcon";
  public static final String PLANT_LEEK_ICON = "plantLeekIcon";
  public static final String PLANT_RASPBERRY_ICON = "plantRaspberryIcon";
  public static final String PLANT_CLOVE_ICON = "plantCloveIcon";
  public static final String PLANT_MINT_LEAF_ICON = "plantMintLeafIcon";
  public static final String PLANT_SAGE_LEAF_ICON = "plantSageLeafIcon";
  public static final String PLANT_THYME_LEAF_ICON = "plantThymeLeafIcon";
  public static final String PLANT_VANILLA_BEAN_ICON = "plantVanillaBeanIcon";
  public static final String PLANT_BEET_ICON = "plantBeetIcon";
  public static final String PLANT_PARSNIP_ICON = "plantParsnipIcon";
  public static final String PLANT_RUTABAGA_ICON = "plantRutabagaIcon";
  public static final String PLANT_TURNIP_ICON = "plantTurnipIcon";
  public static final String PLANT_BLACKBERRY_ICON = "BlackberryIcon";
  public static final String PLANT_HEAD_OF_CAULIFLOWER_ICON = "HeadOfCauliflowerIcon";
  public static final String PLANT_CORAL_CHUNK_ICON = "CoralChunkIcon";
  public static final String PLANT_CORAL_ORB_ICON = "CoralOrbIcon";
  public static final String PLANT_CORAL_TENTACLE_ICON = "CoralTentacleIcon";
  public static final String PLANT_BAY_LEAF_ICON = "BayLeafIcon";
  public static final String PLANT_CHILI_PEPPER_ICON = "ChiliPepperIcon";
  public static final String PLANT_DILL_SPRIG_ICON = "DillSprigIcon";
  public static final String PLANT_ROSEMARY_SPRIG_ICON = "RosemarySprigIcon";
  public static final String PLANT_SESAME_SEED_ICON = "SesameSeedIcon";
  public static final String PLANT_PORTOBELLO_MUSHROOM_ICON = "PortobelloMushroomIcon";
  public static final String PLANT_ONION_ICON = "OnionIcon";
  public static final String PLANT_SUGAR_PUMPKIN_ICON = "SugarPumpkinIcon";
  public static final String PLANT_CARROT_ICON = "CarrotIcon";
  public static final String PLANT_HEAD_OF_CABBAGE_ICON = "HeadOfCabbageIcon";
  public static final String PLANT_GRAPE_ICON = "GrapeIcon";
  public static final String PLANT_KALE_LEAF_ICON = "KaleLeafIcon";
  public static final String PLANT_YAM_ICON = "YamIcon";
  public static final String PLANT_MUSHROOM_ICON = "MushroomIcon";
  public static final String PLANT_BLACK_PEPPERCORN_ICON = "BlackPeppercornIcon";
  public static final String PLANT_PARSLEY_LEAF_ICON = "ParsleyLeafIcon";
  public static final String PLANT_ZUCCHINI_ICON = "ZucchiniIcon";
  public static final String PLANT_CLAM_ICON = "ClamIcon";
  public static final String PLANT_PEARL_ICON = "PearlIcon";
  public static final String PLANT_OREGANO_LEAF_ICON = "OreganoLeafIcon";
  public static final String PLANT_SPINACH_LEAF_ICON = "SpinachLeafIcon";
  public static final String PLANT_STRAWBERRY_ICON = "StrawberryIcon";
  public static final String PLANT_BLUEBERRY_ICON = "BlueberryIcon";
  public static final String PLANT_HEAD_OF_LETTUCE_ICON = "HeadOfLettuceIcon";
  public static final String PLANT_POTATO_ICON = "PotatoIcon";

  public static final String GUILD_BOUNTY_ICON = "guildBountyIcon";

  private static final String PATH_IMAGES = "/images";
  private static final String PATH_EMPTY_ICON = PATH_IMAGES + "/empty.png";
  private static final String PATH_BLUE_BALL_ICON = PATH_IMAGES + "/blue-ball.png";
  private static final String PATH_ARROWS_UP_ICON = PATH_IMAGES + "/up_arrows.png";
  private static final String PATH_ARROWS_DOWN_ICON = PATH_IMAGES + "/down_arrows.png";
  private static final String PATH_ARROW_LEFT_ICON = PATH_IMAGES + "/left_arrow.png";
  private static final String PATH_ARROWS_LEFT_ICON = PATH_IMAGES + "/left_arrows.png";
  private static final String PATH_ARROW_RIGHT_ICON = PATH_IMAGES + "/right_arrow.png";
  private static final String PATH_ARROWS_RIGHT_ICON = PATH_IMAGES + "/right_arrows.png";
  private static final String PATH_ERROR_ICON = PATH_IMAGES + "/error_icon.png";
  private static final String PATH_CONFIRMATION_ICON = PATH_IMAGES + "/confirmation_icon.png";
  private static final String PATH_NOTIFICATION_ICON = PATH_IMAGES + "/notify_icon.png";
  private static final String PATH_UNCHANGED_ICON = PATH_IMAGES + "/apply.png";
  private static final String PATH_MODIFIED_ICON = PATH_IMAGES + "/reload.png";
  private static final String PATH_DELETED_ICON = PATH_IMAGES + "/cancel.png";
  private static final String PATH_DELETE_ICON = PATH_IMAGES + "/erase.png";
  private static final String PATH_RICH_ICON = PATH_IMAGES + "/rich.png";
  private static final String PATH_PERMANENT_ICON = PATH_IMAGES + "/permanent.png";
  private static final String PATH_ORE_ICON = PATH_IMAGES + "/ore.png";
  private static final String PATH_ORE_RICH_ICON = PATH_IMAGES + "/ore-rich.png";
  private static final String PATH_ORE_RICH_PERMANENT_ICON = PATH_IMAGES + "/ore-rich-permanent.png";
  private static final String PATH_ORE_PERMANENT_ICON = PATH_IMAGES + "/ore-permanent.png";
  private static final String PATH_WOOD_ICON = PATH_IMAGES + "/wood.png";
  private static final String PATH_WOOD_RICH_ICON = PATH_IMAGES + "/wood-rich.png";
  private static final String PATH_WOOD_RICH_PERMANENT_ICON = PATH_IMAGES + "/wood-rich-permanent.png";
  private static final String PATH_WOOD_PERMANENT_ICON = PATH_IMAGES + "/wood-permanent.png";
  private static final String PATH_PLANT_ICON = PATH_IMAGES + "/plant.png";
  private static final String PATH_PLANT_RICH_ICON = PATH_IMAGES + "/plant-rich.png";
  private static final String PATH_PLANT_RICH_PERMANENT_ICON = PATH_IMAGES + "/plant-rich-permanent.png";
  private static final String PATH_PLANT_PERMANENT_ICON = PATH_IMAGES + "/plant-permanent.png";
  private static final String PATH_FACEBOOK_ICON = PATH_IMAGES + "/facebook-icon.png";
  private static final String PATH_GOOGLE_PLUS_ICON = PATH_IMAGES + "/google-plus-icon.png";
  private static final String PATH_TWITTER_ICON = PATH_IMAGES + "/twitter-icon.png";
  private static final String PATH_BACKGROUND_IMAGE = PATH_IMAGES + "/Handelsposten.png";
  private static final String PATH_SELECTED_DRAGON_ICON = PATH_IMAGES + "/drache_selected.png";
  private static final String PATH_DRAGON_ICON = PATH_IMAGES + "/drache.png";
  private static final String PATH_SELECTED_MAP_ICON = PATH_IMAGES + "/map_selected.png";
  private static final String PATH_SELECTED_INFO_ICON = PATH_IMAGES + "/info_selected.png";
  private static final String PATH_MAP_ICON = PATH_IMAGES + "/map.png";
  private static final String PATH_INFO_ICON = PATH_IMAGES + "/info.png";
  private static final String PATH_BAR_MAP_ICON = PATH_IMAGES + "/bar_map_icon.png";
  private static final String PATH_BAR_MAP_HOVER_ICON = PATH_IMAGES + "/bar_map_hover_icon.png";
  private static final String PATH_BAR_EXIT_ICON = PATH_IMAGES + "/bar_exit_icon.png";
  private static final String PATH_BAR_EXIT_HOVER_ICON = PATH_IMAGES + "/bar_exit_hover_icon.png";
  private static final String PATH_BAR_INFO_ICON = PATH_IMAGES + "/bar_info_icon.png";
  private static final String PATH_BAR_INFO_HOVER_ICON = PATH_IMAGES + "/bar_info_hover_icon.png";
  private static final String PATH_SELECTED_STATS_ICON = PATH_IMAGES + "/stats_selected.png";
  private static final String PATH_STATS_ICON = PATH_IMAGES + "/stats.png";
  private static final String PATH_CLOSE_ICON = PATH_IMAGES + "/close-button.png";
  private static final String PATH_CLOSE_HOVER_ICON = PATH_IMAGES + "/close-button-hover.png";
  private static final String PATH_MIN_MAX_TO_TOP_ICON = PATH_IMAGES + "/minMaxToTop-button.png";
  private static final String PATH_MIN_MAX_TO_TOP_HOVER_ICON = PATH_IMAGES + "/minMaxToTop-button-hover.png";
  private static final String PATH_MIN_MAX_TO_SIDE_ICON = PATH_IMAGES + "/minMaxToSide-button.png";
  private static final String PATH_MIN_MAX_TO_SIDE_HOVER_ICON = PATH_IMAGES + "/minMaxToSide-button-hover.png";
  private static final String PATH_MENU_BAR_ICON = PATH_IMAGES + "/menuebalken.png";
  private static final String PATH_VISTA_ICON = PATH_IMAGES + "/vista.png";
  private static final String PATH_SKILL_CHALLENGE_ICON = PATH_IMAGES + "/skill-challenge.png";
  private static final String PATH_HEART_ICON = PATH_IMAGES + "/heart.png";
  private static final String PATH_CHEST_ICON = PATH_IMAGES + "/chest.png";
  private static final String PATH_CHEST_PERMANENT_ICON = PATH_IMAGES + "/chest-permanent.png";
  private static final String PATH_COPPER_ICON = PATH_IMAGES + "/20px-Copper_Ore.png";
  private static final String PATH_GOLD_ICON = PATH_IMAGES + "/20px-Gold_Ore.png";
  private static final String PATH_IRON_ICON = PATH_IMAGES + "/20px-Iron_Ore.png";
  private static final String PATH_MITHRIL_ICON = PATH_IMAGES + "/20px-Mithril_Ore.png";
  private static final String PATH_ORICHALCHUM_ICON = PATH_IMAGES + "/20px-Orichalcum_Ore.png";
  private static final String PATH_PLATINUM_ICON = PATH_IMAGES + "/20px-Platinum_Ore.png";
  private static final String PATH_SILVER_ICON = PATH_IMAGES + "/20px-Silver_Ore.png";
  private static final String PATH_GREEN_WOOD_ICON = PATH_IMAGES + "/20px-Green_Wood_Log.png";
  private static final String PATH_SOFT_WOOD_ICON = PATH_IMAGES + "/20px-Soft_Wood_Log.png";
  private static final String PATH_SEASONED_WOOD_ICON = PATH_IMAGES + "/20px-Seasoned_Wood_Log.png";
  private static final String PATH_HARD_WOOD_ICON = PATH_IMAGES + "/20px-Hard_Wood_Log.png";
  private static final String PATH_ELDER_WOOD_ICON = PATH_IMAGES + "/20px-Elder_Wood_Log.png";
  private static final String PATH_ANCIENT_WOOD_ICON = PATH_IMAGES + "/20px-Ancient_Wood_Log.png";
  private static final String PATH_AXE_ICON = PATH_IMAGES + "/20px-Holzfaelleraxt_Icon.png";
  private static final String PATH_PICK_ICON = PATH_IMAGES + "/20px-Spitzhacke_Icon.png";
  private static final String PATH_SICKLES_ICON = PATH_IMAGES + "/20px-Erntesichel_Icon.png";
  private static final String PATH_PLANT_SAFFRON_THREAD_ICON = PATH_IMAGES + "/20px-Saffron_Thread.png";
  private static final String PATH_PLANT_GHOST_PEPPER_ICON = PATH_IMAGES + "/20px-Ghost_Pepper.png";
  private static final String PATH_PLANT_LOTUS_ROOT_ICON = PATH_IMAGES + "/20px-Lotus_Root.png";
  private static final String PATH_PLANT_OMNOMBERRY_ICON = PATH_IMAGES + "/20px-Omnomberry.png";
  private static final String PATH_PLANT_ORRIAN_TRUFFLE_ICON = PATH_IMAGES + "/20px-Orrian_Truffle.png";
  private static final String PATH_PLANT_SEAWEED_ICON = PATH_IMAGES + "/20px-Seaweed.png";
  private static final String PATH_PLANT_SNOW_TRUFFLE_ICON = PATH_IMAGES + "/20px-Snow_Truffle.png";

  public static final String PATH_PLANT_ARTICHOKE_ICON = PATH_IMAGES + "/20px-Artichoke.png";
  public static final String PATH_PLANT_ASPARAGUS_SPEAR_ICON = PATH_IMAGES + "/20px-Asparagus_Spear.png";
  public static final String PATH_PLANT_PASSION_FRUIT_ICON = PATH_IMAGES + "/20px-Passion_Fruit.png";
  public static final String PATH_PLANT_PASSION_FLOWER_ICON = PATH_IMAGES + "/20px-Passion_Flower.png";
  public static final String PATH_PLANT_BUTTERNUT_SQUASH_ICON = PATH_IMAGES + "/20px-Butternut_Squash.png";
  public static final String PATH_PLANT_CAYENNE_PEPPER_ICON = PATH_IMAGES + "/20px-Cayenne_Pepper.png";
  public static final String PATH_PLANT_HEAD_OF_GARLIC_ICON = PATH_IMAGES + "/20px-Head_of_Garlic.png";
  public static final String PATH_PLANT_LEMONGRASS_ICON = PATH_IMAGES + "/20px-Lemongrass.png";
  public static final String PATH_PLANT_CORIANDER_SEED_ICON = PATH_IMAGES + "/20px-Coriander_Seed.png";
  public static final String PATH_PLANT_TARRAGON_LEAVES_ICON = PATH_IMAGES + "/20px-Tarragon_Leaves.png";
  public static final String PATH_PLANT_GREEN_ONION_ICON = PATH_IMAGES + "/20px-Green_Onion.png";
  public static final String PATH_PLANT_LEEK_ICON = PATH_IMAGES + "/20px-Leek.png";
  public static final String PATH_PLANT_RASPBERRY_ICON = PATH_IMAGES + "/20px-Raspberry.png";
  public static final String PATH_PLANT_CLOVE_ICON = PATH_IMAGES + "/20px-Clove.png";
  public static final String PATH_PLANT_MINT_LEAF_ICON = PATH_IMAGES + "/20px-Mint_Leaf.png";
  public static final String PATH_PLANT_SAGE_LEAF_ICON = PATH_IMAGES + "/20px-Sage_Leaf.png";
  public static final String PATH_PLANT_THYME_LEAF_ICON = PATH_IMAGES + "/20px-Thyme_Leaf.png";
  public static final String PATH_PLANT_VANILLA_BEAN_ICON = PATH_IMAGES + "/20px-Vanilla_Bean.png";
  public static final String PATH_PLANT_BEET_ICON = PATH_IMAGES + "/20px-Beet.png";
  public static final String PATH_PLANT_PARSNIP_ICON = PATH_IMAGES + "/20px-Parsnip.png";
  public static final String PATH_PLANT_RUTABAGA_ICON = PATH_IMAGES + "/20px-Rutabaga.png";
  public static final String PATH_PLANT_TURNIP_ICON = PATH_IMAGES + "/20px-Turnip.png";
  public static final String PATH_PLANT_BLACKBERRY_ICON = PATH_IMAGES + "/20px-Blackberry.png";
  public static final String PATH_PLANT_HEAD_OF_CAULIFLOWER_ICON = PATH_IMAGES + "/20px-Head_of_Cauliflower.png";
  public static final String PATH_PLANT_CORAL_CHUNK_ICON = PATH_IMAGES + "/20px-Coral_Chunk.png";
  public static final String PATH_PLANT_CORAL_ORB_ICON = PATH_IMAGES + "/20px-Coral_Orb.png";
  public static final String PATH_PLANT_CORAL_TENTACLE_ICON = PATH_IMAGES + "/20px-Coral_Tentacle.png";
  public static final String PATH_PLANT_BAY_LEAF_ICON = PATH_IMAGES + "/20px-Bay_Leaf.png";
  public static final String PATH_PLANT_CHILI_PEPPER_ICON = PATH_IMAGES + "/20px-Chili_Pepper.png";
  public static final String PATH_PLANT_DILL_SPRIG_ICON = PATH_IMAGES + "/20px-Dill_Sprig.png";
  public static final String PATH_PLANT_ROSEMARY_SPRIG_ICON = PATH_IMAGES + "/20px-Rosemary_Sprig.png";
  public static final String PATH_PLANT_SESAME_SEED_ICON = PATH_IMAGES + "/20px-Sesame_Seed.png";
  public static final String PATH_PLANT_PORTOBELLO_MUSHROOM_ICON = PATH_IMAGES + "/20px-Portobello_Mushroom.png";
  public static final String PATH_PLANT_ONION_ICON = PATH_IMAGES + "/20px-Onion.png";
  public static final String PATH_PLANT_SUGAR_PUMPKIN_ICON = PATH_IMAGES + "/20px-Sugar_Pumpkin.png";
  public static final String PATH_PLANT_CARROT_ICON = PATH_IMAGES + "/20px-Carrot.png";
  public static final String PATH_PLANT_HEAD_OF_CABBAGE_ICON = PATH_IMAGES + "/20px-Head_of_Cabbage.png";
  public static final String PATH_PLANT_GRAPE_ICON = PATH_IMAGES + "/20px-Grape.png";
  public static final String PATH_PLANT_KALE_LEAF_ICON = PATH_IMAGES + "/20px-Kale_Leaf.png";
  public static final String PATH_PLANT_YAM_ICON = PATH_IMAGES + "/20px-Yam.png";
  public static final String PATH_PLANT_MUSHROOM_ICON = PATH_IMAGES + "/20px-Mushroom.png";
  public static final String PATH_PLANT_BLACK_PEPPERCORN_ICON = PATH_IMAGES + "/20px-Black_Peppercorn.png";
  public static final String PATH_PLANT_PARSLEY_LEAF_ICON = PATH_IMAGES + "/20px-Parsley_Leaf.png";
  public static final String PATH_PLANT_ZUCCHINI_ICON = PATH_IMAGES + "/20px-Zucchini.png";
  public static final String PATH_PLANT_CLAM_ICON = PATH_IMAGES + "/20px-Clam.png";
  public static final String PATH_PLANT_PEARL_ICON = PATH_IMAGES + "/20px-Pearl.png";
  public static final String PATH_PLANT_OREGANO_LEAF_ICON = PATH_IMAGES + "/20px-Oregano_Leaf.png";
  public static final String PATH_PLANT_SPINACH_LEAF_ICON = PATH_IMAGES + "/20px-Spinach_Leaf.png";
  public static final String PATH_PLANT_STRAWBERRY_ICON = PATH_IMAGES + "/20px-Strawberry.png";
  public static final String PATH_PLANT_BLUEBERRY_ICON = PATH_IMAGES + "/20px-Blueberry.png";
  public static final String PATH_PLANT_HEAD_OF_LETTUCE_ICON = PATH_IMAGES + "/20px-Head_of_Lettuce.png";
  public static final String PATH_PLANT_POTATO_ICON = PATH_IMAGES + "/20px-Potato.png";

  public static final String PATH_GUILD_BOUNTY_ICON = PATH_IMAGES + "/32px-guild-bounty_icon.png";

  /** Basis URL zur ArenaNet GW2 Tile API */
  public static final String BASE_URL = "https://render.guildwars2.com/file/";

  public static final String POI_ICON = "poiIcon";
  public static final String POI_FILE_ID = "97461";
  public static final String POI_SIGNATURE = "25B230711176AB5728E86F5FC5F0BFAE48B32F6E";

  public static final String UNLOCK_ICON = "unlockIcon";
  public static final String UNLOCK_FILE_ID = "102478";
  public static final String UNLOCK_SIGNATURE = "943538394A94A491C8632FBEF6203C2013443555";

  public static final String WAYPOINT_ICON = "waypointIcon";
  public static final String WAYPOINT_FILE_ID = "157353";
  public static final String WAYPOINT_SIGNATURE = "32633AF8ADEA696A1EF56D3AE32D617B10D3AC57";

  /** Manager-Methode für diese Klasse (Singleton) */
  public static ImageConstants getInstance() {
    return INSTANCE;
  }

  /** Beinhaltet alle geladenen Bilder und können über einen entsprechenden Schlüssel abgerufen werden */
  private final Map<String, Image> imageMap;

  /** Gibt an, ob {@link ImageConstants#loadImages()} aufgerufen wurde, damit die Bilder zur Verfügung stehen. */
  private boolean inited;

  /** Privater Konstruktor (Singleton). */
  private ImageConstants() {
    super();

    this.imageMap = new HashMap<>();
    this.inited = false;

    loadImages();
  }

  /**
   * Diese Methode sucht in einer Map nach einem passenden Bild zu dem angegebenen Schlüssel.
   * 
   * @param imageKey Ein Schlüssel zu dem ein Image gesucht werden soll.
   * @return Ein Image, dass zu dem Schlüssel gehört oder null, wenn kein Bild gefunden wurde.
   * @throws IllegalStateException Falls vergessen wurde die ImageFactory zu initialisieren.
   */
  public Image getImage(final String imageKey) {
    if (!this.inited) {
      throw new IllegalStateException("Es sind keine Bilder verfügbar, da 'loadImages' nicht aufgerufen wurde!");
    }
    return this.imageMap.get(imageKey);
  }

  /**
   * Diese Methode sucht delegiert den Aufruf an {@link ImageConstants#getImage(String)} weiter und wandelt das
   * zurüchgegebene Image in ein ImageIcon um.
   * 
   * @param imageKey Ein Schlüssel zu dem ein ImageIcon gesucht werden soll.
   * @return Ein ImageIcon, dass zu dem Schlüssel gehört oder null, wenn kein Bild gefunden wurde.
   * @throws IllegalStateException Falls vergessen wurde die ImageFactory zu initialisieren.
   * @author morrigan
   */
  public ImageIcon getImageIcon(final String imageKey) {
    if (!this.inited) {
      throw new IllegalStateException("Es sind keine Bilder verfügbar, da 'loadImages' nicht aufgerufen wurde!");
    }
    final Image img = getImage(imageKey);
    if (img != null) {
      return new ImageIcon(img);
    }
    return null;
  }

  public ImageIcon getImageIcon(final String imageKey, final Dimension scaleToDim) {
    return getImageIcon(imageKey, scaleToDim.width, scaleToDim.height);
  }

  public ImageIcon getImageIcon(final String imageKey, final int scaleToWidth, final int scaleToHeight) {
    if (!this.inited) {
      throw new IllegalStateException("Es sind keine Bilder verfügbar, da 'loadImages' nicht aufgerufen wurde!");
    }
    Image img = getImage(imageKey);
    if (img != null) {
      img = img.getScaledInstance(scaleToWidth, scaleToHeight, Image.SCALE_SMOOTH);
      return new ImageIcon(img);
    }
    return null;
  }

  /**
   * Diese Methode lädt ein Bild zu dem angegebenen Pfad und legt dieses in der HashMap mit dem angegebenen Schlüssel
   * ab.
   * 
   * @param imagePath Der Pfad unter dem das zu ladene bild liegt.
   * @param imageKey Ein Schlüssel unter dem das Bild später in der HashMap abgerufen werden kann.
   */
  private void loadImage(final String imagePath, final String imageKey) {
    LOG.debug("imagePath: {}, imageKey: {}", imagePath, imageKey);

    final URL urlToImage = getClass().getResource(imagePath);
    if (urlToImage != null) {
      try {
        final Image loadedImage = ImageIO.read(urlToImage);
        this.imageMap.put(imageKey, loadedImage);
      } catch (final IOException e) {
        LOG.error(e.getMessage(), e);
      }
    } else {
      LOG.warn(imagePath + " nicht gefunden!");
    }
  }

  /**
   * Diese Methode muss vor der ersten Benutzung aufgerufen werden, damit die Bilder in den Speicher geladen werden
   * können und zur Laufzeit über die verfügbaren Konstanten bereit stehen.
   */
  private void loadImages() {
    loadImage(PATH_EMPTY_ICON, EMPTY_ICON);
    loadImage(PATH_BLUE_BALL_ICON, BLUE_BALL_ICON);
    loadImage(PATH_ARROWS_UP_ICON, ARROWS_UP_ICON);
    loadImage(PATH_ARROWS_DOWN_ICON, ARROWS_DOWN_ICON);
    loadImage(PATH_ARROWS_LEFT_ICON, ARROWS_LEFT_ICON);
    loadImage(PATH_ARROW_LEFT_ICON, ARROW_LEFT_ICON);
    loadImage(PATH_ARROWS_RIGHT_ICON, ARROWS_RIGHT_ICON);
    loadImage(PATH_ARROW_RIGHT_ICON, ARROW_RIGHT_ICON);
    loadImage(PATH_ERROR_ICON, ERROR_ICON);
    loadImage(PATH_CONFIRMATION_ICON, CONFIRMATION_ICON);
    loadImage(PATH_NOTIFICATION_ICON, NOTIFICATION_ICON);
    loadImage(PATH_UNCHANGED_ICON, UNCHANGED_ICON);
    loadImage(PATH_MODIFIED_ICON, MODIFIED_ICON);
    loadImage(PATH_DELETED_ICON, DELETED_ICON);
    loadImage(PATH_DELETE_ICON, DELETE_ICON);

    loadImage(PATH_RICH_ICON, RICH_ICON);
    loadImage(PATH_PERMANENT_ICON, PERMANENT_ICON);
    loadImage(PATH_ORE_ICON, ORE_ICON);
    loadImage(PATH_ORE_RICH_ICON, ORE_RICH_ICON);
    loadImage(PATH_ORE_RICH_PERMANENT_ICON, ORE_RICH_PERMANENT_ICON);
    loadImage(PATH_ORE_PERMANENT_ICON, ORE_PERMANENT_ICON);
    loadImage(PATH_WOOD_ICON, WOOD_ICON);
    loadImage(PATH_WOOD_RICH_ICON, WOOD_RICH_ICON);
    loadImage(PATH_WOOD_RICH_PERMANENT_ICON, WOOD_RICH_PERMANENT_ICON);
    loadImage(PATH_WOOD_PERMANENT_ICON, WOOD_PERMANENT_ICON);
    loadImage(PATH_PLANT_ICON, PLANT_ICON);
    loadImage(PATH_PLANT_RICH_ICON, PLANT_RICH_ICON);
    loadImage(PATH_PLANT_RICH_PERMANENT_ICON, PLANT_RICH_PERMANENT_ICON);
    loadImage(PATH_PLANT_PERMANENT_ICON, PLANT_PERMANENT_ICON);
    loadImage(PATH_FACEBOOK_ICON, FACEBOOK_ICON);
    loadImage(PATH_GOOGLE_PLUS_ICON, GOOGLE_PLUS_ICON);
    loadImage(PATH_TWITTER_ICON, TWITTER_ICON);
    loadImage(PATH_BACKGROUND_IMAGE, BACKGROUND_IMAGE);
    loadImage(PATH_SELECTED_DRAGON_ICON, SELECTED_DRAGON_ICON);
    loadImage(PATH_DRAGON_ICON, DRAGON_ICON);
    loadImage(PATH_SELECTED_MAP_ICON, SELECTED_MAP_ICON);
    loadImage(PATH_SELECTED_INFO_ICON, SELECTED_INFO_ICON);
    loadImage(PATH_MAP_ICON, MAP_ICON);
    loadImage(PATH_INFO_ICON, INFO_ICON);
    loadImage(PATH_BAR_MAP_ICON, BAR_MAP_ICON);
    loadImage(PATH_BAR_MAP_HOVER_ICON, BAR_MAP_HOVER_ICON);
    loadImage(PATH_BAR_EXIT_ICON, BAR_EXIT_ICON);
    loadImage(PATH_BAR_EXIT_HOVER_ICON, BAR_EXIT_HOVER_ICON);
    loadImage(PATH_BAR_INFO_ICON, BAR_INFO_ICON);
    loadImage(PATH_BAR_INFO_HOVER_ICON, BAR_INFO_HOVER_ICON);
    loadImage(PATH_SELECTED_STATS_ICON, SELECTED_STATS_ICON);
    loadImage(PATH_STATS_ICON, STATS_ICON);
    loadImage(PATH_CLOSE_ICON, CLOSE_ICON);
    loadImage(PATH_CLOSE_HOVER_ICON, CLOSE_HOVER_ICON);
    loadImage(PATH_MIN_MAX_TO_TOP_ICON, MIN_MAX_TO_TOP_ICON);
    loadImage(PATH_MIN_MAX_TO_TOP_HOVER_ICON, MIN_MAX_TO_TOP_HOVER_ICON);
    loadImage(PATH_MIN_MAX_TO_SIDE_ICON, MIN_MAX_TO_SIDE_ICON);
    loadImage(PATH_MIN_MAX_TO_SIDE_HOVER_ICON, MIN_MAX_TO_SIDE_HOVER_ICON);
    loadImage(PATH_MENU_BAR_ICON, MENU_BAR_ICON);
    loadImage(PATH_VISTA_ICON, VISTA_ICON);
    loadImage(PATH_SKILL_CHALLENGE_ICON, SKILL_CHALLEGENE_ICON);
    loadImage(PATH_HEART_ICON, HEART_ICON);
    loadImage(PATH_CHEST_ICON, CHEST_ICON);
    loadImage(PATH_CHEST_PERMANENT_ICON, CHEST_PERMANENT_ICON);
    loadImage(PATH_COPPER_ICON, COPPER_ICON);
    loadImage(PATH_GOLD_ICON, GOLD_ICON);
    loadImage(PATH_IRON_ICON, IRON_ICON);
    loadImage(PATH_MITHRIL_ICON, MITHRIL_ICON);
    loadImage(PATH_ORICHALCHUM_ICON, ORICHALCUM_ICON);
    loadImage(PATH_PLATINUM_ICON, PLATINUM_ICON);
    loadImage(PATH_SILVER_ICON, SILVER_ICON);
    loadImage(PATH_GREEN_WOOD_ICON, GREEN_WOOD_ICON);
    loadImage(PATH_SOFT_WOOD_ICON, SOFT_WOOD_ICON);
    loadImage(PATH_SEASONED_WOOD_ICON, SEASONED_WOOD_ICON);
    loadImage(PATH_HARD_WOOD_ICON, HARD_WOOD_ICON);
    loadImage(PATH_ELDER_WOOD_ICON, ELDER_WOOD_ICON);
    loadImage(PATH_ANCIENT_WOOD_ICON, ANCIENT_WOOD_ICON);
    loadImage(PATH_AXE_ICON, AXE_ICON);
    loadImage(PATH_PICK_ICON, PICK_ICON);
    loadImage(PATH_SICKLES_ICON, SICKLES_ICON);
    loadImage(PATH_PLANT_SAFFRON_THREAD_ICON, PLANT_SAFFRON_THREAD_ICON);
    loadImage(PATH_PLANT_GHOST_PEPPER_ICON, PLANT_GHOST_PEPPER_ICON);
    loadImage(PATH_PLANT_LOTUS_ROOT_ICON, PLANT_LOTUS_ROOT_ICON);
    loadImage(PATH_PLANT_OMNOMBERRY_ICON, PLANT_OMNOMBERRY_ICON);
    loadImage(PATH_PLANT_ORRIAN_TRUFFLE_ICON, PLANT_ORRIAN_TRUFFLE_ICON);
    loadImage(PATH_PLANT_SEAWEED_ICON, PLANT_SEAWEED_ICON);
    loadImage(PATH_PLANT_SNOW_TRUFFLE_ICON, PLANT_SNOW_TRUFFLE_ICON);

    loadImage(PATH_PLANT_ARTICHOKE_ICON, PLANT_ARTICHOKE_ICON);
    loadImage(PATH_PLANT_ASPARAGUS_SPEAR_ICON, PLANT_ASPARAGUS_SPEAR_ICON);
    loadImage(PATH_PLANT_PASSION_FRUIT_ICON, PLANT_PASSION_FRUIT_ICON);
    loadImage(PATH_PLANT_PASSION_FLOWER_ICON, PLANT_PASSION_FLOWER_ICON);
    loadImage(PATH_PLANT_BUTTERNUT_SQUASH_ICON, PLANT_BUTTERNUT_SQUASH_ICON);
    loadImage(PATH_PLANT_CAYENNE_PEPPER_ICON, PLANT_CAYENNE_PEPPER_ICON);
    loadImage(PATH_PLANT_HEAD_OF_GARLIC_ICON, PLANT_HEAD_OF_GARLIC_ICON);
    loadImage(PATH_PLANT_LEMONGRASS_ICON, PLANT_LEMONGRASS_ICON);
    loadImage(PATH_PLANT_CORIANDER_SEED_ICON, PLANT_CORIANDER_SEED_ICON);
    loadImage(PATH_PLANT_TARRAGON_LEAVES_ICON, PLANT_TARRAGON_LEAVES_ICON);
    loadImage(PATH_PLANT_GREEN_ONION_ICON, PLANT_GREEN_ONION_ICON);
    loadImage(PATH_PLANT_LEEK_ICON, PLANT_LEEK_ICON);
    loadImage(PATH_PLANT_RASPBERRY_ICON, PLANT_RASPBERRY_ICON);
    loadImage(PATH_PLANT_CLOVE_ICON, PLANT_CLOVE_ICON);
    loadImage(PATH_PLANT_MINT_LEAF_ICON, PLANT_MINT_LEAF_ICON);
    loadImage(PATH_PLANT_SAGE_LEAF_ICON, PLANT_SAGE_LEAF_ICON);
    loadImage(PATH_PLANT_THYME_LEAF_ICON, PLANT_THYME_LEAF_ICON);
    loadImage(PATH_PLANT_VANILLA_BEAN_ICON, PLANT_VANILLA_BEAN_ICON);
    loadImage(PATH_PLANT_BEET_ICON, PLANT_BEET_ICON);
    loadImage(PATH_PLANT_PARSNIP_ICON, PLANT_PARSNIP_ICON);
    loadImage(PATH_PLANT_RUTABAGA_ICON, PLANT_RUTABAGA_ICON);
    loadImage(PATH_PLANT_TURNIP_ICON, PLANT_TURNIP_ICON);
    loadImage(PATH_GUILD_BOUNTY_ICON, GUILD_BOUNTY_ICON);
    loadImage(PATH_PLANT_BLACKBERRY_ICON, PLANT_BLACKBERRY_ICON);
    loadImage(PATH_PLANT_HEAD_OF_CAULIFLOWER_ICON, PLANT_HEAD_OF_CAULIFLOWER_ICON);
    loadImage(PATH_PLANT_CORAL_CHUNK_ICON, PLANT_CORAL_CHUNK_ICON);
    loadImage(PATH_PLANT_CORAL_ORB_ICON, PLANT_CORAL_ORB_ICON);
    loadImage(PATH_PLANT_CORAL_TENTACLE_ICON, PLANT_CORAL_TENTACLE_ICON);
    loadImage(PATH_PLANT_BAY_LEAF_ICON, PLANT_BAY_LEAF_ICON);
    loadImage(PATH_PLANT_CHILI_PEPPER_ICON, PLANT_CHILI_PEPPER_ICON);
    loadImage(PATH_PLANT_DILL_SPRIG_ICON, PLANT_DILL_SPRIG_ICON);
    loadImage(PATH_PLANT_ROSEMARY_SPRIG_ICON, PLANT_ROSEMARY_SPRIG_ICON);
    loadImage(PATH_PLANT_SESAME_SEED_ICON, PLANT_SESAME_SEED_ICON);
    loadImage(PATH_PLANT_PORTOBELLO_MUSHROOM_ICON, PLANT_PORTOBELLO_MUSHROOM_ICON);
    loadImage(PATH_PLANT_ONION_ICON, PLANT_ONION_ICON);
    loadImage(PATH_PLANT_SUGAR_PUMPKIN_ICON, PLANT_SUGAR_PUMPKIN_ICON);
    loadImage(PATH_PLANT_CARROT_ICON, PLANT_CARROT_ICON);
    loadImage(PATH_PLANT_HEAD_OF_CABBAGE_ICON, PLANT_HEAD_OF_CABBAGE_ICON);
    loadImage(PATH_PLANT_GRAPE_ICON, PLANT_GRAPE_ICON);
    loadImage(PATH_PLANT_KALE_LEAF_ICON, PLANT_KALE_LEAF_ICON);
    loadImage(PATH_PLANT_YAM_ICON, PLANT_YAM_ICON);
    loadImage(PATH_PLANT_MUSHROOM_ICON, PLANT_MUSHROOM_ICON);
    loadImage(PATH_PLANT_BLACK_PEPPERCORN_ICON, PLANT_BLACK_PEPPERCORN_ICON);
    loadImage(PATH_PLANT_PARSLEY_LEAF_ICON, PLANT_PARSLEY_LEAF_ICON);
    loadImage(PATH_PLANT_ZUCCHINI_ICON, PLANT_ZUCCHINI_ICON);
    loadImage(PATH_PLANT_CLAM_ICON, PLANT_CLAM_ICON);
    loadImage(PATH_PLANT_PEARL_ICON, PLANT_PEARL_ICON);
    loadImage(PATH_PLANT_OREGANO_LEAF_ICON, PLANT_OREGANO_LEAF_ICON);
    loadImage(PATH_PLANT_SPINACH_LEAF_ICON, PLANT_SPINACH_LEAF_ICON);
    loadImage(PATH_PLANT_STRAWBERRY_ICON, PLANT_STRAWBERRY_ICON);
    loadImage(PATH_PLANT_BLUEBERRY_ICON, PLANT_BLUEBERRY_ICON);
    loadImage(PATH_PLANT_HEAD_OF_LETTUCE_ICON, PLANT_HEAD_OF_LETTUCE_ICON);
    loadImage(PATH_PLANT_POTATO_ICON, PLANT_POTATO_ICON);

    URL url;
    try {
      // url = new URL(BASE_URL + ORE_SIGNATURE + "/" + ORE_FILE_ID + ".png");
      // BufferedImage imgOre = ImageIO.read(url);
      // this.imageMap.put(ORE_ICON, imgOre);
      //
      // url = new URL(BASE_URL + WOOD_SIGNATURE + "/" + WOOD_FILE_ID + ".png");
      // BufferedImage imgWood = ImageIO.read(url);
      // this.imageMap.put(WOOD_ICON, imgWood);
      //
      // url = new URL(BASE_URL + PLANT_SIGNATURE + "/" + PLANT_FILE_ID + ".png");
      // BufferedImage imgPlant = ImageIO.read(url);
      // this.imageMap.put(PLANT_ICON, imgPlant);

      url = new URL(BASE_URL + POI_SIGNATURE + "/" + POI_FILE_ID + ".png");
      BufferedImage imgPoi = ImageIO.read(url);
      this.imageMap.put(POI_ICON, imgPoi);

      url = new URL(BASE_URL + UNLOCK_SIGNATURE + "/" + UNLOCK_FILE_ID + ".png");
      BufferedImage imgUnlock = ImageIO.read(url);
      this.imageMap.put(UNLOCK_ICON, imgUnlock);

      url = new URL(BASE_URL + WAYPOINT_SIGNATURE + "/" + WAYPOINT_FILE_ID + ".png");
      BufferedImage imgWP = ImageIO.read(url);
      this.imageMap.put(WAYPOINT_ICON, imgWP);

    } catch (MalformedURLException e) {
      LOG.error(e.getMessage(), e);
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
    }

    this.inited = true;
  }
}
