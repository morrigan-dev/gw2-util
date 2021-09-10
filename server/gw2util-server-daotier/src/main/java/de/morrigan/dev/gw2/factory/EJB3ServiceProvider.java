package de.morrigan.dev.gw2.factory;

import java.io.Serializable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Klasse ermöglicht den Zugriff auf EJBs, die lokal oder remote sein können.
 * 
 * @author morrigan
 */
public final class EJB3ServiceProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(EJB3ServiceProvider.class);

	private static final String MODULE_BUSINESS_TIER = "gw2util-server-businesstier-0.0.1-SNAPSHOT";
	private static final String MODULE_DAO_TIER = "gw2util-server-daotier-0.0.1-SNAPSHOT";

	/** Einzige Instanz dieser Klasse (Singleton) */
	private static final EJB3ServiceProvider INSTANCE = new EJB3ServiceProvider();

	/** @return die einzige Instanz dieses Providers */
	public static EJB3ServiceProvider getInstance() {
		return INSTANCE;
	}

	/** Privater Singleton-Konstruktor */
	private EJB3ServiceProvider() {
		super();
	}

	/**
	 * Lädt einen EJB Service anhand der Serviceklasse aus dem Modul BusinessTier.
	 * 
	 * @param serviceClass Der Name der zu ladenen Serviceklasse.
	 * @return Eine Instanz des Services.
	 */
	public Object getEJBFromBusinessTier(final Class<?> serviceClass, final Class<?> interfaceClass) {
		return getEJB(serviceClass.getSimpleName(), interfaceClass.getName(), MODULE_BUSINESS_TIER);
	}

	/**
	 * Lädt einen local EJB Service anhand des Servicenamens.
	 * 
	 * @param serviceName Der Name des zu ladenen Services.
	 * @return eine Instanz des Services.
	 */
	public Object getEJBFromDAOTier(final Class<?> serviceClass, final Class<?> interfaceClass) {
		return getEJB(serviceClass.getSimpleName(), interfaceClass.getName(), MODULE_DAO_TIER);
	}

	/**
	 * Lädt einen EJB Service anhand der Serviceklasse und der Art des Services, die entwerden local oder remote sein
	 * kann.
	 * 
	 * @param beanName der Name des zu ladenen Services.
	 * @param module das Modul in dem der Service sich befindet.
	 * @return eine Instanz des Services.
	 */
	private Object getEJB(final String beanName, final String interfaceName, final String module) {
		assert beanName != null : "serviceName darf nicht null sein!";
		assert module != null : " type darf nicht null sein!";
		Object service = null;
		try {
			final Context ic = new InitialContext();
			// final String path = "java:app/" + module + "/" + beanName + "!" + interfaceName;
			final String path = "java:global/GW2Util-Server/" + module + "/" + beanName + "!" + interfaceName;
			LOG.debug("path: {}", path);
			service = ic.lookup(path);
		} catch (final NamingException e) {
			LOG.error(e.getMessage(), e);
		}
		return service;
	}
}
