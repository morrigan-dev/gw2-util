package cz.zweistein.gw2.app.gearscore;

import java.rmi.RemoteException;
import java.util.Locale;

import cz.zweistein.gw2.api.GW2API;
import cz.zweistein.gw2.api.dao.OfflineJsonDao;
import cz.zweistein.gw2.api.dto.enums.items.ItemClass;
import cz.zweistein.gw2.api.dto.enums.items.ItemType;
import cz.zweistein.gw2.api.dto.enums.items.Rarity;
import cz.zweistein.gw2.api.dto.items.InfixUpgrade;
import cz.zweistein.gw2.api.dto.items.Item;
import cz.zweistein.gw2.api.util.SupportedLanguage;

public class GearScore {

	public static void main(String[] args) throws RemoteException {

		GW2API api = new GW2API();
		api.setDao(new OfflineJsonDao());

		SupportedLanguage language = SupportedLanguage.resolve(Locale.getDefault().getLanguage());
		
		long maxScore = 0;
		Item maxItem = null;

		for (Long itemId : api.getItems()) {

			Item item = api.getItemDetails(itemId, language);

			if (item.getItemType() == ItemClass.ARMOR || item.getItemType() == ItemClass.BACK || item.getItemType() == ItemClass.TRINKET || item.getItemType() == ItemClass.WEAPON || item.getItemType() == ItemClass.UPGRADE_COMPONENT) {

				long score = getScore(item);
				
				if (score > maxScore) {
					maxScore = score;
					maxItem = item;
				}
				
				System.out.println(score + " " + item.getName() + " " + item.toString());

			}

		}
		
		System.out.println("---");
		
		System.out.println(maxScore + " " + maxItem.getName() + " " + maxItem.toString());

	}

	private static long getScore(Item item) {
		
		InfixUpgrade infix = null;
		long defense = 0l;

		double slotWeight = 34;

		switch (item.getItemType()) {
		case ARMOR:
			infix = item.getArmor().getInfixUpgrade();
			defense = item.getArmor().getDefense();
			slotWeight = getSlotWeight(item.getArmor().getArmorType());
			break;
		case BACK:
			infix = item.getBack().getInfixUpgrade();
			slotWeight = 22d;
			break;
		case TRINKET:
			infix = item.getTrinket().getInfixUpgrade();
			slotWeight = getSlotWeight(item.getTrinket().getType());
			break;
		case WEAPON:
			infix = item.getWeapon().getInfixUpgrade();
			defense = item.getWeapon().getDefense();
			slotWeight = getSlotWeight(item.getWeapon().getWeaponType());
			break;
		case UPGRADE_COMPONENT:
			infix = item.getUpgradeComponent().getInfixUpgrade();
			switch (item.getUpgradeComponent().getType()) {
			case GEM:
			case RUNE:
				slotWeight = 25;
				break;
			case SIGIL:
				slotWeight = 30;
				break;

			default:
				break;
			}

			break;
		default:
			break;
		}

		slotWeight = slotWeight / 34d;

		double score = item.getLevel();
		if (infix != null) {
			score = score / (4 - infix.getAttributes().size());
		}
		score = (score * getRarityCoeficient(item.getRarity()));
		score = score * slotWeight;

		return (long) score;
	}

	private static double getSlotWeight(ItemType itemType) {
		int maxStat = 34;

		switch (itemType) {
		case HELM:
		case HELM_AQUATIC:
			maxStat = 45;
			break;
		case SHOULDERS:
		case BOOTS:
			maxStat = 34;
			break;
		case COAT:
			maxStat = 101;
			break;
		case GLOVES:
			maxStat = 34;
			break;
		case LEGGINGS:
		case RING:
			maxStat = 67;
			break;
		case AMULET:
			maxStat = 90;
			break;
		case ACCESSORY:
		case EARRING:
			maxStat = 56;
			break;
		case AXE:
		case DAGGER:
		case FOCUS:
		case MACE:
		case PISTOL:
		case SCEPTER:
		case SWORD:
		case TORCH:
		case WARHORN:
		case SHIELD:
			maxStat = 92;
			break;
		case GREATSWORD:
		case HAMMER:
		case HARPOON:
		case LONGBOW:
		case RIFLE:
		case SHORTBOW:
		case TRIDENT:
		case SPEARGUN:
		case STAFF:
			maxStat = 179;
			break;

		default:
			break;
		}

		return maxStat;

	}

	private static double getRarityCoeficient(Rarity rarity) {

		switch (rarity) {
		case FINE:
			return 1.25d;
		case MASTERWORK:
			return 1.35d;
		case RARE:
			return 1.45d;
		case EXOTIC:
		case LEGENDARY:
			return 1.65d;
		case ASCENDED:
			return 1.75d;
		case BASIC:
		default:
			return 1d;
		}

	}

}
