package de.morrigan.dev.gw2.business.factories;

import java.io.Serializable;

import de.morrigan.dev.gw2.business.local.interfaces.IAuthenticationService;
import de.morrigan.dev.gw2.business.local.interfaces.IJobService;
import de.morrigan.dev.gw2.business.local.interfaces.ITradingPostService;

/**
 * Diese abstrakte Klasse gibt die Schnittstellen vor, die von den Factory Methoden implementiert werden müssen. Für
 * jede Service Klasse muss eine abstrakte Factorymethode hier definiert werden, die dann später in den entsprechenden
 * Factoryklassen implementiert wird.
 * 
 * @author morrigan
 */
public abstract class ServiceFactory implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Einzige Instanz dieser Factory */
	private static final ServiceFactory INSTANCE = new EJBServiceFactory();

	/**
	 * @return die einzige Instanz dieser Factory.
	 */
	public static ServiceFactory getInstance() {
		return INSTANCE;
	}

	protected ServiceFactory() {
		super();
	}

	/** @return den lokalen Service für die Authentification */
	public abstract IAuthenticationService getAuthenticationService();

	/** @return den lokalen Service für die Cron Job Aktionen */
	public abstract IJobService getJobService();

	/** @return den lokalen Service für den Handelsposten */
	public abstract ITradingPostService getTradingPostService();

}
