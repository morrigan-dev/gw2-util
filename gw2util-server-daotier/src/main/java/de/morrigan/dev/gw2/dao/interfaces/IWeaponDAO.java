package de.morrigan.dev.gw2.dao.interfaces;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.WeaponDAO;
import de.morrigan.dev.gw2.entity.Weapon;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link WeaponDAO}. Hier werden nur die Methoden bereitgestellt,
 * die etwas mit der Entity {@link Weapon} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface IWeaponDAO extends IGenericDAO<Weapon, Long> {

}
