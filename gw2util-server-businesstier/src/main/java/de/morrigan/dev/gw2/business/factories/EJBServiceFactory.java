package de.morrigan.dev.gw2.business.factories;

import de.morrigan.dev.gw2.business.local.interfaces.IAuthenticationService;
import de.morrigan.dev.gw2.business.local.interfaces.IJobService;
import de.morrigan.dev.gw2.business.local.interfaces.ITradingPostService;
import de.morrigan.dev.gw2.business.logic.AuthenticationService;
import de.morrigan.dev.gw2.business.logic.JobService;
import de.morrigan.dev.gw2.business.logic.TradingPostService;
import de.morrigan.dev.gw2.factory.EJB3ServiceProvider;

/**
 * Diese Klasse bietet verschiedene Factory Methoden an, um EJB Serviceklassen zu erzeugen. Bitte in der
 * {@link ServiceFactory} einen abstrakten Signatureintrag für jede Methode eintragen.
 * 
 * @author morrigan
 */
public class EJBServiceFactory extends NoEJBServiceFactory {

	private static final long serialVersionUID = 1L;

	@Override
	public IAuthenticationService getAuthenticationService() {
		return (IAuthenticationService) EJB3ServiceProvider.getInstance().getEJBFromBusinessTier(
				AuthenticationService.class, IAuthenticationService.class);
	}

	@Override
	public IJobService getJobService() {
		return (IJobService) EJB3ServiceProvider.getInstance().getEJBFromBusinessTier(JobService.class,
				IJobService.class);
	}

	@Override
	public ITradingPostService getTradingPostService() {
		return (ITradingPostService) EJB3ServiceProvider.getInstance().getEJBFromBusinessTier(TradingPostService.class,
				ITradingPostService.class);
	}

}
