package de.morrigan.dev.gw2.factory;

import de.morrigan.dev.gw2.dao.interfaces.IClientDataDAO;
import de.morrigan.dev.gw2.dao.interfaces.IGemsStatisticDAO;
import de.morrigan.dev.gw2.dao.interfaces.IItemDAO;
import de.morrigan.dev.gw2.dao.interfaces.IRightDAO;
import de.morrigan.dev.gw2.dao.interfaces.ISessionDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserGroupDAO;
import de.morrigan.dev.gw2.dao.interfaces.IWaypointDAO;

/**
 * Diese Factory Klasse stellt für jedes DAO eine Methode bereit, die entsprechende Interfaces für den Zugriff auf die
 * DAO bieten. Für jedes DAO muss hier eine entsprechende abstrakte Methode definiert und in allen konkreten Factory
 * Klassen implementiert werden.
 *
 * @author morrigan
 * @see http://www.ibm.com/developerworks/java/library/j-genericdao/index.html
 * @see http://java.sun.com/blueprints/corej2eepatterns/Patterns/DataAccessObject.html
 */
public abstract class DBDAOFactory {

  /** Einzige Instanz der Factory Klasse für den Zugriff auf eine Datenbank */
  private static DBDAOFactory instance;

  /**
   * @return die Instanz der Factory für den Zugriff auf die DAOs.
   */
  public static DBDAOFactory getInstance() {
    if (instance == null) {
      instance = new MySQLDAOFactory();
    }
    return instance;
  }

  /** @return das DAO für den Zugriff auf die ClientData */
  public abstract IClientDataDAO getClientDataDAO();

  /** @return das DAO für den Zugriff auf die Gemsstatistic */
  public abstract IGemsStatisticDAO getGemsStatisticDAO();

  /** @return das DAO für den Zugriff auf die Items */
  public abstract IItemDAO getItemDAO();

  /** @return das DAO für den Zugriff auf die Rechte */
  public abstract IRightDAO getRightDAO();

  /** @return das DAO für den Zugriff auf die Sessions */
  public abstract ISessionDAO getSessionDAO();

  /** @return das DAO für den Zugriff auf die Benutzer */
  public abstract IUserDAO getUserDAO();

  /** @return das DAO für den Zugriff auf die Benutzergruppen */
  public abstract IUserGroupDAO getUserGroupDAO();

  /** @return das DAO für den Zugriff auf die Wegpunkte */
  public abstract IWaypointDAO getWaypointDAO();

}
