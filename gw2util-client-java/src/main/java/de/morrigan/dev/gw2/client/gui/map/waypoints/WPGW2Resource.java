package de.morrigan.dev.gw2.client.gui.map.waypoints;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import de.morrigan.dev.gw2.client.gui.AbstractGW2Waypoint;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.resources.ImageManager;
import de.morrigan.dev.gw2.resources.ResourceManager;
import net.coobird.thumbnailator.Thumbnailator;

public class WPGW2Resource extends AbstractGW2Waypoint {

	/** Handle auf den ResourceManager */
	private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

	public static HoverInfo getHoverInfoBySubType(WPSubType wpSubType, int iconWidth, int iconHeight) {
		List<BufferedImage> resourceIcon = new ArrayList<>();
		BufferedImage gatheringIcon;
		List<String> resourceLabelKey = new ArrayList<>();
		String gatheringLabelKey = null;

		switch (wpSubType) {
			// Erze
			case ORICHALCUM:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.ORICHALCUM_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PICK_ICON);
				resourceLabelKey.add("orichalcum");
				gatheringLabelKey = "orichalcumPick";
			break;
			case MITHRIL:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.MITHRIL_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PICK_ICON);
				resourceLabelKey.add("mithril");
				gatheringLabelKey = "mithrilPick";
			break;
			case PLATINUM:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLATINUM_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PICK_ICON);
				resourceLabelKey.add("platinum");
				gatheringLabelKey = "darksteelPick";
			break;
			case GOLD:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.GOLD_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PICK_ICON);
				resourceLabelKey.add("gold");
				gatheringLabelKey = "steelPick";
			break;
			case SILVER:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SILVER_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PICK_ICON);
				resourceLabelKey.add("silver");
				gatheringLabelKey = "ironPick";
			break;
			case IRON:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.IRON_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PICK_ICON);
				resourceLabelKey.add("iron");
				gatheringLabelKey = "ironPick";
			break;
			case COPPER:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.COPPER_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PICK_ICON);
				resourceLabelKey.add("copper");
				gatheringLabelKey = "copperPick";
			break;

			// Hölzer
			case ASPEN:
			case EKKU:
			case KERTCH:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.GREEN_WOOD_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.AXE_ICON);
				resourceLabelKey.add("greenWoodLog");
				gatheringLabelKey = "copperAxe";
			break;
			case GUMMO:
			case MIMOSA:
			case SNOWCHERRY:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SOFT_WOOD_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.AXE_ICON);
				resourceLabelKey.add("softWoodLog");
				gatheringLabelKey = "ironAxe";
			break;
			case FIR:
			case TUKAWA:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SEASONED_WOOD_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.AXE_ICON);
				resourceLabelKey.add("seasonedWoodLog");
				gatheringLabelKey = "steelAxe";
			break;
			case BANYAN:
			case INGLEWOOD:
			case PINE:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.HARD_WOOD_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.AXE_ICON);
				resourceLabelKey.add("hardWoodLog");
				gatheringLabelKey = "darksteelAxe";
			break;
			case BAOBA:
			case CYPRESS:
			case REDOAK:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.ELDER_WOOD_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.AXE_ICON);
				resourceLabelKey.add("elderWoodLog");
				gatheringLabelKey = "mithrilAxe";
			break;
			case ANCIENT:
			case ORRIAN:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.ANCIENT_WOOD_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.AXE_ICON);
				resourceLabelKey.add("ancientWoodLog");
				gatheringLabelKey = "orichalcumAxe";
			break;

			// Planzen
			case BLACK_CROCUS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SAFFRON_THREAD_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("saffronThread");
				gatheringLabelKey = "orichalcumHarvestingSickle";
			break;
			case GHOST_PEPPER:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_GHOST_PEPPER_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("ghostPepper");
				gatheringLabelKey = "orichalcumHarvestingSickle";
			break;
			case LOTUS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_LOTUS_ROOT_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("lotusRoot");
				gatheringLabelKey = "orichalcumHarvestingSickle";
			break;
			case OMNOMBERRIES:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_OMNOMBERRY_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("omnomberry");
				gatheringLabelKey = "orichalcumHarvestingSickle";
			break;
			case ORRIAN_TRUFFLE:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ORRIAN_TRUFFLE_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("orrianTruffle");
				gatheringLabelKey = "orichalcumHarvestingSickle";
			break;
			case SEAWEED:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SEAWEED_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("seaweed");
				gatheringLabelKey = "orichalcumHarvestingSickle";
			break;
			case SNOW_TRUFFLE:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SNOW_TRUFFLE_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("snowTruffle");
				gatheringLabelKey = "orichalcumHarvestingSickle";
			break;
			case ARTICHOKE:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ARTICHOKE_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("artichoke");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case ASPARAGUS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ASPARAGUS_SPEAR_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("asparagusSpear");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case BLOOMING_PASSIFLORA:
			case PASSIFLORA:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PASSION_FRUIT_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PASSION_FLOWER_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("passionFruit");
				resourceLabelKey.add("passionFlower");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case BUTTERNUT_SQUASH:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BUTTERNUT_SQUASH_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("butternutSquash");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case CAYENNE_PEPPER:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CAYENNE_PEPPER_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("cayennePepper");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case HERB_PATCH:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_GARLIC_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_LEMONGRASS_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CORIANDER_SEED_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_TARRAGON_LEAVES_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("headOfGarlic");
				resourceLabelKey.add("lemongrass");
				resourceLabelKey.add("corianderSeed");
				resourceLabelKey.add("tarragonLeaves");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case LEEKS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_GREEN_ONION_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_LEEK_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("greenOnion");
				resourceLabelKey.add("leek");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case RASPBERRIES:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_RASPBERRY_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("raspberry");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case VERDANT_HERBS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CLOVE_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_GARLIC_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_MINT_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SAGE_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_TARRAGON_LEAVES_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_THYME_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_VANILLA_BEAN_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("clove");
				resourceLabelKey.add("headOfGarlic");
				resourceLabelKey.add("mintLeaf");
				resourceLabelKey.add("sageLeaf");
				resourceLabelKey.add("tarragonLeaves");
				resourceLabelKey.add("thymeLeaf");
				resourceLabelKey.add("vanillaBean");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case WINTER_ROOT_VEGETABLES:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BEET_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PARSNIP_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_RUTABAGA_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_TURNIP_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("beet");
				resourceLabelKey.add("parsnip");
				resourceLabelKey.add("rutabaga");
				resourceLabelKey.add("turnip");
				gatheringLabelKey = "mithrilHarvestingSickle";
			break;
			case BLACKBERRIES:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BLACKBERRY_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("blackberry");
				gatheringLabelKey = "darksteelHarvestingSickle";
			break;
			case CAULIFLOWER:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_CAULIFLOWER_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("headOfCauliflower");
				gatheringLabelKey = "darksteelHarvestingSickle";
			break;
			case CORAL:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CORAL_CHUNK_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CORAL_ORB_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CORAL_TENTACLE_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("coralChunk");
				resourceLabelKey.add("coralOrb");
				resourceLabelKey.add("coralTentacle");
				gatheringLabelKey = "darksteelHarvestingSickle";
			break;
			case MATURE_HERBS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BAY_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CHILI_PEPPER_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CORIANDER_SEED_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_DILL_SPRIG_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_GARLIC_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_MINT_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ROSEMARY_SPRIG_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SESAME_SEED_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_THYME_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_VANILLA_BEAN_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("bayLeaf");
				resourceLabelKey.add("chiliPepper");
				resourceLabelKey.add("corianderSeed");
				resourceLabelKey.add("dillSprig");
				resourceLabelKey.add("headOfGarlic");
				resourceLabelKey.add("mintLeaf");
				resourceLabelKey.add("rosemarySprig");
				resourceLabelKey.add("sesameSeed");
				resourceLabelKey.add("thymeLeaf");
				resourceLabelKey.add("vanillaBean");
				gatheringLabelKey = "darksteelHarvestingSickle";
			break;
			case PORTOBELLO_MUSHROOMS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PORTOBELLO_MUSHROOM_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("portobelloMushroom");
				gatheringLabelKey = "darksteelHarvestingSickle";
			break;
			case SCALLIONS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_GREEN_ONION_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ONION_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("greenOnion");
				resourceLabelKey.add("onion");
				gatheringLabelKey = "darksteelHarvestingSickle";
			break;
			case SUGAR_PUMPKIN:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SUGAR_PUMPKIN_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("sugarPumpkin");
				gatheringLabelKey = "darksteelHarvestingSickle";
			break;
			case VARIEGATED_TAPROOTS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CARROT_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_RUTABAGA_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_TURNIP_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("carrot");
				resourceLabelKey.add("rutabaga");
				resourceLabelKey.add("turnip");
				gatheringLabelKey = "darksteelHarvestingSickle";
			break;
			case CABBAGE:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_CABBAGE_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("headOfCabbage");
				gatheringLabelKey = "steelHarvestingSickle";
			break;
			case GRAPES:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_GRAPE_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("grape");
				gatheringLabelKey = "steelHarvestingSickle";
			break;
			case KALE:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_KALE_LEAF_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("kaleLeaf");
				gatheringLabelKey = "steelHarvestingSickle";
			break;
			case ROOT_VEGETABLES:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BEET_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CARROT_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_TURNIP_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_YAM_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("beet");
				resourceLabelKey.add("carrot");
				resourceLabelKey.add("turnip");
				resourceLabelKey.add("yam");
				gatheringLabelKey = "steelHarvestingSickle";
			break;
			case VARIED_MUSHROOMS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_MUSHROOM_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PORTOBELLO_MUSHROOM_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("mushroom");
				resourceLabelKey.add("portobelloMushroom");
				gatheringLabelKey = "steelHarvestingSickle";
			break;
			case YOUNG_HERBS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BAY_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BLACK_PEPPERCORN_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CHILI_PEPPER_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_DILL_SPRIG_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_GARLIC_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PARSLEY_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ROSEMARY_SPRIG_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SAGE_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SESAME_SEED_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_THYME_LEAF_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("bayLeaf");
				resourceLabelKey.add("blackPeppercorn");
				resourceLabelKey.add("chiliPepper");
				resourceLabelKey.add("dillSprig");
				resourceLabelKey.add("headOfGarlic");
				resourceLabelKey.add("parsleyLeaf");
				resourceLabelKey.add("rosemarySprig");
				resourceLabelKey.add("sageLeaf");
				resourceLabelKey.add("sesameSeed");
				resourceLabelKey.add("thymeLeaf");
				gatheringLabelKey = "steelHarvestingSickle";
			break;
			case ZUCCHINI:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ZUCCHINI_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("zucchini");
				gatheringLabelKey = "steelHarvestingSickle";
			break;
			case CLAM:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CLAM_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PEARL_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("clam");
				resourceLabelKey.add("pearl");
				gatheringLabelKey = "ironHarvestingSickle";
			break;
			case HERB_SPROUTS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BAY_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BLACK_PEPPERCORN_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CHILI_PEPPER_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_GARLIC_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_OREGANO_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PARSLEY_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SAGE_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_THYME_LEAF_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("bayLeaf");
				resourceLabelKey.add("blackPeppercorn");
				resourceLabelKey.add("chiliPepper");
				resourceLabelKey.add("headOfGarlic");
				resourceLabelKey.add("oreganoLeaf");
				resourceLabelKey.add("parsleyLeaf");
				resourceLabelKey.add("sageLeaf");
				resourceLabelKey.add("thymeLeaf");
				gatheringLabelKey = "ironHarvestingSickle";
			break;
			case SPINACH:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SPINACH_LEAF_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("spinachLeaf");
				gatheringLabelKey = "ironHarvestingSickle";
			break;
			case STRAWBERRY_PATCH:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_STRAWBERRY_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("strawberry");
				gatheringLabelKey = "ironHarvestingSickle";
			break;
			case TAPROOTS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BEET_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CARROT_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_SAGE_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_TURNIP_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("beet");
				resourceLabelKey.add("carrot");
				resourceLabelKey.add("sageLeaf");
				resourceLabelKey.add("turnip");
				gatheringLabelKey = "ironHarvestingSickle";
			break;
			case BLUEBERRY_BUSH:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BLUEBERRY_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("blueberry");
				gatheringLabelKey = "copperHarvestingSickle";
			break;
			case BUTTON_MUSHROOMS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_MUSHROOM_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("mushroom");
				gatheringLabelKey = "copperHarvestingSickle";
			break;
			case CARROTS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_CARROT_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("carrot");
				gatheringLabelKey = "copperHarvestingSickle";
			break;
			case HERB_PATCH_LOW:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BLACK_PEPPERCORN_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PARSLEY_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_THYME_LEAF_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("blackPeppercorn");
				resourceLabelKey.add("parsleyLeaf");
				resourceLabelKey.add("thymeLeaf");
				gatheringLabelKey = "copperHarvestingSickle";
			break;
			case HERB_SEEDLINGS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_BLACK_PEPPERCORN_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_GARLIC_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PARSLEY_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_THYME_LEAF_ICON));
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_VANILLA_BEAN_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("blackPeppercorn");
				resourceLabelKey.add("headOfGarlic");
				resourceLabelKey.add("parsleyLeaf");
				resourceLabelKey.add("thymeLeaf");
				resourceLabelKey.add("vanillaBean");
				gatheringLabelKey = "copperHarvestingSickle";
			break;
			case LETTUCE:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_HEAD_OF_LETTUCE_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("headOfLettuce");
				gatheringLabelKey = "copperHarvestingSickle";
			break;
			case ONIONS:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ONION_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("onion");
				gatheringLabelKey = "copperHarvestingSickle";
			break;
			case POTATO:
				resourceIcon.add((BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_POTATO_ICON));
				gatheringIcon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SICKLES_ICON);
				resourceLabelKey.add("potato");
				gatheringLabelKey = "copperHarvestingSickle";
			break;

			default:
				resourceIcon = null;
				gatheringIcon = null;
			break;
		}
		if ((iconWidth > 0) && (iconHeight > 0)) {
			List<BufferedImage> images = new ArrayList<>();
			for (BufferedImage img : resourceIcon) {
				images.add(Thumbnailator.createThumbnail(img, iconWidth, iconHeight));
			}
			resourceIcon = images;
			gatheringIcon = Thumbnailator.createThumbnail(gatheringIcon, iconWidth, iconHeight);
		}

		HoverInfo info = new HoverInfo();
		info.resourceIcon = new ArrayList<>();
		if (resourceIcon != null) {
			for (BufferedImage img : resourceIcon) {
				info.resourceIcon.add(new ImageIcon(img));
			}
		}
		if (gatheringIcon != null) {
			info.gatheringIcon = new ImageIcon(gatheringIcon);
		}
		info.resourceLabel = new ArrayList<>();
		for (String labelKey : resourceLabelKey) {
			info.resourceLabel.add(RESOURCE_MANAGER.getLabel(labelKey));
		}
		info.gatheringLabel = RESOURCE_MANAGER.getLabel(gatheringLabelKey);
		return info;
	}

	private final long id;
	private final Date updateDate;
	private final String username;
	private boolean rich;
	private boolean permanent;

	public WPGW2Resource(long id, GeoPosition position, WPType wpType, WPSubType wpSubType, Date updateDate,
			String username, boolean rich, boolean permanent) {
		super(position, ResourceManager.getInstance().getLabel(wpSubType.getLabelKey()), wpType, wpSubType, rich,
				permanent);

		this.id = id;
		this.updateDate = updateDate;
		this.username = username;
		this.rich = rich;
		this.permanent = permanent;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WPGW2Resource other = (WPGW2Resource) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String getHoverInfo() {
		StringBuilder hoverInfo = new StringBuilder(super.getHoverInfo());
		if (this.rich && this.permanent) {
			hoverInfo.append(" (");
			hoverInfo.append(RESOURCE_MANAGER.getLabel("rich"));
			hoverInfo.append(", ");
			hoverInfo.append(RESOURCE_MANAGER.getLabel("permanent"));
			hoverInfo.append(")");
		} else if (this.rich) {
			hoverInfo.append(" (").append(RESOURCE_MANAGER.getLabel("rich")).append(")");
		} else if (this.permanent) {
			hoverInfo.append(" (").append(RESOURCE_MANAGER.getLabel("permanent")).append(")");
		}
		return hoverInfo.toString();
	}

	@Override
	public long getId() {
		return this.id;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	public boolean isPermanent() {
		return this.permanent;
	}

	public boolean isRich() {
		return this.rich;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
		updateIcon(getWpType(), getWPSubType(), this.rich, this.permanent);
	}

	public void setRich(boolean rich) {
		this.rich = rich;
		updateIcon(getWpType(), getWPSubType(), this.rich, this.permanent);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", position: ").append(getPosition());
		sb.append(", wpType: ").append(getWpType());
		sb.append(", wpSubType: ").append(getWPSubType());
		sb.append(", rich: ").append(this.rich);
		sb.append(", permanent: ").append(this.permanent);
		sb.append("]");
		return sb.toString();
	}

	@Override
	protected ZoomScale[] getZoomScale() {
		return new ZoomScale[] { new ZoomScale(0, 1), new ZoomScale(1, 1.5), new ZoomScale(2, 2), new ZoomScale(3, 2.5) };
	}
}
