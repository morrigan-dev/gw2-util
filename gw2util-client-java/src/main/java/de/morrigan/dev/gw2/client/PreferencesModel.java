package de.morrigan.dev.gw2.client;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.jboss.logging.Logger;

import de.morrigan.dev.swing.models.AbstractModel;

public class PreferencesModel extends AbstractModel {

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(PreferencesModel.class);

	private static final PreferencesModel INSTANCE = new PreferencesModel();

	private static final String PREFERENCES_FILE = "preferences.ini";

	private static final String PATH_STAY_LOGGED_ON = "Authentication.stayLoggedOn";
	private static final String PATH_SESSION_KEY = "Authentication.sessionKey";
	private static final String PATH_USERNAME = "Authentication.username";

	private static final String PATH_UPDATE_INTERVAL = "GameSettings.updateInterval";

	public static final PreferencesModel getInstance() {
		return INSTANCE;
	}

	private HierarchicalINIConfiguration config;

	private PreferencesModel() {
		super();

		File prefFile = new File(PREFERENCES_FILE);
		try {
			if (!prefFile.exists()) {
				prefFile.createNewFile();
			}
			this.config = new HierarchicalINIConfiguration(PREFERENCES_FILE);
		} catch (ConfigurationException | IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public String getSessionKey() {
		return getPropertyValue(PATH_SESSION_KEY);
	}

	public int getUpdateInterval() {
		int result = 0;
		String updateInterval = getPropertyValue(PATH_UPDATE_INTERVAL);
		try {
			result = Integer.parseInt(updateInterval);
		} catch (NumberFormatException e) {}
		if (result < 10000) {
			result = 10000;
			setUpdateInterval(10000);
		}
		return result;
	}

	public String getUsername() {
		return getPropertyValue(PATH_USERNAME);
	}

	public boolean isStayLoggedOn() {
		return Boolean.valueOf(getPropertyValue(PATH_STAY_LOGGED_ON));
	}

	public void savePreferences() throws ConfigurationException {
		this.config.save();
	}

	public void setSessionKey(String sessionKey) {
		this.config.setProperty(PATH_SESSION_KEY, sessionKey);
	}

	public void setStayLoggedOn(final boolean stayLoggedOn) {
		this.config.setProperty(PATH_STAY_LOGGED_ON, stayLoggedOn);
	}

	public void setUpdateInterval(int updateInterval) {
		this.config.setProperty(PATH_UPDATE_INTERVAL, updateInterval);
	}

	public void setUsername(final String username) {
		this.config.setProperty(PATH_USERNAME, username);
	}

	private String getPropertyValue(String key) {
		String result = "";
		Object propertyValue = this.config.getProperty(key);
		if (propertyValue != null) {
			result = propertyValue.toString();
		}
		return result;
	}
}
