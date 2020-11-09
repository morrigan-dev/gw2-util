package de.morrigan.dev.gw2.client.gui.components;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.resources.ImageManager;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.BitUtils;
import de.morrigan.dev.swing.models.AbstractModel;

public class FilterModel extends AbstractModel {

	public class TreeObject {

		public ImageIcon icon;
		public String label;
		public long value;
		public int sortOrder;

		public TreeObject(ImageIcon icon, String label, long value, int sortOrder) {
			super();
			this.icon = icon;
			this.label = label;
			this.value = value;
			this.sortOrder = sortOrder;
		}

		@Override
		public String toString() {
			return this.label + "(" + this.sortOrder + ")";
		}
	}

	private class GroupNode extends DefaultMutableTreeNode {

		private static final long serialVersionUID = 1L;

		public GroupNode(TreeObject treeObject) {
			super(treeObject, true);
		}

		@Override
		public boolean isLeaf() {
			return false;
		}
	}

	private class ItemNode extends DefaultMutableTreeNode {

		private static final long serialVersionUID = 1L;

		public ItemNode(TreeObject treeObject) {
			super(treeObject, false);
		}

		@Override
		public boolean isLeaf() {
			return true;
		}
	}
	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(FilterModel.class);
	
	/** Handel auf den LabelManager */
	private static final ResourceManager RM = ResourceManager.getInstance();

	/** Handel auf den ImageManager */
	private static final ImageManager IM = ImageManager.getInstance();

	private static final Dimension ICON_SIZE = new Dimension(16, 16);

	//
	private static final int GROUP_ROOT = 1;
	private static final int GROUP_ORE = 2;
	private static final int GROUP_WOOD = 3;
	private static final int GROUP_PLANT = 4;
	private static final int GROUP_OTHER = 5;
	//
	public static final long ORE_ORICHALCUM = BitUtils.setLongBit(0);
	public static final long ORE_MITHRIL = BitUtils.setLongBit(1);
	public static final long ORE_PLATINUM = BitUtils.setLongBit(2);
	public static final long ORE_GOLD = BitUtils.setLongBit(3);
	public static final long ORE_SILVER = BitUtils.setLongBit(4);
	public static final long ORE_IRON = BitUtils.setLongBit(5);
	public static final long ORE_COPPER = BitUtils.setLongBit(6);
	//
	public static final long WOOD_ORRIAN = BitUtils.setLongBit(0);
	public static final long WOOD_ANCIENT = BitUtils.setLongBit(1);
	public static final long WOOD_BAOBA = BitUtils.setLongBit(2);
	public static final long WOOD_REDOAK = BitUtils.setLongBit(3);
	public static final long WOOD_CYPRESS = BitUtils.setLongBit(4);
	public static final long WOOD_BANYAN = BitUtils.setLongBit(5);
	public static final long WOOD_INGLEWOOD = BitUtils.setLongBit(6);
	public static final long WOOD_PINE = BitUtils.setLongBit(7);
	public static final long WOOD_FIR = BitUtils.setLongBit(8);
	public static final long WOOD_TUKAWA = BitUtils.setLongBit(9);
	public static final long WOOD_SNOWCHERRY = BitUtils.setLongBit(10);
	public static final long WOOD_MIMOSA = BitUtils.setLongBit(11);
	public static final long WOOD_GUMMO = BitUtils.setLongBit(12);
	public static final long WOOD_ASPEN = BitUtils.setLongBit(13);
	public static final long WOOD_EKKU = BitUtils.setLongBit(14);
	public static final long WOOD_KERTCH = BitUtils.setLongBit(15);
	//
	public static final long PLANT_BLACK_CROCUS = BitUtils.setLongBit(0);
	public static final long PLANT_GHOST_PEPPER = BitUtils.setLongBit(1);
	public static final long PLANT_LOTUS = BitUtils.setLongBit(2);
	public static final long PLANT_OMNOMBERRIES = BitUtils.setLongBit(3);
	public static final long PLANT_ORRIAN_TRUFFLE = BitUtils.setLongBit(4);
	public static final long PLANT_SEAWEED = BitUtils.setLongBit(5);
	public static final long PLANT_SNOW_TRUFFLE = BitUtils.setLongBit(6);
	public static final long PLANT_ARTICHOKE = BitUtils.setLongBit(7);
	public static final long PLANT_ASPARAGUS = BitUtils.setLongBit(8);
	public static final long PLANT_BLOOMING_PASSIFLORA = BitUtils.setLongBit(9);
	public static final long PLANT_BUTTERNUT_SQUASH = BitUtils.setLongBit(10);
	public static final long PLANT_CAYENNE_PEPPER = BitUtils.setLongBit(11);
	public static final long PLANT_HERB_PATCH = BitUtils.setLongBit(12);
	public static final long PLANT_LEEKS = BitUtils.setLongBit(13);
	public static final long PLANT_PASSIFLORA = BitUtils.setLongBit(14);
	public static final long PLANT_RASPBERRIES = BitUtils.setLongBit(15);
	public static final long PLANT_VERDANT_HERBS = BitUtils.setLongBit(16);
	public static final long PLANT_WINTER_ROOT_VEGETABLES = BitUtils.setLongBit(17);
	public static final long PLANT_BLACKBERRIES = BitUtils.setLongBit(18);
	public static final long PLANT_CAULIFLOWER = BitUtils.setLongBit(19);
	public static final long PLANT_CORAL = BitUtils.setLongBit(20);
	public static final long PLANT_MATURE_HERBS = BitUtils.setLongBit(21);
	public static final long PLANT_PORTOBELLO_MUSHROOMS = BitUtils.setLongBit(22);
	public static final long PLANT_SCALLIONS = BitUtils.setLongBit(23);
	public static final long PLANT_SUGAR_PUMPKIN = BitUtils.setLongBit(24);
	public static final long PLANT_VARIEGATED_TAPROOTS = BitUtils.setLongBit(25);
	public static final long PLANT_CABBAGE = BitUtils.setLongBit(26);
	public static final long PLANT_GRAPES = BitUtils.setLongBit(27);
	public static final long PLANT_KALE = BitUtils.setLongBit(28);
	public static final long PLANT_ROOT_VEGETABLES = BitUtils.setLongBit(29);
	public static final long PLANT_VARIED_MUSHROOMS = BitUtils.setLongBit(30);
	public static final long PLANT_YOUNG_HERBS = BitUtils.setLongBit(31);
	public static final long PLANT_ZUCCHINI = BitUtils.setLongBit(32);
	public static final long PLANT_CLAM = BitUtils.setLongBit(33);
	public static final long PLANT_HERB_SPROUTS = BitUtils.setLongBit(34);
	public static final long PLANT_SPINACH = BitUtils.setLongBit(35);
	public static final long PLANT_STRAWBERRY_PATCH = BitUtils.setLongBit(36);
	public static final long PLANT_TAPROOTS = BitUtils.setLongBit(37);
	public static final long PLANT_BLUEBERRY_BUSH = BitUtils.setLongBit(38);
	public static final long PLANT_BUTTON_MUSHROOMS = BitUtils.setLongBit(39);
	public static final long PLANT_CARROTS = BitUtils.setLongBit(40);
	public static final long PLANT_HERB_PATCH_LOW = BitUtils.setLongBit(41);
	public static final long PLANT_HERB_SEEDLINGS = BitUtils.setLongBit(42);
	public static final long PLANT_LETTUCE = BitUtils.setLongBit(43);
	public static final long PLANT_ONIONS = BitUtils.setLongBit(44);
	public static final long PLANT_POTATO = BitUtils.setLongBit(45);

