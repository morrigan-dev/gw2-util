package de.morrigan.dev.gw2.dao;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dao.interfaces.IItemDAO;
import de.morrigan.dev.gw2.dto.common.enums.Language;
import de.morrigan.dev.gw2.entity.Item;
import de.morrigan.dev.gw2.exception.NoResultException;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

@Stateless
public class ItemDAO extends GenericDAOHibernate<Item, Long> implements IItemDAO {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(ItemDAO.class);

	public ItemDAO() {
		super(Item.class);
	}

	@Override
	public Item findByExternalId(long externalItemId, Language lang) throws PersistenceException {
		LOG.debug("externalItemId: {}, lang: {}", externalItemId, lang);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT item FROM Item AS item");
		query.append("  WHERE item.externalId = :pExternalItemId");
		query.append("  AND item.language = :pLanguage");

		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("pExternalItemId", externalItemId);
		q.setParameter("pLanguage", lang);

		Item result = null;
		try {
			result = getSingleResult(q);
		} catch (NoResultException e) {
			LOG.info("Item mit der externen ID " + externalItemId + " und der Sprache " + lang
					+ " existiert noch nicht.");
		}

		LOG.debug("result: {}", result);
		return result;
	}
}
