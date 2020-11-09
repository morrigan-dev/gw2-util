package de.morrigan.dev.gw2.dao;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dao.interfaces.IRightDAO;
import de.morrigan.dev.gw2.entity.Right;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

@Stateless
public class RightDAO extends GenericDAOHibernate<Right, Long> implements IRightDAO {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(RightDAO.class);

	public RightDAO() {
		super(Right.class);
	}

	@Override
	public List<Right> getRightByActiveState(final ActiveState... activeStates) throws PersistenceException {
		Validate.notNull(activeStates, "Der Parameter (activeStates) darf nicht null sein!");
		LOG.debug("activeStates: {}", Arrays.asList(activeStates));
		final StringBuilder query = new StringBuilder();
		query.append("SELECT rights FROM Right AS rights");
		query.append(" WHERE ");
		query.append("	rights.activeState IN (" + getINParameter(activeStates) + ")");
		final Query q = this.entityManager.createQuery(query.toString());
		final List<Right> resultList = getResultList(q);
		LOG.debug("resultList: {}", resultList);
		return resultList;
	}
}