	public static final long OTHER_HEART = BitUtils.setLongBit(0);
	public static final long OTHER_WAYPOINT = BitUtils.setLongBit(1);
	public static final long OTHER_POI = BitUtils.setLongBit(2);
	public static final long OTHER_SKILL_CHALLENGE = BitUtils.setLongBit(3);
	public static final long OTHER_VISTA = BitUtils.setLongBit(4);
	public static final long OTHER_UNLOCK = BitUtils.setLongBit(5);
	public static final long OTHER_CHEST = BitUtils.setLongBit(6);
	public static final long OTHER_GUILD_BOUNTY = BitUtils.setLongBit(7);
	public static final long OTHER_MAP_NAME = BitUtils.setLongBit(8);

	private static final FilterModel INSTANCE = new FilterModel();

	public static final FilterModel getInstance() {
		return INSTANCE;
	}

	private long oreFilter;
	private long woodFilter;
	private long plantFilter;
	private long otherFilter;

	private DefaultTreeModel availableElementsModel;
	private DefaultTreeModel selectedElementsModel;

	private Map<WPSubType, Long> filterByWPSubType = new HashMap<WPSubType, Long>();

	private FilterModel() {
		super();

		initAvailableElementsModel();
		initSelectedElementsModel();
		initFilterByWPSubType();

		setOreFilter(ORE_ORICHALCUM | ORE_MITHRIL | ORE_PLATINUM);
		setWoodFilter(WOOD_ORRIAN | WOOD_ANCIENT | WOOD_BAOBA | WOOD_REDOAK | WOOD_CYPRESS | WOOD_BANYAN
				| WOOD_INGLEWOOD | WOOD_PINE);
		setPlantFilter(PLANT_BLACK_CROCUS | PLANT_GHOST_PEPPER | PLANT_LOTUS | PLANT_OMNOMBERRIES
				| PLANT_ORRIAN_TRUFFLE | PLANT_SEAWEED | PLANT_SNOW_TRUFFLE | PLANT_ARTICHOKE | PLANT_ASPARAGUS
				| PLANT_BLOOMING_PASSIFLORA | PLANT_BUTTERNUT_SQUASH | PLANT_CAYENNE_PEPPER | PLANT_HERB_PATCH
				| PLANT_LEEKS | PLANT_PASSIFLORA | PLANT_RASPBERRIES | PLANT_VERDANT_HERBS
				| PLANT_WINTER_ROOT_VEGETABLES | PLANT_BLACKBERRIES | PLANT_CAULIFLOWER | PLANT_CORAL
				| PLANT_MATURE_HERBS | PLANT_PORTOBELLO_MUSHROOMS | PLANT_SCALLIONS | PLANT_SCALLIONS
				| PLANT_SUGAR_PUMPKIN | PLANT_VARIEGATED_TAPROOTS);
		setOtherFilter(OTHER_WAYPOINT | OTHER_CHEST | OTHER_MAP_NAME);
	}

	public void addItems(List<DefaultMutableTreeNode> selectedNodes) {
		LOG.debug("selectedNodes: {}", selectedNodes);
		for (DefaultMutableTreeNode node : selectedNodes) {
			// Suche Parent im rechten Baum
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
			TreeObject userObject = (TreeObject) parent.getUserObject();
			DefaultMutableTreeNode group = findGroup((DefaultMutableTreeNode) this.selectedElementsModel.getRoot(),
					userObject.value);
			if (group == null) {
				LOG.warn("Gruppe mit der ID " + userObject.value + " im rechten Baum nicht gefunden!");
			} else {
				group.add(node);
			}
		}
		sortTree((DefaultMutableTreeNode) this.selectedElementsModel.getRoot());
		this.availableElementsModel.reload();
		this.selectedElementsModel.reload();
		calcOreFilter();
		calcWoodFilter();
		calcPlantFilter();
		calcOtherFilter();
		if (!isChanging()) {
			syncViews();
		}
	}

	public void addOreFilter(WPSubType wpSubType) {
		setOreFilter(this.oreFilter | getFilterByWPSubType(wpSubType));
	}

	public void addOtherFilter(WPSubType wpSubType) {
		setOtherFilter(this.otherFilter | getFilterByWPSubType(wpSubType));
	}

	public void addPlantFilter(WPSubType wpSubType) {
		setPlantFilter(this.plantFilter | getFilterByWPSubType(wpSubType));
	}

	public void addWoodFilter(WPSubType wpSubType) {
		setWoodFilter(this.woodFilter | getFilterByWPSubType(wpSubType));
	}

