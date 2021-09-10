package de.morrigan.dev.gw2.dao.interfaces;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.ItemDAO;
import de.morrigan.dev.gw2.dto.common.enums.Language;
import de.morrigan.dev.gw2.entity.Item;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link ItemDAO}. Hier werden nur die Methoden bereitgestellt,
 * die etwas mit der Entity {@link Item} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface IItemDAO extends IGenericDAO<Item, Long> {

	/**
	 * Schaut in der DB nach, ob es ein Item mit der angegebenen externen Item ID und der Sprache gibt. Ist dies der
	 * Fall, wird das Item zurückgegeben. Ansonsten wird <code>null</code> zurückgegeben.
	 * 
	 * @param externalItemId eine externe Item ID.
	 * @param lang eine Sprache.
	 * @return ein Item oder <code>null</code>
	 * @throws PersistenceException
	 */
	Item findByExternalId(long externalItemId, Language lang) throws PersistenceException;

}
