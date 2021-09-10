package de.morrigan.dev.gw2.entity.interfaces;

import de.morrigan.dev.gw2.utils.enums.ActiveState;

/**
 * Dieses Interface ermöglicht den Zugriff auf den Aktivstatus einer Entity.
 * 
 * @author morrigan
 * @see ActiveState
 */
public interface IActiveStateEntity extends IEntity {

	/**
	 * @return den Aktivstatus dieses Datensatzes.
	 */
	public ActiveState getActiveState();

	/**
	 * Setzt den Aktivstatus dieses Datensatzes.
	 * 
	 * @param activeState einen Aktivstatus.
	 */
	public void setActiveState(ActiveState activeState);
}