	public void fillSelectedLeafs(List<DefaultMutableTreeNode> selectedNodes, DefaultMutableTreeNode node) {
		if (node.isLeaf()) {
			if (!selectedNodes.contains(node)) {
				selectedNodes.add(node);
			}
		} else {
			int childCount = node.getChildCount();
			for (int i = 0; i < childCount; i++) {
				fillSelectedLeafs(selectedNodes, (DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}

	public TreeModel getAvailableElementsModel() {
		return this.availableElementsModel;
	}

	public long getFilterByWPSubType(WPSubType wpSubType) {
		return this.filterByWPSubType.get(wpSubType);
	}

	public long getOreFilter() {
		return this.oreFilter;
	}

	public long getOtherFilter() {
		return this.otherFilter;
	}

	public long getPlantFilter() {
		return this.plantFilter;
	}

	public TreeModel getSelectedElementsModel() {
		return this.selectedElementsModel;
	}

	public long getWoodFilter() {
		return this.woodFilter;
	}

	public void removeItems(List<DefaultMutableTreeNode> selectedNodes) {
		LOG.debug("selectedNodes: {}", selectedNodes);
		for (DefaultMutableTreeNode node : selectedNodes) {
			// Suche Parent im linken Baum
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
			TreeObject userObject = (TreeObject) parent.getUserObject();
			DefaultMutableTreeNode group = findGroup((DefaultMutableTreeNode) this.availableElementsModel.getRoot(),
					userObject.value);
			if (group == null) {
				LOG.warn("Gruppe mit der ID " + userObject.value + " im rechten Baum nicht gefunden!");
			} else {
				group.add(node);
			}
		}
		sortTree((DefaultMutableTreeNode) this.availableElementsModel.getRoot());
		this.selectedElementsModel.reload();
		this.availableElementsModel.reload();
		calcOreFilter();
		calcWoodFilter();
		calcPlantFilter();
		calcOtherFilter();
		if (!isChanging()) {
			syncViews();
		}
	}

	public void setOreFilter(long oreFilter) {
		setInternalFilter(GROUP_ORE, oreFilter);
	}

	public void setOtherFilter(long otherFilter) {
		setInternalFilter(GROUP_OTHER, otherFilter);
	}

	public void setPlantFilter(long plantFilter) {
		setInternalFilter(GROUP_PLANT, plantFilter);
	}

	public void setWoodFilter(long woodFilter) {
		setInternalFilter(GROUP_WOOD, woodFilter);
	}

	private void calcOreFilter() {
		this.oreFilter = 0;
		DefaultMutableTreeNode group = findGroup((DefaultMutableTreeNode) this.selectedElementsModel.getRoot(),
				GROUP_ORE);
		if (group != null) {
			int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				ItemNode childAt = (ItemNode) group.getChildAt(i);
				this.oreFilter |= ((TreeObject) childAt.getUserObject()).value;
			}
		}
	}

	private void calcOtherFilter() {
		this.otherFilter = 0;
		DefaultMutableTreeNode group = findGroup((DefaultMutableTreeNode) this.selectedElementsModel.getRoot(),
				GROUP_OTHER);
		if (group != null) {
			int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				ItemNode childAt = (ItemNode) group.getChildAt(i);
				this.otherFilter |= ((TreeObject) childAt.getUserObject()).value;
			}
		}
	}

	private void calcPlantFilter() {
		this.plantFilter = 0;
		DefaultMutableTreeNode group = findGroup((DefaultMutableTreeNode) this.selectedElementsModel.getRoot(),
				GROUP_PLANT);
		if (group != null) {
			int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				ItemNode childAt = (ItemNode) group.getChildAt(i);
				this.plantFilter |= ((TreeObject) childAt.getUserObject()).value;
			}
		}
	}

	private void calcWoodFilter() {
		this.woodFilter = 0;
		DefaultMutableTreeNode group = findGroup((DefaultMutableTreeNode) this.selectedElementsModel.getRoot(),
				GROUP_WOOD);
		if (group != null) {
			int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				ItemNode childAt = (ItemNode) group.getChildAt(i);
				this.woodFilter |= ((TreeObject) childAt.getUserObject()).value;
			}
		}
	}

	private DefaultMutableTreeNode findGroup(DefaultMutableTreeNode node, long value) {
		TreeObject userObject = (TreeObject) node.getUserObject();
		if (userObject.value == value) {
			return node;
		} else {
			int childCount = node.getChildCount();
			for (int i = 0; i < childCount; i++) {
				DefaultMutableTreeNode childAt = (DefaultMutableTreeNode) node.getChildAt(i);
				DefaultMutableTreeNode findGroup = findGroup(childAt, value);
				if ((findGroup != null) && !findGroup.isLeaf()) {
					return findGroup;
				}
			}
		}
		return null;
	}

