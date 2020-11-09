package de.morrigan.dev.gw2.factory;

import de.morrigan.dev.gw2.dao.ClientDataDAO;
import de.morrigan.dev.gw2.dao.ItemDAO;
import de.morrigan.dev.gw2.dao.RightDAO;
import de.morrigan.dev.gw2.dao.SessionDAO;
import de.morrigan.dev.gw2.dao.UserDAO;
import de.morrigan.dev.gw2.dao.UserGroupDAO;
import de.morrigan.dev.gw2.dao.WaypointDAO;
import de.morrigan.dev.gw2.dao.interfaces.IClientDataDAO;
import de.morrigan.dev.gw2.dao.interfaces.IGemsStatisticDAO;
import de.morrigan.dev.gw2.dao.interfaces.IItemDAO;
import de.morrigan.dev.gw2.dao.interfaces.IRightDAO;
import de.morrigan.dev.gw2.dao.interfaces.ISessionDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserGroupDAO;
import de.morrigan.dev.gw2.dao.interfaces.IWaypointDAO;
import de.morrigan.dev.gw2.entity.GemsStatistic;

/**
 * Diese Klasse implementiert die in der {@link DBDAOFactory} definierten DAO Zugriffsmethoden, die für mehr als nur
 * eine Datenbankgültig sind.
 * 
 * @author morrigan
 */
public abstract class JPADAOFactory extends DBDAOFactory {

	@Override
	public IClientDataDAO getClientDataDAO() {
		return (IClientDataDAO) EJB3ServiceProvider.getInstance().getEJBFromDAOTier(ClientDataDAO.class,
				IClientDataDAO.class);
	}

	@Override
	public IGemsStatisticDAO getGemsStatisticDAO() {
		return (IGemsStatisticDAO) EJB3ServiceProvider.getInstance().getEJBFromDAOTier(GemsStatistic.class,
				IGemsStatisticDAO.class);
	}

	@Override
	public IItemDAO getItemDAO() {
		return (IItemDAO) EJB3ServiceProvider.getInstance().getEJBFromDAOTier(ItemDAO.class, IItemDAO.class);
	}

	@Override
	public IRightDAO getRightDAO() {
		return (IRightDAO) EJB3ServiceProvider.getInstance().getEJBFromDAOTier(RightDAO.class, IRightDAO.class);
	}

	@Override
	public ISessionDAO getSessionDAO() {
		return (ISessionDAO) EJB3ServiceProvider.getInstance().getEJBFromDAOTier(SessionDAO.class, ISessionDAO.class);
	}

	@Override
	public IUserDAO getUserDAO() {
		return (IUserDAO) EJB3ServiceProvider.getInstance().getEJBFromDAOTier(UserDAO.class, IUserDAO.class);
	}

	@Override
	public IUserGroupDAO getUserGroupDAO() {
		return (IUserGroupDAO) EJB3ServiceProvider.getInstance().getEJBFromDAOTier(UserGroupDAO.class,
				IUserGroupDAO.class);
	}

	@Override
	public IWaypointDAO getWaypointDAO() {
		return (IWaypointDAO) EJB3ServiceProvider.getInstance()
				.getEJBFromDAOTier(WaypointDAO.class, IWaypointDAO.class);
	}

}
