package de.morrigan.dev.gw2.business.local.interfaces;

import de.morrigan.dev.gw2.dto.exceptions.ServiceException;

public interface IJobService {

	/**
	 * Ruft von der GW2 API Schnittstelle die Umwandelwerte von Gold zu Edelstein und umgekehrt ab, erstellt einen
	 * Statistikeintrag und speichert diesen in der DB.
	 * 
	 * @throws ServiceException
	 */
	void receiveGemsStatisticValue() throws ServiceException;

	/**
	 * Ruft von der GW2 API Schnittstelle sämtliche IDs aller Items ab.
	 * 
	 * @return ein Array mit Item IDs.
	 * @throws ServiceException
	 */
	long[] receiveItemIds() throws ServiceException;

	/**
	 * Ruft von der GW2 API Schnittstelle zu allen angegebenen Item IDs die Item Details ab und speichert diese in der
	 * DB.
	 * 
	 * @param ids ein Array mit Item IDs
	 * @throws ServiceException
	 */
	void receiveItems(long[] ids) throws ServiceException;

	/**
	 * Ruft von der GW2 API Schnittstelle zu allen angegebenen Rezepte IDs die Rezepte Details ab und speichert diese in
	 * der DB.
	 * 
	 * @param ids ein Array mit Rezepte IDs
	 * @throws ServiceException
	 */
	void receiveRecipes(long[] idsToLoad) throws ServiceException;

	/**
	 * Ruft von der GW2 API Schnittstelle sämtliche IDs aller Rezepte ab.
	 * 
	 * @return ein Array mit Rezepte IDs.
	 * @throws ServiceException
	 */
	long[] receiveRecipesIds() throws ServiceException;

	/**
	 * Lädt alle Items von der GW2 API und aktualisiert diese in der DB, wobei immer eine feste Anzahl an Items in einer
	 * Transaktion abgehandelt werden.
	 * 
	 * @throws ServiceException
	 */
	void updateItems() throws ServiceException;

}
