package de.morrigan.dev.gw2.dto.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum WPSubType {
	// Erze
	ORICHALCUM(WPType.ORE, "orichalcumVein"),
	MITHRIL(WPType.ORE, "mithrilVein"),
	PLATINUM(WPType.ORE, "platinumVein"),
	GOLD(WPType.ORE, "goldVein"),
	SILVER(WPType.ORE, "silverVein"),
	IRON(WPType.ORE, "ironVein"),
	COPPER(WPType.ORE, "copperVein"),
	//
	// Hölzer
	ORRIAN(WPType.WOOD, "orrian"),
	ANCIENT(WPType.WOOD, "ancient"),
	BAOBA(WPType.WOOD, "baoba"),
	REDOAK(WPType.WOOD, "redOak"),
	CYPRESS(WPType.WOOD, "cypress"),
	BANYAN(WPType.WOOD, "banyan"),
	INGLEWOOD(WPType.WOOD, "inglewood"),
	PINE(WPType.WOOD, "pine"),
	FIR(WPType.WOOD, "fir"),
	TUKAWA(WPType.WOOD, "tukawa"),
	SNOWCHERRY(WPType.WOOD, "snowCherry"),
	MIMOSA(WPType.WOOD, "mimosa"),
	GUMMO(WPType.WOOD, "gummo"),
	ASPEN(WPType.WOOD, "aspen"),
	EKKU(WPType.WOOD, "ekku"),
	KERTCH(WPType.WOOD, "kertch"),
	//
	// Planzen
	BLACK_CROCUS(WPType.PLANT, "blackCrocus"),
	GHOST_PEPPER(WPType.PLANT, "ghostPepper"),
	LOTUS(WPType.PLANT, "lotus"),
	OMNOMBERRIES(WPType.PLANT, "omnomberries"),
	ORRIAN_TRUFFLE(WPType.PLANT, "orrianTruffle"),
	SEAWEED(WPType.PLANT, "seaweed"),
	SNOW_TRUFFLE(WPType.PLANT, "snowTruffle"),
	//
	ARTICHOKE(WPType.PLANT, "artichoke"),
	ASPARAGUS(WPType.PLANT, "asparagus"),
	BLOOMING_PASSIFLORA(WPType.PLANT, "bloomingPassiflora"),
	BUTTERNUT_SQUASH(WPType.PLANT, "butternutSquash"),
	CAYENNE_PEPPER(WPType.PLANT, "cayennePepper"),
	HERB_PATCH(WPType.PLANT, "herbPatch"),
	LEEKS(WPType.PLANT, "leaks"),
	PASSIFLORA(WPType.PLANT, "passiflora"),
	RASPBERRIES(WPType.PLANT, "raspberries"),
	VERDANT_HERBS(WPType.PLANT, "verdantHerbs"),
	WINTER_ROOT_VEGETABLES(WPType.PLANT, "winterRootVegetables"),
	//
	BLACKBERRIES(WPType.PLANT, "blackberries"),
	CAULIFLOWER(WPType.PLANT, "cauliflower"),
	CORAL(WPType.PLANT, "coral"),
	MATURE_HERBS(WPType.PLANT, "matureHerbs"),
	PORTOBELLO_MUSHROOMS(WPType.PLANT, "portobelloMushrooms"),
	SCALLIONS(WPType.PLANT, "scallions"),
	SUGAR_PUMPKIN(WPType.PLANT, "sugarPumpkin"),
	VARIEGATED_TAPROOTS(WPType.PLANT, "variegatedTaproots"),
	//
	CABBAGE(WPType.PLANT, "cabbage"),
	GRAPES(WPType.PLANT, "grapes"),
	KALE(WPType.PLANT, "kale"),
	ROOT_VEGETABLES(WPType.PLANT, "rootVegetables"),
	VARIED_MUSHROOMS(WPType.PLANT, "variedMushrooms"),
	YOUNG_HERBS(WPType.PLANT, "youngHerbs"),
	ZUCCHINI(WPType.PLANT, "zucchini"),
	//
	CLAM(WPType.PLANT, "clam"),
	HERB_SPROUTS(WPType.PLANT, "herbSprouts"),
	SPINACH(WPType.PLANT, "spinach"),
	STRAWBERRY_PATCH(WPType.PLANT, "strawberryPatch"),
	TAPROOTS(WPType.PLANT, "taproots"),
	//
	BLUEBERRY_BUSH(WPType.PLANT, "blueberryBush"),
	BUTTON_MUSHROOMS(WPType.PLANT, "buttonMushrooms"),
	CARROTS(WPType.PLANT, "carrots"),
	HERB_PATCH_LOW(WPType.PLANT, "herbPatchLowLevel"),
	HERB_SEEDLINGS(WPType.PLANT, "herbSeedlings"),
	LETTUCE(WPType.PLANT, "lettuce"),
	ONIONS(WPType.PLANT, "onions"),
	POTATO(WPType.PLANT, "potato"),

	// Sonstige
	GUILD_BOUNTY(WPType.OTHER, "guildBounty"),
	CHEST(WPType.OTHER, "chest");

	public static List<WPSubType> valuesByWpType(WPType wpType) {
		List<WPSubType> resultList = new ArrayList<>();
		for (WPSubType subType : values()) {
			if (subType.getWpType() == wpType) {
				resultList.add(subType);
			}
		}
		return resultList;
	}

	private String labelKey;

	private WPType wpType;

	private WPSubType(WPType wpType, String labelKey) {
		this.wpType = wpType;
		this.labelKey = labelKey;
	}

	public String getLabelKey() {
		return this.labelKey;
	}

	public WPType getWpType() {
		return this.wpType;
	}
}
