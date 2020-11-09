package de.morrigan.dev.gw2.dao;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import de.morrigan.dev.gw2.dao.interfaces.IWeaponDAO;
import de.morrigan.dev.gw2.entity.Weapon;

@Stateless
public class WeaponDAO extends GenericDAOHibernate<Weapon, Long> implements IWeaponDAO {

	/** Logger für Debug/Fehlerausgaben */
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(WeaponDAO.class);

	public WeaponDAO() {
		super(Weapon.class);
	}

}
