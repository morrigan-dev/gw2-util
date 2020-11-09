package de.morrigan.dev.swing.renderer.table;

import de.morrigan.dev.gw2.utils.enums.ActiveState;

public interface IActiveStateModel {

	/** @return liefert den ActivStatus des Datensatzes in der angegebenen Zeile */
	public ActiveState getActiveState(int rowIndex);
}