	private void initAvailableElementsModel() {
		int gSort = 0;
		int iSort = 0;
		ImageIcon icon = null;

		GroupNode root = new GroupNode(new TreeObject(null, RM.getLabel("filter"), GROUP_ROOT, gSort++));

		icon = IM.getImageIcon(ImageManager.ORE_ICON, ICON_SIZE);
		GroupNode ore = new GroupNode(new TreeObject(icon, RM.getLabel("ore"), GROUP_ORE, gSort++));
		icon = IM.getImageIcon(ImageManager.ORICHALCUM_ICON, ICON_SIZE);
		ItemNode oricalcum = new ItemNode(new TreeObject(icon, RM.getLabel("orichalcumVein"), ORE_ORICHALCUM, iSort++));
		icon = IM.getImageIcon(ImageManager.MITHRIL_ICON, ICON_SIZE);
		ItemNode mithril = new ItemNode(new TreeObject(icon, RM.getLabel("mithrilVein"), ORE_MITHRIL, iSort++));
		icon = IM.getImageIcon(ImageManager.PLATINUM_ICON, ICON_SIZE);
		ItemNode platinum = new ItemNode(new TreeObject(icon, RM.getLabel("platinumVein"), ORE_PLATINUM, iSort++));
		icon = IM.getImageIcon(ImageManager.GOLD_ICON, ICON_SIZE);
		ItemNode gold = new ItemNode(new TreeObject(icon, RM.getLabel("goldVein"), ORE_GOLD, iSort++));
		icon = IM.getImageIcon(ImageManager.SILVER_ICON, ICON_SIZE);
		ItemNode silver = new ItemNode(new TreeObject(icon, RM.getLabel("silverVein"), ORE_SILVER, iSort++));
		icon = IM.getImageIcon(ImageManager.IRON_ICON, ICON_SIZE);
		ItemNode iron = new ItemNode(new TreeObject(icon, RM.getLabel("ironVein"), ORE_IRON, iSort++));
		icon = IM.getImageIcon(ImageManager.COPPER_ICON, ICON_SIZE);
		ItemNode copper = new ItemNode(new TreeObject(icon, RM.getLabel("copperVein"), ORE_COPPER, iSort++));

		iSort = 0;
		GroupNode wood = new GroupNode(new TreeObject(IM.getImageIcon(ImageManager.WOOD_ICON, ICON_SIZE),
				RM.getLabel("wood"), GROUP_WOOD, gSort++));
		icon = IM.getImageIcon(ImageManager.ANCIENT_WOOD_ICON, ICON_SIZE);
		ItemNode orrian = new ItemNode(new TreeObject(icon, RM.getLabel("orrian"), WOOD_ORRIAN, iSort++));
		icon = IM.getImageIcon(ImageManager.ANCIENT_WOOD_ICON, ICON_SIZE);
		ItemNode ancient = new ItemNode(new TreeObject(icon, RM.getLabel("ancient"), WOOD_ANCIENT, iSort++));
		icon = IM.getImageIcon(ImageManager.ELDER_WOOD_ICON, ICON_SIZE);
		ItemNode baoba = new ItemNode(new TreeObject(icon, RM.getLabel("baoba"), WOOD_BAOBA, iSort++));
		icon = IM.getImageIcon(ImageManager.ELDER_WOOD_ICON, ICON_SIZE);
		ItemNode redOak = new ItemNode(new TreeObject(icon, RM.getLabel("redOak"), WOOD_REDOAK, iSort++));
		icon = IM.getImageIcon(ImageManager.ELDER_WOOD_ICON, ICON_SIZE);
		ItemNode cypress = new ItemNode(new TreeObject(icon, RM.getLabel("cypress"), WOOD_CYPRESS, iSort++));
		icon = IM.getImageIcon(ImageManager.HARD_WOOD_ICON, ICON_SIZE);
		ItemNode banyan = new ItemNode(new TreeObject(icon, RM.getLabel("banyan"), WOOD_BANYAN, iSort++));
		icon = IM.getImageIcon(ImageManager.HARD_WOOD_ICON, ICON_SIZE);
		ItemNode inglewood = new ItemNode(new TreeObject(icon, RM.getLabel("inglewood"), WOOD_INGLEWOOD, iSort++));
		icon = IM.getImageIcon(ImageManager.HARD_WOOD_ICON, ICON_SIZE);
		ItemNode pine = new ItemNode(new TreeObject(icon, RM.getLabel("pine"), WOOD_PINE, iSort++));
		icon = IM.getImageIcon(ImageManager.SEASONED_WOOD_ICON, ICON_SIZE);
		ItemNode fir = new ItemNode(new TreeObject(icon, RM.getLabel("fir"), WOOD_FIR, iSort++));
		icon = IM.getImageIcon(ImageManager.SEASONED_WOOD_ICON, ICON_SIZE);
		ItemNode tukawa = new ItemNode(new TreeObject(icon, RM.getLabel("tukawa"), WOOD_TUKAWA, iSort++));
		icon = IM.getImageIcon(ImageManager.SOFT_WOOD_ICON, ICON_SIZE);
		ItemNode snowCherry = new ItemNode(new TreeObject(icon, RM.getLabel("snowCherry"), WOOD_SNOWCHERRY, iSort++));
		icon = IM.getImageIcon(ImageManager.SOFT_WOOD_ICON, ICON_SIZE);
		ItemNode mimosa = new ItemNode(new TreeObject(icon, RM.getLabel("mimosa"), WOOD_MIMOSA, iSort++));
		icon = IM.getImageIcon(ImageManager.SOFT_WOOD_ICON, ICON_SIZE);
		ItemNode gummo = new ItemNode(new TreeObject(icon, RM.getLabel("gummo"), WOOD_GUMMO, iSort++));
		icon = IM.getImageIcon(ImageManager.GREEN_WOOD_ICON, ICON_SIZE);
		ItemNode aspen = new ItemNode(new TreeObject(icon, RM.getLabel("aspen"), WOOD_ASPEN, iSort++));
		icon = IM.getImageIcon(ImageManager.GREEN_WOOD_ICON, ICON_SIZE);
		ItemNode ekku = new ItemNode(new TreeObject(icon, RM.getLabel("ekku"), WOOD_EKKU, iSort++));
		icon = IM.getImageIcon(ImageManager.GREEN_WOOD_ICON, ICON_SIZE);
		ItemNode kertch = new ItemNode(new TreeObject(icon, RM.getLabel("kertch"), WOOD_KERTCH, iSort++));

		iSort = 0;
		GroupNode plant = new GroupNode(new TreeObject(IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE),
				RM.getLabel("plant"), GROUP_PLANT, gSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_SAFFRON_THREAD_ICON, ICON_SIZE);
		ItemNode blackCrocus = new ItemNode(new TreeObject(icon, RM.getLabel("blackCrocus"), PLANT_BLACK_CROCUS,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_GHOST_PEPPER_ICON, ICON_SIZE);
		ItemNode ghostPepper = new ItemNode(new TreeObject(icon, RM.getLabel("ghostPepper"), PLANT_GHOST_PEPPER,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_LOTUS_ROOT_ICON, ICON_SIZE);
		ItemNode lotus = new ItemNode(new TreeObject(icon, RM.getLabel("lotus"), PLANT_LOTUS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_OMNOMBERRY_ICON, ICON_SIZE);
		ItemNode omnomberries = new ItemNode(new TreeObject(icon, RM.getLabel("omnomberries"), PLANT_OMNOMBERRIES,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ORRIAN_TRUFFLE_ICON, ICON_SIZE);
		ItemNode orrianTruffle = new ItemNode(new TreeObject(icon, RM.getLabel("orrianTruffle"), PLANT_ORRIAN_TRUFFLE,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_SEAWEED_ICON, ICON_SIZE);
		ItemNode seaweed = new ItemNode(new TreeObject(icon, RM.getLabel("seaweed"), PLANT_SEAWEED, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_SNOW_TRUFFLE_ICON, ICON_SIZE);
		ItemNode snowTruffle = new ItemNode(new TreeObject(icon, RM.getLabel("snowTruffle"), PLANT_SNOW_TRUFFLE,
				iSort++));

		icon = IM.getImageIcon(ImageManager.PLANT_ARTICHOKE_ICON, ICON_SIZE);
		ItemNode artichoke = new ItemNode(new TreeObject(icon, RM.getLabel("artichoke"), PLANT_ARTICHOKE, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ASPARAGUS_SPEAR_ICON, ICON_SIZE);
		ItemNode asparagus = new ItemNode(new TreeObject(icon, RM.getLabel("asparagus"), PLANT_ASPARAGUS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_PASSION_FLOWER_ICON, ICON_SIZE);
		ItemNode bloomingPassiflora = new ItemNode(new TreeObject(icon, RM.getLabel("bloomingPassiflora"),
				PLANT_BLOOMING_PASSIFLORA, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_BUTTERNUT_SQUASH_ICON, ICON_SIZE);
		ItemNode butternutSquash = new ItemNode(new TreeObject(icon, RM.getLabel("butternutSquash"),
				PLANT_BUTTERNUT_SQUASH, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_CAYENNE_PEPPER_ICON, ICON_SIZE);
		ItemNode cayennePepper = new ItemNode(new TreeObject(icon, RM.getLabel("cayennePepper"), PLANT_CAYENNE_PEPPER,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode herbPatch = new ItemNode(new TreeObject(icon, RM.getLabel("herbPatch"), PLANT_HERB_PATCH, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_LEEK_ICON, ICON_SIZE);
		ItemNode leaks = new ItemNode(new TreeObject(icon, RM.getLabel("leaks"), PLANT_LEEKS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_PASSION_FLOWER_ICON, ICON_SIZE);
		ItemNode passiflora = new ItemNode(new TreeObject(icon, RM.getLabel("passiflora"), PLANT_PASSIFLORA, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_RASPBERRY_ICON, ICON_SIZE);
		ItemNode raspberries = new ItemNode(
				new TreeObject(icon, RM.getLabel("raspberries"), PLANT_RASPBERRIES, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode verdantHerbs = new ItemNode(new TreeObject(icon, RM.getLabel("verdantHerbs"), PLANT_VERDANT_HERBS,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode winterRootVegetables = new ItemNode(new TreeObject(icon, RM.getLabel("winterRootVegetables"),
				PLANT_WINTER_ROOT_VEGETABLES, iSort++));

		icon = IM.getImageIcon(ImageManager.PLANT_BLACKBERRY_ICON, ICON_SIZE);
		ItemNode blackberries = new ItemNode(new TreeObject(icon, RM.getLabel("blackberries"), PLANT_BLACKBERRIES,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_HEAD_OF_CAULIFLOWER_ICON, ICON_SIZE);
		ItemNode cauliflower = new ItemNode(
				new TreeObject(icon, RM.getLabel("cauliflower"), PLANT_CAULIFLOWER, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_CORAL_CHUNK_ICON, ICON_SIZE);
		ItemNode coral = new ItemNode(new TreeObject(icon, RM.getLabel("coral"), PLANT_CORAL, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode matureHerbs = new ItemNode(new TreeObject(icon, RM.getLabel("matureHerbs"), PLANT_MATURE_HERBS,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_PORTOBELLO_MUSHROOM_ICON, ICON_SIZE);
		ItemNode portobelloMushrooms = new ItemNode(new TreeObject(icon, RM.getLabel("portobelloMushrooms"),
				PLANT_PORTOBELLO_MUSHROOMS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ONION_ICON, ICON_SIZE);
		ItemNode scallions = new ItemNode(new TreeObject(icon, RM.getLabel("scallions"), PLANT_SCALLIONS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_SUGAR_PUMPKIN_ICON, ICON_SIZE);
		ItemNode sugarPumpkin = new ItemNode(new TreeObject(icon, RM.getLabel("sugarPumpkin"), PLANT_SUGAR_PUMPKIN,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode variegatedTaproots = new ItemNode(new TreeObject(icon, RM.getLabel("variegatedTaproots"),
				PLANT_VARIEGATED_TAPROOTS, iSort++));

		icon = IM.getImageIcon(ImageManager.PLANT_HEAD_OF_CABBAGE_ICON, ICON_SIZE);
		ItemNode cabbage = new ItemNode(new TreeObject(icon, RM.getLabel("cabbage"), PLANT_CABBAGE, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_GRAPE_ICON, ICON_SIZE);
		ItemNode grapes = new ItemNode(new TreeObject(icon, RM.getLabel("grapes"), PLANT_GRAPES, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_KALE_LEAF_ICON, ICON_SIZE);
		ItemNode kale = new ItemNode(new TreeObject(icon, RM.getLabel("kale"), PLANT_KALE, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode rootVegetables = new ItemNode(new TreeObject(icon, RM.getLabel("rootVegetables"),
				PLANT_ROOT_VEGETABLES, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_MUSHROOM_ICON, ICON_SIZE);
		ItemNode variedMushrooms = new ItemNode(new TreeObject(icon, RM.getLabel("variedMushrooms"),
				PLANT_VARIED_MUSHROOMS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode youngHerbs = new ItemNode(new TreeObject(icon, RM.getLabel("youngHerbs"), PLANT_YOUNG_HERBS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ZUCCHINI_ICON, ICON_SIZE);
		ItemNode zucchini = new ItemNode(new TreeObject(icon, RM.getLabel("zucchini"), PLANT_ZUCCHINI, iSort++));

		icon = IM.getImageIcon(ImageManager.PLANT_CLAM_ICON, ICON_SIZE);
		ItemNode clam = new ItemNode(new TreeObject(icon, RM.getLabel("clam"), PLANT_CLAM, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode herbSprouts = new ItemNode(new TreeObject(icon, RM.getLabel("herbSprouts"), PLANT_HERB_SPROUTS,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_SPINACH_LEAF_ICON, ICON_SIZE);
		ItemNode spinach = new ItemNode(new TreeObject(icon, RM.getLabel("spinach"), PLANT_SPINACH, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_STRAWBERRY_ICON, ICON_SIZE);
		ItemNode strawberryPatch = new ItemNode(new TreeObject(icon, RM.getLabel("strawberryPatch"),
				PLANT_STRAWBERRY_PATCH, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode taproots = new ItemNode(new TreeObject(icon, RM.getLabel("taproots"), PLANT_TAPROOTS, iSort++));

		icon = IM.getImageIcon(ImageManager.PLANT_BLUEBERRY_ICON, ICON_SIZE);
		ItemNode blueberryBush = new ItemNode(new TreeObject(icon, RM.getLabel("blueberryBush"), PLANT_BLUEBERRY_BUSH,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_MUSHROOM_ICON, ICON_SIZE);
		ItemNode buttonMushrooms = new ItemNode(new TreeObject(icon, RM.getLabel("buttonMushrooms"),
				PLANT_BUTTON_MUSHROOMS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_CARROT_ICON, ICON_SIZE);
		ItemNode carrots = new ItemNode(new TreeObject(icon, RM.getLabel("carrots"), PLANT_CARROTS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode herbPatchLowLevel = new ItemNode(new TreeObject(icon, RM.getLabel("herbPatchLowLevel"),
				PLANT_HERB_PATCH_LOW, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE);
		ItemNode herbSeedlings = new ItemNode(new TreeObject(icon, RM.getLabel("herbSeedlings"), PLANT_HERB_SEEDLINGS,
				iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_HEAD_OF_LETTUCE_ICON, ICON_SIZE);
		ItemNode lettuce = new ItemNode(new TreeObject(icon, RM.getLabel("lettuce"), PLANT_LETTUCE, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_ONION_ICON, ICON_SIZE);
		ItemNode onions = new ItemNode(new TreeObject(icon, RM.getLabel("onions"), PLANT_ONIONS, iSort++));
		icon = IM.getImageIcon(ImageManager.PLANT_POTATO_ICON, ICON_SIZE);
		ItemNode potato = new ItemNode(new TreeObject(icon, RM.getLabel("potato"), PLANT_POTATO, iSort++));

		iSort = 0;
		GroupNode other = new GroupNode(new TreeObject(IM.getImageIcon(ImageManager.BLUE_BALL_ICON, ICON_SIZE),
				RM.getLabel("other"), GROUP_OTHER, gSort++));
		icon = IM.getImageIcon(ImageManager.HEART_ICON, ICON_SIZE);
		ItemNode heart = new ItemNode(new TreeObject(icon, RM.getLabel("heart"), OTHER_HEART, iSort++));
		icon = IM.getImageIcon(ImageManager.WAYPOINT_ICON, ICON_SIZE);
		ItemNode waypoint = new ItemNode(new TreeObject(icon, RM.getLabel("waypoint"), OTHER_WAYPOINT, iSort++));
		icon = IM.getImageIcon(ImageManager.POI_ICON, ICON_SIZE);
		ItemNode poi = new ItemNode(new TreeObject(icon, RM.getLabel("poi"), OTHER_POI, iSort++));
		icon = IM.getImageIcon(ImageManager.SKILL_CHALLEGENE_ICON, ICON_SIZE);
		ItemNode skillChallenge = new ItemNode(new TreeObject(icon, RM.getLabel("skillChallenge"),
				OTHER_SKILL_CHALLENGE, iSort++));
		icon = IM.getImageIcon(ImageManager.VISTA_ICON, ICON_SIZE);
		ItemNode vista = new ItemNode(new TreeObject(icon, RM.getLabel("vista"), OTHER_VISTA, iSort++));
		icon = IM.getImageIcon(ImageManager.CHEST_ICON, ICON_SIZE);
		ItemNode chest = new ItemNode(new TreeObject(icon, RM.getLabel("chest"), OTHER_CHEST, iSort++));
		icon = IM.getImageIcon(ImageManager.GUILD_BOUNTY_ICON, ICON_SIZE);
		ItemNode guildBounty = new ItemNode(new TreeObject(icon, RM.getLabel("guildBounty"), OTHER_GUILD_BOUNTY,
				iSort++));
		icon = IM.getImageIcon(ImageManager.UNLOCK_ICON, ICON_SIZE);
		ItemNode dungeon = new ItemNode(new TreeObject(icon, RM.getLabel("dungeon"), OTHER_UNLOCK, iSort++));
		icon = IM.getImageIcon(ImageManager.EMPTY_ICON, ICON_SIZE);
		ItemNode mapName = new ItemNode(new TreeObject(icon, RM.getLabel("mapName"), OTHER_MAP_NAME, iSort++));

		root.add(ore);
		ore.add(oricalcum);
		ore.add(mithril);
		ore.add(platinum);
		ore.add(gold);
		ore.add(silver);
		ore.add(iron);
		ore.add(copper);

		root.add(wood);
		wood.add(orrian);
		wood.add(ancient);
		wood.add(baoba);
		wood.add(redOak);
		wood.add(cypress);
		wood.add(banyan);
		wood.add(inglewood);
		wood.add(pine);
		wood.add(fir);
		wood.add(tukawa);
		wood.add(snowCherry);
		wood.add(mimosa);
		wood.add(gummo);
		wood.add(aspen);
		wood.add(ekku);
		wood.add(kertch);

		root.add(plant);
		plant.add(blackCrocus);
		plant.add(ghostPepper);
		plant.add(lotus);
		plant.add(omnomberries);
		plant.add(orrianTruffle);
		plant.add(seaweed);
		plant.add(snowTruffle);
		plant.add(artichoke);
		plant.add(asparagus);
		plant.add(bloomingPassiflora);
		plant.add(butternutSquash);
		plant.add(cayennePepper);
		plant.add(herbPatch);
		plant.add(leaks);
		plant.add(passiflora);
		plant.add(raspberries);
		plant.add(verdantHerbs);
		plant.add(winterRootVegetables);
		plant.add(blackberries);
		plant.add(cauliflower);
		plant.add(coral);
		plant.add(matureHerbs);
		plant.add(portobelloMushrooms);
		plant.add(scallions);
		plant.add(sugarPumpkin);
		plant.add(variegatedTaproots);
		plant.add(cabbage);
		plant.add(grapes);
		plant.add(kale);
		plant.add(rootVegetables);
		plant.add(variedMushrooms);
		plant.add(youngHerbs);
		plant.add(zucchini);
		plant.add(clam);
		plant.add(herbSprouts);
		plant.add(spinach);
		plant.add(strawberryPatch);
		plant.add(taproots);
		plant.add(blueberryBush);
		plant.add(buttonMushrooms);
		plant.add(carrots);
		plant.add(herbPatchLowLevel);
		plant.add(herbSeedlings);
		plant.add(lettuce);
		plant.add(onions);
		plant.add(potato);

		root.add(other);
		other.add(heart);
		other.add(waypoint);
		other.add(poi);
		other.add(skillChallenge);
		other.add(vista);
		other.add(chest);
		other.add(guildBounty);
		other.add(dungeon);
		other.add(mapName);

		this.availableElementsModel = new DefaultTreeModel(root);
	}

	private void initFilterByWPSubType() {
		this.filterByWPSubType.put(WPSubType.ORICHALCUM, ORE_ORICHALCUM);
		this.filterByWPSubType.put(WPSubType.MITHRIL, ORE_MITHRIL);
		this.filterByWPSubType.put(WPSubType.PLATINUM, ORE_PLATINUM);
		this.filterByWPSubType.put(WPSubType.GOLD, ORE_GOLD);
		this.filterByWPSubType.put(WPSubType.SILVER, ORE_SILVER);
		this.filterByWPSubType.put(WPSubType.IRON, ORE_IRON);
		this.filterByWPSubType.put(WPSubType.COPPER, ORE_COPPER);

		this.filterByWPSubType.put(WPSubType.ORRIAN, WOOD_ORRIAN);
		this.filterByWPSubType.put(WPSubType.ANCIENT, WOOD_ANCIENT);
		this.filterByWPSubType.put(WPSubType.BAOBA, WOOD_BAOBA);
		this.filterByWPSubType.put(WPSubType.REDOAK, WOOD_REDOAK);
		this.filterByWPSubType.put(WPSubType.CYPRESS, WOOD_CYPRESS);
		this.filterByWPSubType.put(WPSubType.BANYAN, WOOD_BANYAN);
		this.filterByWPSubType.put(WPSubType.INGLEWOOD, WOOD_INGLEWOOD);
		this.filterByWPSubType.put(WPSubType.PINE, WOOD_PINE);
		this.filterByWPSubType.put(WPSubType.FIR, WOOD_FIR);
		this.filterByWPSubType.put(WPSubType.TUKAWA, WOOD_TUKAWA);
		this.filterByWPSubType.put(WPSubType.SNOWCHERRY, WOOD_SNOWCHERRY);
		this.filterByWPSubType.put(WPSubType.MIMOSA, WOOD_MIMOSA);
		this.filterByWPSubType.put(WPSubType.GUMMO, WOOD_GUMMO);
		this.filterByWPSubType.put(WPSubType.ASPEN, WOOD_ASPEN);
		this.filterByWPSubType.put(WPSubType.EKKU, WOOD_EKKU);
		this.filterByWPSubType.put(WPSubType.KERTCH, WOOD_KERTCH);

		this.filterByWPSubType.put(WPSubType.BLACK_CROCUS, PLANT_BLACK_CROCUS);
		this.filterByWPSubType.put(WPSubType.GHOST_PEPPER, PLANT_GHOST_PEPPER);
		this.filterByWPSubType.put(WPSubType.LOTUS, PLANT_LOTUS);
		this.filterByWPSubType.put(WPSubType.OMNOMBERRIES, PLANT_OMNOMBERRIES);
		this.filterByWPSubType.put(WPSubType.ORRIAN_TRUFFLE, PLANT_ORRIAN_TRUFFLE);
		this.filterByWPSubType.put(WPSubType.SEAWEED, PLANT_SEAWEED);
		this.filterByWPSubType.put(WPSubType.SNOW_TRUFFLE, PLANT_SNOW_TRUFFLE);

		this.filterByWPSubType.put(WPSubType.ARTICHOKE, PLANT_ARTICHOKE);
		this.filterByWPSubType.put(WPSubType.ASPARAGUS, PLANT_ASPARAGUS);
		this.filterByWPSubType.put(WPSubType.BLOOMING_PASSIFLORA, PLANT_BLOOMING_PASSIFLORA);
		this.filterByWPSubType.put(WPSubType.BUTTERNUT_SQUASH, PLANT_BUTTERNUT_SQUASH);
		this.filterByWPSubType.put(WPSubType.CAYENNE_PEPPER, PLANT_CAYENNE_PEPPER);
		this.filterByWPSubType.put(WPSubType.HERB_PATCH, PLANT_HERB_PATCH);
		this.filterByWPSubType.put(WPSubType.LEEKS, PLANT_LEEKS);
		this.filterByWPSubType.put(WPSubType.PASSIFLORA, PLANT_PASSIFLORA);
		this.filterByWPSubType.put(WPSubType.RASPBERRIES, PLANT_RASPBERRIES);
		this.filterByWPSubType.put(WPSubType.VERDANT_HERBS, PLANT_VERDANT_HERBS);
		this.filterByWPSubType.put(WPSubType.WINTER_ROOT_VEGETABLES, PLANT_WINTER_ROOT_VEGETABLES);

		this.filterByWPSubType.put(WPSubType.BLACKBERRIES, PLANT_BLACKBERRIES);
		this.filterByWPSubType.put(WPSubType.CAULIFLOWER, PLANT_CAULIFLOWER);
		this.filterByWPSubType.put(WPSubType.CORAL, PLANT_CORAL);
		this.filterByWPSubType.put(WPSubType.MATURE_HERBS, PLANT_MATURE_HERBS);
		this.filterByWPSubType.put(WPSubType.PORTOBELLO_MUSHROOMS, PLANT_PORTOBELLO_MUSHROOMS);
		this.filterByWPSubType.put(WPSubType.SCALLIONS, PLANT_SCALLIONS);
		this.filterByWPSubType.put(WPSubType.SUGAR_PUMPKIN, PLANT_SUGAR_PUMPKIN);
		this.filterByWPSubType.put(WPSubType.VARIEGATED_TAPROOTS, PLANT_VARIEGATED_TAPROOTS);

		this.filterByWPSubType.put(WPSubType.CABBAGE, PLANT_CABBAGE);
		this.filterByWPSubType.put(WPSubType.GRAPES, PLANT_GRAPES);
		this.filterByWPSubType.put(WPSubType.KALE, PLANT_KALE);
		this.filterByWPSubType.put(WPSubType.ROOT_VEGETABLES, PLANT_ROOT_VEGETABLES);
		this.filterByWPSubType.put(WPSubType.VARIED_MUSHROOMS, PLANT_VARIED_MUSHROOMS);
		this.filterByWPSubType.put(WPSubType.YOUNG_HERBS, PLANT_YOUNG_HERBS);
		this.filterByWPSubType.put(WPSubType.ZUCCHINI, PLANT_ZUCCHINI);

		this.filterByWPSubType.put(WPSubType.CLAM, PLANT_CLAM);
		this.filterByWPSubType.put(WPSubType.HERB_SPROUTS, PLANT_HERB_SPROUTS);
		this.filterByWPSubType.put(WPSubType.SPINACH, PLANT_SPINACH);
		this.filterByWPSubType.put(WPSubType.STRAWBERRY_PATCH, PLANT_STRAWBERRY_PATCH);
		this.filterByWPSubType.put(WPSubType.TAPROOTS, PLANT_TAPROOTS);

		this.filterByWPSubType.put(WPSubType.BLUEBERRY_BUSH, PLANT_BLUEBERRY_BUSH);
		this.filterByWPSubType.put(WPSubType.BUTTON_MUSHROOMS, PLANT_BUTTON_MUSHROOMS);
		this.filterByWPSubType.put(WPSubType.CARROTS, PLANT_CARROTS);
		this.filterByWPSubType.put(WPSubType.HERB_PATCH_LOW, PLANT_HERB_PATCH_LOW);
		this.filterByWPSubType.put(WPSubType.LETTUCE, PLANT_LETTUCE);
		this.filterByWPSubType.put(WPSubType.ONIONS, PLANT_ONIONS);
		this.filterByWPSubType.put(WPSubType.POTATO, PLANT_POTATO);

		this.filterByWPSubType.put(WPSubType.CHEST, OTHER_CHEST);
		this.filterByWPSubType.put(WPSubType.GUILD_BOUNTY, OTHER_GUILD_BOUNTY);
	}

	private void initSelectedElementsModel() {
		int groupSort = 0;
		GroupNode root = new GroupNode(new TreeObject(null, RM.getLabel("filter"), GROUP_ROOT, groupSort++));
		GroupNode ore = new GroupNode(new TreeObject(IM.getImageIcon(ImageManager.ORE_ICON, ICON_SIZE),
				RM.getLabel("ore"), GROUP_ORE, groupSort++));
		GroupNode wood = new GroupNode(new TreeObject(IM.getImageIcon(ImageManager.WOOD_ICON, ICON_SIZE),
				RM.getLabel("wood"), GROUP_WOOD, groupSort++));
		GroupNode plant = new GroupNode(new TreeObject(IM.getImageIcon(ImageManager.PLANT_ICON, ICON_SIZE),
				RM.getLabel("plant"), GROUP_PLANT, groupSort++));
		GroupNode other = new GroupNode(new TreeObject(IM.getImageIcon(ImageManager.BLUE_BALL_ICON, ICON_SIZE),
				RM.getLabel("other"), GROUP_OTHER, groupSort++));

		root.add(ore);
		root.add(wood);
		root.add(plant);
		root.add(other);

		this.selectedElementsModel = new DefaultTreeModel(root);
	}

	private void setInternalFilter(int groupID, long filter) {
		LOG.debug("groupID: {}, filter: {}", groupID, filter);

		// Entferne alle Items aus der rechten Liste
		List<DefaultMutableTreeNode> allSelected = new ArrayList<DefaultMutableTreeNode>();
		DefaultMutableTreeNode group = findGroup((DefaultMutableTreeNode) this.selectedElementsModel.getRoot(), groupID);
		fillSelectedLeafs(allSelected, group);
		removeItems(allSelected);

		switch (groupID) {
			case GROUP_ORE:
				this.oreFilter = filter;
				break;
			case GROUP_WOOD:
				this.woodFilter = filter;
				break;
			case GROUP_PLANT:
				this.plantFilter = filter;
				break;
			case GROUP_OTHER:
				this.otherFilter = filter;
				break;
		}

		// Ermittle nun alle Items aus der linken Liste, die wieder selektiert werden sollen
		group = findGroup((DefaultMutableTreeNode) this.availableElementsModel.getRoot(), groupID);
		List<DefaultMutableTreeNode> selectedNodes = new ArrayList<DefaultMutableTreeNode>();
		int childCount = group.getChildCount();
		for (int i = 0; i < childCount; i++) {
			ItemNode childAt = (ItemNode) group.getChildAt(i);
			TreeObject userObject = (TreeObject) childAt.getUserObject();

			switch (groupID) {
				case GROUP_ORE:
					if ((this.oreFilter & userObject.value) != 0) {
						selectedNodes.add(childAt);
					}
					break;
				case GROUP_WOOD:
					if ((this.woodFilter & userObject.value) != 0) {
						selectedNodes.add(childAt);
					}
					break;
				case GROUP_PLANT:
					if ((this.plantFilter & userObject.value) != 0) {
						selectedNodes.add(childAt);
					}
					break;
				case GROUP_OTHER:
					if ((this.otherFilter & userObject.value) != 0) {
						selectedNodes.add(childAt);
					}
					break;
			}
		}
		addItems(selectedNodes);
		if (!isChanging()) {
			syncViews();
		}
	}

	private void sort(DefaultMutableTreeNode parent) {
		int n = parent.getChildCount();
		List<DefaultMutableTreeNode> children = new ArrayList<DefaultMutableTreeNode>();
		for (int i = 0; i < n; i++) {
			children.add((DefaultMutableTreeNode) parent.getChildAt(i));
		}
		Collections.sort(children, new Comparator<DefaultMutableTreeNode>() {

			@Override
			public int compare(DefaultMutableTreeNode left, DefaultMutableTreeNode right) {
				if (left.isLeaf() && !right.isLeaf()) {
					return 1;
				} else if (!left.isLeaf() && right.isLeaf()) {
					return -1;
				} else {
					Integer leftSortOrder = ((TreeObject) left.getUserObject()).sortOrder;
					Integer rightSortOrder = ((TreeObject) right.getUserObject()).sortOrder;
					return leftSortOrder.compareTo(rightSortOrder);
				}
			}
		});
		parent.removeAllChildren();
		for (MutableTreeNode node : children) {
			parent.add(node);
		}
	}

	private void sortTree(DefaultMutableTreeNode root) {
		Enumeration<?> e = root.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
			if (!node.isLeaf()) {
				sort(node);
			}
		}
	}
}
