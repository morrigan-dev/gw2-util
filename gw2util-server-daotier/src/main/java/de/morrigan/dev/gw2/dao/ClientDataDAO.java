package de.morrigan.dev.gw2.dao;

import javax.ejb.Stateless;

import de.morrigan.dev.gw2.dao.interfaces.IClientDataDAO;
import de.morrigan.dev.gw2.entity.ClientData;

@Stateless
public class ClientDataDAO extends GenericDAOHibernate<ClientData, Long> implements IClientDataDAO {

	public ClientDataDAO() {
		super(ClientData.class);
	}

}
