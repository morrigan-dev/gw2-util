package cz.zweistein.gw2.api;

import java.util.Locale;

import org.apache.commons.codec.binary.Base64;

import cz.zweistein.gw2.api.util.Realm;
import cz.zweistein.gw2.api.util.SupportedLanguage;

public class GW2APIUtilities {
	
	/**
	 * Return realm of giver world of wvw match
	 * 
	 * @param worldId
	 * @return
	 */
	public static Realm getRealm(Long worldId) {
		if (worldId == null) {
			return Realm.UNKNOWN;
		} else {
			String worldString = Long.toString(worldId);
			if (worldString.startsWith("1")) {
				return Realm.NA;
			} else if (worldString.startsWith("2")) {
				return Realm.EU;
			} else {
				return Realm.UNKNOWN;
			}
		}
	}

	public static String getItemChatCode(Long itemId) {
		StringBuilder result = new StringBuilder();

		result.append("[&");

		byte[] bytes = new byte[6];

		bytes[0] = 0x02;
		bytes[1] = 0x01;

		if (itemId != null) {
			bytes[2] = (byte) (itemId % 256);
			bytes[3] = (byte) (itemId / 256);
		}

		bytes[4] = 0x00;
		bytes[5] = 0x00;

		result.append(Base64.encodeBase64String(bytes));

		result.append("]");

		return result.toString();
	}
	
	public static SupportedLanguage getSystemLanguage() {
		return SupportedLanguage.resolve(Locale.getDefault().getLanguage());
	}

}
