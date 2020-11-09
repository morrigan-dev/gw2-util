package de.morrigan.dev.gw2.dao;

import javax.ejb.Stateless;

import de.morrigan.dev.gw2.dao.interfaces.IGemsStatisticDAO;
import de.morrigan.dev.gw2.entity.GemsStatistic;

@Stateless
public class GemsStatisticDAO extends GenericDAOHibernate<GemsStatistic, Long> implements IGemsStatisticDAO {

	public GemsStatisticDAO() {
		super(GemsStatistic.class);
	}

}
