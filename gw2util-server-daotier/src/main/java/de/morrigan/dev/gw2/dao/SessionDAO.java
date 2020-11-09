package de.morrigan.dev.gw2.dao;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dao.interfaces.ISessionDAO;
import de.morrigan.dev.gw2.entity.Session;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.exception.NoResultException;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

@Stateless
public class SessionDAO extends GenericDAOHibernate<Session, Long> implements ISessionDAO {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(SessionDAO.class);

	public SessionDAO() {
		super(Session.class);
	}

	@Override
	public Session findSessionBySessionKey(final String sessionKey) throws PersistenceException {
		Validate.notNull(sessionKey, "Der Parameter (sessionKey) darf nicht null sein!");
		LOG.debug("sessionKey: {}", sessionKey);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT session FROM Session AS session");
		query.append("  WHERE session.sessionKey = :pSessionKey");

		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("pSessionKey", sessionKey);

		Session result = null;
		try {
			result = getSingleResult(q);
		} catch (final NoResultException e) {
			// Erwartete Fehlermeldung, falls es noch keine Session mit der SessionId gibt
			LOG.debug("Kein Session mit dem SessionKey {} gefunden.", sessionKey);
		}

		LOG.debug("result: {}", result);
		return result;
	}

	@Override
	public Session findSessionByUser(final User user) throws PersistenceException {
		Validate.notNull(user, "Der Parameter (user) darf nicht null sein!");
		LOG.debug("user: {}", user);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT session FROM Session AS session");
		query.append("  WHERE session.user = :pUser");

		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("pUser", user);
		Session result = null;
		try {
			result = getSingleResult(q);
		} catch (final NoResultException e) {
			// Erwartete Fehlermeldung, falls es noch keine Session mit dem User gibt
			LOG.debug("Kein Session mit dem User {} gefunden.", user);
		}

		LOG.debug("result: {}", result);
		return result;
	}
}
