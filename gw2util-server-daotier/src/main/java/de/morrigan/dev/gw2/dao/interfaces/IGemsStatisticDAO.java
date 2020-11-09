package de.morrigan.dev.gw2.dao.interfaces;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.GemsStatisticDAO;
import de.morrigan.dev.gw2.entity.GemsStatistic;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link GemsStatisticDAO}. Hier werden nur die Methoden
 * bereitgestellt, die etwas mit der Entity {@link GemsStatistic} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface IGemsStatisticDAO extends IGenericDAO<GemsStatistic, Long> {

}
