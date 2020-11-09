package de.morrigan.dev.gw2.dao;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dao.interfaces.IUserGroupDAO;
import de.morrigan.dev.gw2.entity.UserGroup;
import de.morrigan.dev.gw2.exception.NoResultException;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

@Stateless
public class UserGroupDAO extends GenericDAOHibernate<UserGroup, Long> implements IUserGroupDAO {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(UserGroupDAO.class);

	public UserGroupDAO() {
		super(UserGroup.class);
	}

	@Override
	public List<UserGroup> getUserGroupByActiveState(final ActiveState... activeStates) throws PersistenceException {
		LOG.debug("activeStates: {}", (Object) activeStates);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT userGroup FROM UserGroup AS userGroup");
		query.append(" WHERE ");
		query.append("	userGroup.activeState IN (" + getINParameter(activeStates) + ")");
		final Query q = this.entityManager.createQuery(query.toString());
		final List<UserGroup> resultList = getResultList(q);

		LOG.debug("resultList: {}", resultList);
		return resultList;
	}

	@Override
	public List<UserGroup> getUserGroupsForCache(ActiveState... activeStates) throws PersistenceException {
		LOG.debug("activeStates: {}", (Object) activeStates);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT userGroup FROM UserGroup AS userGroup");
		query.append(" INNER JOIN FETCH userGroup.userSet");
		query.append(" INNER JOIN FETCH userGroup.rightSet");
		query.append(" WHERE ");
		query.append("	userGroup.activeState IN (" + getINParameter(activeStates) + ")");
		final Query q = this.entityManager.createQuery(query.toString());
		final List<UserGroup> resultList = getResultList(q);

		LOG.debug("resultList: {}", resultList);
		return resultList;
	}

	@Override
	public UserGroup findByName(String groupname) throws PersistenceException {
		LOG.debug("groupname: {}", groupname);
		
		final StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT userGroup FROM UserGroup AS userGroup");
		query.append(" WHERE ");
		query.append("	userGroup.name = :pGroupName");
		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("pGroupName", groupname);

		UserGroup result = null;
		try {
			result = getSingleResult(q);
		} catch (NoResultException e) {
			// Benutzername ist noch frei
		}

		LOG.debug("result: {}", result);
		return result;
	}
}
