package de.morrigan.dev.gw2.dto.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteAuthenticationService;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteMapService;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteUserAdminService;

/**
 * @author morrigan
 * @see https://docs.jboss.org/author/display/AS71/EJB+invocations+from+a+remote+client+using+JNDI
 */
public class JNDIServiceFactory {

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(JNDIServiceFactory.class);

	/** Einzige Instanz dieser Factory */
	private static final JNDIServiceFactory INSTANCE = new JNDIServiceFactory();

	private static final String APP_NAME = "GW2Util-Server";
	private static final String MODULE_NAME = "gw2util-server-businesstier-0.0.1-SNAPSHOT";
	private static final String EJB_PATH = "ejb:" + APP_NAME + "/" + MODULE_NAME;

	private static final String JNDI_REMOTE_MAP_SERVICE = EJB_PATH + "/MapService!" + IRemoteMapService.class.getName();
	private static final String JNDI_REMOTE_USER_ADMIN_SERVICE = EJB_PATH + "/UserAdminService!"
			+ IRemoteUserAdminService.class.getName();
	private static final String JNDI_REMOTE_AUTH_SERVICE = EJB_PATH + "/AuthenticationService!"
			+ IRemoteAuthenticationService.class.getName();

	/**
	 * @return die einzige Instanz dieser Factory.
	 */
	public static JNDIServiceFactory getInstance() {
		return INSTANCE;
	}

	protected Context jndiContext;
	protected IRemoteMapService remoteMapService;
	protected IRemoteUserAdminService remoteUserAdminService;
	protected IRemoteAuthenticationService remoteAuthService;

	private JNDIServiceFactory() {
		super();

		initJNDIContext();
	}

	public IRemoteAuthenticationService getRemoteAuthenticationService() throws ServiceException {
		if (this.remoteAuthService == null) {
			try {
				// Original
				this.remoteAuthService = (IRemoteAuthenticationService) this.jndiContext.lookup(JNDI_REMOTE_AUTH_SERVICE);

				//				final Hashtable jndiProperties = new Hashtable();

				//				jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
				//				jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
				//				final Context ctx = new InitialContext(jndiProperties);
				//				return (IRemoteAuthenticationService) this.jndiContext
				//						.lookup("ejb:GW2Util-Server/gw2util-server-businesstier-0.0.1-SNAPSHOT/AuthenticationService!sd.domain.remote.interfaces.IRemoteAuthenticationService");

				//				final String appName = "GW2Util-Server";
				//        final String moduleName = "gw2util-server-businesstier-0.0.1-SNAPSHOT";
				//        final String beanName = "AuthenticationService";
				//        final String viewClassName = IRemoteAuthenticationService.class.getName();
				//        System.out.println("Looking EJB via JNDI ");
				//        System.out.println("ejb:" + appName + "/" + moduleName + "/" + beanName + "!" + viewClassName);
				//
				//        this.remoteAuthService= (IRemoteAuthenticationService) jndiContext.lookup("ejb:" + appName + "/" + moduleName + "/" + beanName + "!" + viewClassName);

				//				this.remoteAuthService = (IRemoteAuthenticationService) this.jndiContext.lookup(
				//						"ejb:GW2Util-Server/gw2util-server-businesstier-0.0.1-SNAPSHOT/AuthenticationService!sd.domain.remote.interfaces.IRemoteAuthenticationService");
			} catch (NamingException e) {
				throw new ServiceException(e);
			}
		}
		return this.remoteAuthService;
	}

	public IRemoteMapService getRemoteMapService() throws ServiceException {
		if (this.remoteMapService == null) {
			try {
				this.remoteMapService = (IRemoteMapService) this.jndiContext.lookup(JNDI_REMOTE_MAP_SERVICE);
			} catch (NamingException e) {
				throw new ServiceException(e);
			}
		}
		return this.remoteMapService;
	}

	public IRemoteUserAdminService getRemoteUserAdminService() throws ServiceException {
		if (this.remoteUserAdminService == null) {
			try {
				this.remoteUserAdminService = (IRemoteUserAdminService) this.jndiContext
						.lookup(JNDI_REMOTE_USER_ADMIN_SERVICE);
			} catch (NamingException e) {
				throw new ServiceException(e);
			}
		}
		return this.remoteUserAdminService;
	}

	private void initJNDIContext() {
		try {
			Properties jndiProperties = new Properties();
			File jndiPropertiesFile = new File("config/jndi.properties");
			if (jndiPropertiesFile.exists()) {
				jndiProperties.load(new FileInputStream(jndiPropertiesFile));
			} else {
				jndiProperties.load(getClass().getResourceAsStream("/config/jndi.properties"));
			}
			Hashtable<String, String> table = new Hashtable<String, String>();
			Enumeration<Object> enm = jndiProperties.keys();
			while (enm.hasMoreElements()) {
				String key = (String) enm.nextElement();
				String value = jndiProperties.getProperty(key);
				table.put(key, value);
			}

			Properties jndiPRoperties = new Properties();
			jndiPRoperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			jndiPRoperties.put("jboss.naming.client.ejb.context", true);
			this.jndiContext = new InitialContext(jndiPRoperties);
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} catch (NamingException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
