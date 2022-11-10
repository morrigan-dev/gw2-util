package de.morrigan.dev.gw2.factory;

import de.morrigan.dev.gw2.dao.ClientDataDAO;
import de.morrigan.dev.gw2.dao.GemsStatisticDAO;
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

public class LocalSQLiteDAOFactory extends DBDAOFactory {

  private ClientDataDAO clientDataDAO;
  private GemsStatisticDAO gemsStatisticDAO;
  private ItemDAO itemDAO;
  private RightDAO rightDAO;
  private SessionDAO sessionDAO;
  private UserDAO userDAO;
  private UserGroupDAO userGroupDAO;
  private WaypointDAO waypointDAO;

  @Override
  public IClientDataDAO getClientDataDAO() {
    if (this.clientDataDAO == null) {
      this.clientDataDAO = new ClientDataDAO();
    }
    return this.clientDataDAO;
  }

  @Override
  public IGemsStatisticDAO getGemsStatisticDAO() {
    if (this.gemsStatisticDAO == null) {
      this.gemsStatisticDAO = new GemsStatisticDAO();
    }
    return this.gemsStatisticDAO;
  }

  @Override
  public IItemDAO getItemDAO() {
    if (this.itemDAO == null) {
      this.itemDAO = new ItemDAO();
    }
    return this.itemDAO;
  }

  @Override
  public IRightDAO getRightDAO() {
    if (this.rightDAO == null) {
      this.rightDAO = new RightDAO();
    }
    return this.rightDAO;
  }

  @Override
  public ISessionDAO getSessionDAO() {
    if (this.sessionDAO == null) {
      this.sessionDAO = new SessionDAO();
    }
    return this.sessionDAO;
  }

  @Override
  public IUserDAO getUserDAO() {
    if (this.userDAO == null) {
      this.userDAO = new UserDAO();
    }
    return this.userDAO;
  }

  @Override
  public IUserGroupDAO getUserGroupDAO() {
    if (this.userGroupDAO == null) {
      this.userGroupDAO = new UserGroupDAO();
    }
    return this.userGroupDAO;
  }

  @Override
  public IWaypointDAO getWaypointDAO() {
    if (this.waypointDAO == null) {
      this.waypointDAO = new WaypointDAO();
    }
    return this.waypointDAO;
  }
}
