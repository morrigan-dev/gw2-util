package de.morrigan.dev.gw2.dao.interfaces;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.ClientDataDAO;
import de.morrigan.dev.gw2.entity.ClientData;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link ClientDataDAO}. Hier werden nur die Methoden
 * bereitgestellt, die etwas mit der Entity {@link ClientData} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface IClientDataDAO extends IGenericDAO<ClientData, Long> {

}
