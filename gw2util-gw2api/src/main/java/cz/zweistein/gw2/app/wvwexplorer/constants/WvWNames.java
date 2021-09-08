package cz.zweistein.gw2.app.wvwexplorer.constants;

import java.util.HashMap;
import java.util.Map;

import cz.zweistein.gw2.api.dto.enums.WvWMapType;

public class WvWNames {

	public static final Map<Long, String> objective;
	public static final Map<WvWMapType, String> map;

	static {
		objective = new HashMap<Long, String>();
		objective.put(1L, "Overlook");
		objective.put(2L, "Valley");
		objective.put(3L, "Lowlands");
		objective.put(4L, "Golanta Clearing");
		objective.put(5L, "Pangloss Rise");
		objective.put(6L, "Speldan Clearcut");
		objective.put(7L, "Danelon Passage");
		objective.put(8L, "Umberglade Woods");
		objective.put(9L, "Stonemist Castle");
		objective.put(10L, "Rogue’s Quarry");
		objective.put(11L, "Aldon’s Ledge");
		objective.put(12L, "Wildcreek Run");
		objective.put(13L, "Jerrifer’s Slough");
		objective.put(14L, "Klovan Gully");
		objective.put(15L, "Langor Gulch");
		objective.put(16L, "Quentin Lake");
		objective.put(17L, "Mendon’s Gap");
		objective.put(18L, "Anzalias Pass");
		objective.put(19L, "Ogrewatch Cut");
		objective.put(20L, "Veloka Slope");
		objective.put(21L, "Durios Gulch");
		objective.put(22L, "Bravost Escarpment");
		objective.put(23L, "Garrison");
		objective.put(24L, "Champion’s demense");
		objective.put(25L, "Redbriar");
		objective.put(26L, "Greenlake");
		objective.put(27L, "Ascension Bay");
		objective.put(28L, "Dawn’s Eyrie");
		objective.put(29L, "The Spiritholme");
		objective.put(30L, "Woodhaven");
		objective.put(31L, "Askalion Hills");
		objective.put(32L, "Etheron Hills");
		objective.put(33L, "Dreaming Bay");
		objective.put(34L, "Victors’s Lodge");
		objective.put(35L, "Greenbriar");
		objective.put(36L, "Bluelake");
		objective.put(37L, "Garrison");
		objective.put(38L, "Longview");
		objective.put(39L, "The Godsword");
		objective.put(40L, "Cliffside");
		objective.put(41L, "Shadaran Hills");
		objective.put(42L, "Redlake");
		objective.put(43L, "Hero’s Lodge");
		objective.put(44L, "Dreadfall Bay");
		objective.put(45L, "Bluebriar");
		objective.put(46L, "Garrison");
		objective.put(47L, "Sunnyhill");
		objective.put(48L, "Faithleap");
		objective.put(49L, "Bluevale Refuge");
		objective.put(50L, "Bluewater Lowlands");
		objective.put(51L, "Astralholme");
		objective.put(52L, "Arah’s Hope");
		objective.put(53L, "Greenvale Refuge");
		objective.put(54L, "Foghaven");
		objective.put(55L, "Redwater Lowlands");
		objective.put(56L, "The Titanpaw");
		objective.put(57L, "Cragtop");
		objective.put(58L, "Godslore");
		objective.put(59L, "Redvale Refuge");
		objective.put(60L, "Stargrove");
		objective.put(61L, "Greenwater Lowlands");
		
		map = new HashMap<WvWMapType, String>();
		map.put(WvWMapType.BORDERLANDS_BLUE, "Blue Borderlands");
		map.put(WvWMapType.BORDERLANDS_RED, "Red Borderlands");
		map.put(WvWMapType.BORDERLANDS_GREEN, "Green Borderlands");
		map.put(WvWMapType.ETERNAL_BATTLEGROUNDS, "Eternal Battlegrounds");
		
	}

}
