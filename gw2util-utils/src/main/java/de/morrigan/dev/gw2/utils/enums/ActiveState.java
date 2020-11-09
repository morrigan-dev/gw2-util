package de.morrigan.dev.gw2.utils.enums;

/**
 * Stellt Konstanten für den Aktivstatus einer Entity bereit. Eine Entity kann folgende Status annehmen:<br>
 * <ul>
 * <li><b>Aktiv</b>: Der Datensatz ist aktiv. Er wird in der Administration angezeigt und kann bearbeitet werden. In der
 * übrigen Anwendung wird er angezeigt und kann verwendet werden.</li>
 * <li><b>Inaktiv</b>: Der Datensatz ist inaktiv. Er wird in der Administration angezeigt und kann bearbeitet werden. In
 * der übrigen Anwendung wird nicht angezeigt und kann nicht verwendet werden.</li>
 * <li><b>Nicht administrierbar</b>: Der Datensatz ist nicht administrierbar. Er wird in der Administration angezeigt
 * und kann nicht bearbeitet werden. In der übrigen Anwendung wird er angezeigt und kann verwendet werden.</li>
 * <li><b>Historisiert</b>: Der Datensatz ist historisiert. Er wird in der Administration angezeigt und kann nicht
 * bearbeitet werden. In der übrigen Anwendung kann er angezeigt und verwendet werden.</li>
 * <li><b>Gelöscht</b>: Der Datensatz ist gelöscht. Er wird in der Administration angezeigt und kann bearbeitet werden.
 * In der übrigen Anwendung wird der Datensatz angezeigt un dkann nicht verwendet werden.</li>
 * </ul>
 * 
 * @author morrigan
 */
public enum ActiveState {

	ACTIVE("active"), //
	INACTIVE("inactive"), //
	NOT_ADMIN("notAmdin"), //
	HISTORICIZED("historicized"), //
	DELETED("deleted");

	private String labelKey;

	private ActiveState(String labelKey) {
		this.labelKey = labelKey;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
