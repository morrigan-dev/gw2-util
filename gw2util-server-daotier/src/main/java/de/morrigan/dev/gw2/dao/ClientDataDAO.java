package de.morrigan.dev.gw2.dao;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import de.morrigan.dev.gw2.dao.interfaces.IClientDataDAO;
import de.morrigan.dev.gw2.entity.ClientData;

@Stateless
public class ClientDataDAO extends GenericDAOHibernate<ClientData, Long> implements IClientDataDAO {

	/** Logger für Debug/Fehlerausgaben */
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ClientDataDAO.class);

	public ClientDataDAO() {
		super(ClientData.class);
	}

}
