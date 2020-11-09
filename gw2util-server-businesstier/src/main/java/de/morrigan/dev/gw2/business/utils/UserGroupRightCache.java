package de.morrigan.dev.gw2.business.utils;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.entity.UserGroup;

/**
 * Diese Klasse cached zu jeder Benutzergruppe die Rechteschlüssel der zugewiesenen Rechte, damit eine Rechteprüfung
 * effizienter durchgeführt werden kann.
 * <p>
 * Der Cache wird beim Start des JBosses befüllt.
 * 
 * @author morrigan
 */
public final class UserGroupRightCache {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(UserGroupRightCache.class);

	/** Einzige Instanz dieser Klasse */
	private static final UserGroupRightCache INSTANCE = new UserGroupRightCache();

	/**
	 * @return Liefert die einzige Instanz dieser Klasse.
	 */
	public static UserGroupRightCache getInstance() {
		return INSTANCE;
	}

	/**
	 * In dieser Map werden zu jeder Benutzergruppe ID die Rechteschlüssel der zugewiesenen Rechte abgelegt.
	 * <p>
	 * Stellt sicher, dass Lese und Schreibzugriffe immer erst abgeschlossen sein müssen, um die Threadsicherheit
	 * herzustellen.
	 */
	private final Map<Long, Vector<String>> userGroupRightsMap = new ConcurrentHashMap<Long, Vector<String>>();

	/** Privater Singleton-Konstruktor */
	private UserGroupRightCache() {
		super();
	}

	/**
	 * Liefert alle Rechte, die die übergebene Benutzergruppe besitzt.
	 * 
	 * @param userGroup eine Benutzergruppe. (nullable)
	 * @return alle Rechte, die die Benutzergruppe besitzt oder null, falls die Benutezrgruppe nicht im Cache vorhandne
	 * ist.
	 */
	public Vector<String> getRight(final UserGroup userGroup) {
		return this.userGroupRightsMap.get(userGroup.getId());
	}

	/**
	 * Fügt zu einer Benutzergruppe ein weiteres Recht im Cache hinzu. Ist das Recht bereits vorhanden, so wird nichts
	 * unternommen.
	 * 
	 * @param userGroup eine Benutzergruppe. (not null)
	 * @param rightKey ein Rechteschlüssel. (not null)
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public void putRight(final UserGroup userGroup, final String rightKey) {
		Validate.notNull(userGroup, "Der Parameter (userGroup) darf nicht null sein!");
		Validate.notNull(rightKey, "Der Parameter (rightKey) darf nicht null sein!");
		LOG.debug("userGroup: {}, rightKey: {}", userGroup, rightKey);

		final Vector<String> rightsOfUserGroup = this.userGroupRightsMap.get(userGroup.getId());

		// Es gibt noch keine Rechte zu dieser Benutzergruppe
		if (rightsOfUserGroup == null) {
			final Vector<String> rights = new Vector<String>();
			rights.add(rightKey);
			this.userGroupRightsMap.put(userGroup.getId(), rights);
		}

		// Es gibt bereits Rechte zu dieser Benutzergruppe
		else {
			if (!rightsOfUserGroup.contains(rightKey)) {
				rightsOfUserGroup.add(rightKey);
			}
		}
	}

	/**
	 * Entfernt ein Rechts aus einer Benutzergruppe.
	 * 
	 * @param userGroup eine Benutzergruppe. (not null)
	 * @param rightKey ein Rechteschlüssel. (not null)
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public void removeRight(final UserGroup userGroup, final String rightKey) {
		Validate.notNull(userGroup, "Der Parameter (userGroup) darf nicht null sein!");
		Validate.notNull(rightKey, "Der Parameter (rightKey) darf nicht null sein!");
		LOG.debug("userGroup: {}, rightKey: {}", userGroup, rightKey);

		final Vector<String> rightsOfUserGroup = this.userGroupRightsMap.get(userGroup.getId());

		// Lösche nur, wenn es überhaupt zu der Benutzergruppe Rechte gibt
		if (rightsOfUserGroup != null) {
			rightsOfUserGroup.remove(rightKey);
		}
	}

	/**
	 * Entfernt eine Benutzergruppe aus dem Cache.
	 * 
	 * @param userGroup eine Benutzergruppe. (not null)
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public void removeUserGroup(final UserGroup userGroup) {
		Validate.notNull(userGroup, "Der Parameter (userGroup) darf nicht null sein!");
		LOG.debug("userGroup: {}", userGroup);

		this.userGroupRightsMap.remove(userGroup.getId());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Benutzergruppen-Rechte Cache:\n").append(this.userGroupRightsMap);
		return sb.toString();
	}
}
