package de.morrigan.dev.gw2.entity.interfaces;

import java.util.Date;

import de.morrigan.dev.gw2.entity.User;

/**
 * Dieses Interface ermöglicht den Zugriff auf die Auditinformationen eines Datensatzes.<br>
 * Dazu gehören:<br>
 * <ul>
 * <li>Das Erstellungsdatum dieses Datensatzes</li>
 * <li>Der Erstellungsbenutzer dieses Datensatzes</li>
 * <li>Das Änderungsdatum dieses Datensatzes</li>
 * <li>Der Änderungsbenutzer dieses Datensatzes</li>
 * </ul>
 * 
 * @author morrigan
 */
public interface IAuditEntity extends IEntity {

	/**
	 * @return das Erstellungsdatum dieses Datensatzes.
	 */
	public Date getCreateDate();

	/**
	 * @return den Erstellungsbenutzer dieses Datensatzes.
	 */
	public User getCreateUser();

	/**
	 * @return das Änderungsdatum dieses Datensatzes.
	 */
	public Date getUpdateDate();

	/**
	 * @return den Änderungsbenutzer dieses Datensatzes.
	 */
	public User getUpdateUser();

	/**
	 * Setzt das Erstellungsdatum dieses Datensatzes.
	 * 
	 * @param createDate ein Datum.
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Setzt den Erstellungsbenutzer dieses Datensatzes.
	 * 
	 * @param createUser ein Benutzer.
	 */
	public void setCreateUser(User createUser);

	/**
	 * Detzt das Aktualisierungsdatum dieses Datensatzes.
	 * 
	 * @param updateDate ein Datum.
	 */
	public void setUpdateDate(Date updateDate);

	/**
	 * Setzt den Änderungsbenutzer dieses Datensatzes.
	 * 
	 * @param updateUser ein Benutzer.
	 */
	public void setUpdateUser(User updateUser);
}
