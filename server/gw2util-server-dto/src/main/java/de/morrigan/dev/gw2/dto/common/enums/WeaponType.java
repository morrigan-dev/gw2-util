package de.morrigan.dev.gw2.dto.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum WeaponType {

	UNKNOWN("", "unknown"),
	AXE("Axe", "axe"),
	DAGGER("Dagger", "dagger"),
	FOCUS("Focus", "focus"),
	GREATSWORD("Greatsword", "greatsword"),
	HAMMER("Hammer", "hammer"),
	HARPOON("Harpoon", "harpoon"),
	LARGE_BUNDLE("LargeBundle", "largeBundle"),
	LONGBOW("LongBow", "longbow"),
	MACE("Mace", "mace"),
	PISTOL("Pistol", "pistol"),
	RIFLE("Rifle", "rifle"),
	SCEPTER("Scepter", "scepter"),
	SHIELD("Shield", "shield"),
	SHORTBOW("ShortBow", "shortBow"),
	SMALLBUNDLE("SmallBundle", "smallBundle"),
	SPEARGUN("Speargun", "speargun"),
	STAFF("Staff", "staff"),
	SWORD("Sword", "sword"),
	TORCH("Torch", "torch"),
	TOY("Toy", "toy"),
	TRIDENT("Trident", "trident"),
	TWO_HANDED_TOY("TwoHandedToy", "twoHandedToy"),
	WARHORN("Warhorn", "warhorn");

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(WeaponType.class);

	public static final WeaponType getValueOf(String value) {
		WeaponType result = null;
		for (WeaponType weaponType : values()) {
			if (weaponType.apiValue.equals(value)) {
				result = weaponType;
				break;
			}
		}
		if (result == null) {
			LOG.warn("{} konnte nicht in ein WeaponType umgewandelt werden!", value);
			result = UNKNOWN;
		}
		return result;
	}

	private String apiValue;
	private String labelKey;

	private WeaponType(final String apiValue, final String labelKey) {
		this.apiValue = apiValue;
		this.labelKey = labelKey;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
