package de.morrigan.dev.gw2.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dao.interfaces.IUserDAO;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.exception.NoResultException;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

@Stateless
public class UserDAO extends GenericDAOHibernate<User, Long> implements IUserDAO {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

	public UserDAO() {
		super(User.class);
	}

	@Override
	public User authenticate(final String userName, final String password) throws PersistenceException {
		Validate.notNull(userName, "Der Parameter (userName) darf nicht null sein!");
		Validate.notNull(password, "Der Parameter (password) darf nicht null sein!");
		LOG.debug("userName: {}", userName);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT user FROM User AS user");
		query.append("  WHERE user.userName = :pUserName");
		query.append("  AND user.password = :pPassword");
		query.append("  AND user.activeState IN (:pActive, :pNotAdmin)");

		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("pUserName", userName);
		q.setParameter("pPassword", password);
		q.setParameter("pActive", ActiveState.ACTIVE);
		q.setParameter("pNotAdmin", ActiveState.NOT_ADMIN);

		User result = null;
		result = getSingleResult(q);

		LOG.debug("result: {}", result);
		return result;
	}

	@Override
	public boolean getIsUsernameAvailable(String username) throws PersistenceException {
		LOG.debug("username: {}", username);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT user FROM User AS user");
		query.append("  WHERE user.userName = :pUserName");

		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("pUserName", username);

		User result = null;
		try {
			result = getSingleResult(q);
		} catch (NoResultException e) {
			// Benutzername ist noch frei
		}

		LOG.debug("result: {}", result);
		return result == null;
	}

	@Override
	public List<User> getUserByActiveState(final ActiveState... activeStates) throws PersistenceException {
		LOG.debug("activeStates: {}", (Object) activeStates);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT user FROM User AS user");
		query.append(" WHERE ");
		query.append("	user.activeState IN (");
		for (int i = 0; i < activeStates.length; i++) {
			query.append(":");
			query.append(activeStates[i].name());
			if (i < (activeStates.length - 1)) {
				query.append(",");
			}
		}
		query.append("	)");

		final Query q = this.entityManager.createQuery(query.toString());
		for (int i = 0; i < activeStates.length; i++) {
			q.setParameter(activeStates[i].name(), activeStates[i]);
		}

		final List<User> resultList = getResultList(q);

		LOG.debug("resultList: {}", resultList);
		return resultList;
	}

	@Override
	public User getUserByUsername(String userName) throws PersistenceException {
		LOG.debug("userName: {}", userName);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT user FROM User AS user");
		query.append("  WHERE user.userName = :pUserName");
		query.append("  AND user.activeState IN (:pActive, :pNotAdmin)");

		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("pUserName", userName);
		q.setParameter("pActive", ActiveState.ACTIVE);
		q.setParameter("pNotAdmin", ActiveState.NOT_ADMIN);

		User result = null;
		try {
			result = getSingleResult(q);
		} catch (NoResultException e) {
			// Benutzername existiert nicht
		}

		LOG.debug("result: {}", result);
		return result;
	}
}
