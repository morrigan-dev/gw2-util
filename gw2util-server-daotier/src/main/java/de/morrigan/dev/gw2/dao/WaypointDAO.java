package de.morrigan.dev.gw2.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dao.interfaces.IWaypointDAO;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.entity.Waypoint;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

@Stateless
public class WaypointDAO extends GenericDAOHibernate<Waypoint, Long> implements IWaypointDAO {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(WaypointDAO.class);

	public WaypointDAO() {
		super(Waypoint.class);
	}

	@Override
	public int getAmountOf(WPSubType wpSubType, double fromLatitude, double fromLongitude, double toLatitude,
			double toLongitude) throws PersistenceException {
		Validate.notNull(wpSubType, "Der Parameter (wpSubType) darf nicht null sein!");
		LOG.debug("wpSubType: {}, fromLatitude: {}, fromLongitude: {}, toLatitude: {}, toLongitude: {}", wpSubType,
				fromLatitude, fromLongitude, toLatitude, toLongitude);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM Waypoint AS waypoint");
		query.append("  WHERE waypoint.activeState = :pActive");
		query.append("  AND waypoint.wpSubType = :pWpSubType");
		query.append("  AND waypoint.latitude <= :fromLatitude");
		query.append("  AND waypoint.latitude >= :toLatitude");
		query.append("  AND waypoint.longitude >= :fromLongitude");
		query.append("  AND waypoint.longitude <= :toLongitude");

		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("pActive", ActiveState.ACTIVE);
		q.setParameter("pWpSubType", wpSubType);
		q.setParameter("fromLatitude", fromLatitude);
		q.setParameter("toLatitude", toLatitude);
		q.setParameter("fromLongitude", fromLongitude);
		q.setParameter("toLongitude", toLongitude);

		int amount = ((Number) q.getSingleResult()).intValue();

		LOG.debug("amount: {}", amount);
		return amount;
	}

	@Override
	public List<Waypoint> getWaypoints(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude)
			throws PersistenceException {
		LOG.debug("fromLatitude: {}, fromLongitude: {}, toLatitude: {}, toLongitude: {}", fromLatitude, fromLongitude,
				toLatitude, toLongitude);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT waypoint FROM Waypoint AS waypoint");
		query.append("  WHERE waypoint.latitude <= :fromLatitude");
		query.append("  AND waypoint.latitude >= :toLatitude");
		query.append("  AND waypoint.longitude >= :fromLongitude");
		query.append("  AND waypoint.longitude <= :toLongitude");

		final Query q = this.entityManager.createQuery(query.toString());
		q.setParameter("fromLatitude", fromLatitude);
		q.setParameter("toLatitude", toLatitude);
		q.setParameter("fromLongitude", fromLongitude);
		q.setParameter("toLongitude", toLongitude);

		final List<Waypoint> resultList = getResultList(q);

		LOG.debug("resultList: {}", resultList);
		assert resultList != null : "resultList darf nicht null sein!";
		return resultList;
	}

	@Override
	public List<Waypoint> getWaypoints(WPType[] wpTypes, WPSubType wpSubType, ActiveState... activeStates)
			throws PersistenceException {
		Validate.notNull(wpSubType, "Der Parameter (wpSubType) darf nicht null sein!");
		Validate.notNull(wpTypes, "Der Parameter (wpTypes) darf nicht null sein!");
		Validate.notNull(activeStates, "Der Parameter (activeStates) darf nicht null sein!");
		LOG.debug("wpTypes: {}, wpSubType: {}, activeStates: {}", wpTypes, wpSubType, activeStates);

		final StringBuilder query = new StringBuilder();
		query.append("SELECT waypoint FROM Waypoint AS waypoint");
		query.append("  WHERE waypoint.activeState IN (" + getINParameter(activeStates) + ")");
		query.append("  AND waypoint.wpType IN (" + getINParameter(wpTypes) + ")");

		final Query q = this.entityManager.createQuery(query.toString());

		final List<Waypoint> resultList = getResultList(q);

		LOG.debug("resultList: {}", resultList);
		assert resultList != null : "resultList darf nicht null sein!";
		return resultList;
	}
}
