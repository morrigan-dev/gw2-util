package de.morrigan.dev.gw2.resources;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigManager {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(ConfigManager.class);

	/** Konstanten der Properties */
	public static final String IP = "IP";
	public static final String PORT = "PORT";
	public static final String PRINTER_IP = "PRINTER_IP";
	public static final String PRINTER_PORT = "PRINTER_PORT";
	public static final String APPLICATION_NAME = "APPLICATION_NAME";

	/** Einzige Instanz dieses Managers */
	private static final ConfigManager INSTANCE = new ConfigManager();

	private static final String PROPERTIES_PATH = "properties/";

	public static final String PROPERTIE_NAME = "config.properties";

	/** Manager-Methode für diese Klasse (Singleton) */
	public static ConfigManager getInstance() {
		return INSTANCE;
	}

	private String fullPropertiesPath;

	private Properties properties = new Properties();

	private ConfigManager() {

		generatePropertiesPath();
		loadConfigProperties();
	}

	/**
	 * @param propertyName der Einstellung in der config.properties(not null);
	 * @return property als String
	 */
	public String getPropertyFor(final String propertyName) {
		Validate.notNull(propertyName, "Folgende Parameter dürfen nicht null sein! propertyName: {}", propertyName);
		return this.properties.getProperty(propertyName);
	}

	public void setAddPropertyPath(final String path) {
		Validate.notNull(path, "Folgende Parameter dürfen nicht null sein! path: {}", path);
		this.fullPropertiesPath = path;
		loadConfigProperties();
	}

	/**
	 * Generiert den Pfad in dem die config.properties gesucht wird
	 */
	private void generatePropertiesPath() {
		try {
			final String pathWithFileName = ConfigManager.class.getProtectionDomain().getCodeSource().getLocation()
					.toURI().getPath();
			if ((pathWithFileName != null) && !pathWithFileName.isEmpty()) {
				final File jarFile = new File(pathWithFileName);
				final String pathWithoutFileName = (jarFile.getPath().substring(0, jarFile.getPath().length()
						- jarFile.getName().length()));
				this.fullPropertiesPath = pathWithoutFileName + PROPERTIES_PATH + PROPERTIE_NAME;
			} else {
				final String pathWithoutFileName = new URL(System.getProperty("jboss.server.config.url")).toURI()
						.getPath();
				this.fullPropertiesPath = pathWithoutFileName + PROPERTIE_NAME;
			}
		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Befüllt die Properties
	 */
	private void loadConfigProperties() {
		try {
			if (this.properties == null) {
				this.properties = new Properties();
			}
			LOG.debug("fullPropertiesPath = " + this.fullPropertiesPath);
			this.properties.load(new FileInputStream(this.fullPropertiesPath));
		} catch (final Throwable e) {
			LOG.error(e.getMessage(), e);
			LOG.warn("Es wurde keine config.properties in " + this.fullPropertiesPath + " gefunden.");
		}
	}

}
