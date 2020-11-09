package de.morrigan.dev.gw2.dao;

import javax.ejb.Stateless;

import de.morrigan.dev.gw2.dao.interfaces.IWeaponDAO;
import de.morrigan.dev.gw2.entity.Weapon;

@Stateless
public class WeaponDAO extends GenericDAOHibernate<Weapon, Long> implements IWeaponDAO {

	public WeaponDAO() {
		super(Weapon.class);
	}

}
