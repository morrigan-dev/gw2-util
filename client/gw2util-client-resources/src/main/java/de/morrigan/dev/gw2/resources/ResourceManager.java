package de.morrigan.dev.gw2.resources;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.utils.observer.AObservable;

public final class ResourceManager extends AObservable {

	/** Local für Schweden */
	public static final Locale SWEDISH = new Locale("sv");

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(ResourceManager.class);

	/** Einzige Instanz des ResourceManagers (Singleton) */
	private static final ResourceManager MANAGER = new ResourceManager();

	/** Bundle-Konstante für die kundenspezifischen Beschriftungstexte */
	private static final String BUNDLE_CUSTOMER_LABELS = "labelsCustomer";

	/** Bundle-Konstante für die Beschriftungstexte */
	private static final String BUNDLE_LABELS = "labels";

	/** Bundle-Konstante für die Fehlermeldungen zu den Fehlercodes */
	private static final String BUNDLE_ERROR_CODES = "errorCodes";

	/** Trennzeichen zwischen Beschriftungen und Werten bei z.B. der Kombination Label und Textfeld */
	private static final String SEPARATOR = ":";

	/** Konstante für das Kürzel zu einem Label */
	private static final String PREFIX_LABEL = "l_";

	/** Konstante für das Kürzel zu einem Button */
	private static final String PREFIX_BUTTON = "b_";

	/** Konstante für das Kürzel zu einem errorCode */
	private static final String PREFIX_ERROR_CODE = "e_";

	/** Konstante für das Kürzel zu einer längeren BEschreibung */
	private static final String PREFIX_MESSAGE = "msg_";

	/**
	 * Diese Methode stellt die einzige Instanz dieser Klasse (Singleton) bereit.
	 * 
	 * @author morrigan
	 * @return einzige Instanz dieses ResourceManagers.
	 */
	public static ResourceManager getInstance() {
		return MANAGER;
	}

	/** Resourcebundle "labels.properties" */
	private transient MultiPropertyResourceBundle labelsBundle;

	/** Resourcebundle "errorCodes.properties" */
	private transient ResourceBundle errorCodesBundle;

	/** Aktuell gesetzte Local */
	private Locale currentLocale;

	/**
	 * Dieser private (Singleton) Konstruktor erzeugt alle benötigten ResourceBundels.
	 * 
	 * @author morrigan
	 */
	private ResourceManager() {
		setCurrentLocale(Locale.GERMAN);
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück. Übergeben wird lediglich der
	 * eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'b_' hier ergänzt wird.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels
	 */
	public String getButtonLabel(final String key) {
		return getLabel(key, PREFIX_BUTTON, this.labelsBundle);
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück. Übergeben wird lediglich der
	 * eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'b_' hier ergänzt wird. Der String wird zudem noch mit
	 * der {@link MessageFormat#format(String, Object...)} Methode und den übergebenen Objekten formatiert.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @param Die Objekte die der {@link MessageFormat#format(String, Object...)} Methode übergeben werden
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels
	 */
	public String getButtonLabelWithArguments(final String key, final Object... objects) {
		final String value = getLabel(key, PREFIX_BUTTON, this.labelsBundle);
		try {
			return MessageFormat.format(value, objects);
		} catch (final IllegalArgumentException ae) {
			LOG.error("Ungültige Werte übergeben! objects: " + objects, ae);
		}
		return value;
	}

	public Locale getCurrentLocale() {
		return this.currentLocale;
	}

	public ResourceBundle getErrorCodesBundle() {
		return this.errorCodesBundle;
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück. Übergeben wird lediglich der
	 * eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'e_' hier ergänzt wird.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels
	 */
	public String getErrorMessage(final int errorCode) {
		return getLabel(Integer.toString(errorCode), PREFIX_ERROR_CODE, this.errorCodesBundle);
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück. Übergeben wird lediglich der
	 * eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'e_' hier ergänzt wird. Der String wird zudem noch mit
	 * der {@link MessageFormat#format(String, Object...)} Methode und den übergebenen Objekten formatiert.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @param Die Objekte die der {@link MessageFormat#format(String, Object...)} Methode übergeben werden
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels
	 */
	public String getErrorMessageWithArguments(final int errorCode, final Object... objects) {
		final String value = getLabel(Integer.toString(errorCode), PREFIX_ERROR_CODE, this.errorCodesBundle);
		try {
			return MessageFormat.format(value, objects);
		} catch (final IllegalArgumentException ae) {
			LOG.error("Ungültige Werte übergeben! objects: " + objects, ae);
		}
		return value;
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück. Übergeben wird lediglich der
	 * eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'l_' hier ergänzt wird.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels
	 */
	public String getLabel(final String key) {
		return getLabel(key, PREFIX_LABEL, this.labelsBundle);
	}

	public ResourceBundle getLabelsBundle() {
		return this.labelsBundle;
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück. Übergeben wird lediglich der
	 * eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'l_' hier ergänzt wird. Der String wird zudem noch mit
	 * der {@link MessageFormat#format(String, Object...)} Methode und den übergebenen Objekten formatiert.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @param Die Objekte die der {@link MessageFormat#format(String, Object...)} Methode übergeben werden
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels
	 */
	public String getLabelWithArguments(final String key, final Object... objects) {
		final String value = getLabel(key);
		try {
			return MessageFormat.format(value, objects);
		} catch (final IllegalArgumentException ae) {
			LOG.error("Ungültige Werte übergeben! objects: " + objects, ae);
		}
		return value;
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück, mit einem Doppelpunkt am Ende.
	 * Übergeben wird lediglich der eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'l_' hier ergänzt wird.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels mit Separator
	 */
	public String getLabelWithSeparator(final String key) {
		final String value = getLabel(key, PREFIX_LABEL, this.labelsBundle);
		return value + SEPARATOR;
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück, mit einem Doppelpunkt am Ende.
	 * Übergeben wird lediglich der eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'l_' hier ergänzt wird.
	 * Der String wird zudem noch mit der {@link MessageFormat#format(String, Object...)} Methode und den übergebenen
	 * Objekten formatiert.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel. * @param Die Objekte die der
	 *          {@link MessageFormat#format(String, Object...)} Methode übergeben werden
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels mit Separator
	 */
	public String getLabelWithSeparatorWithArguments(final String key, final Object... objects) {
		final String value = getLabelWithSeparator(key);
		try {
			return MessageFormat.format(value, objects);
		} catch (final IllegalArgumentException ae) {
			LOG.error("Ungültige Werte übergeben! objects" + objects, ae);
		}
		return value;
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück. Übergeben wird lediglich der
	 * eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'msg_' hier ergänzt wird.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @return den Wert zum entsprechenden Beschriftungsschlüssels
	 */
	public String getMessage(final String key) {
		return getLabel(key, PREFIX_MESSAGE, this.labelsBundle);
	}

	/**
	 * Diese Methode gibt den Wert zum entsprechenden Beschriftungsschlüssel zurück. Übergeben wird lediglich der
	 * eigentliche Name des Beschriftungsschlüssels, da das Kürzel 'msg_' hier ergänzt wird. Der String wird zudem noch
	 * mit der {@link MessageFormat#format(String, Object...)} Methode und den übergebenen Objekten formatiert.
	 * 
	 * @param key ist der Name des BeschriftungsSchluessels ohne das vorangestellte Kürzel.
	 * @param Die Objekte die der {@link MessageFormat#format(String, Object...)} Methode übergeben werden
	 * @return den formatierten Wert zum entsprechenden Beschriftungsschlüssels
	 */
	public String getMessageWithArguments(final String key, final Object... objects) {
		final String msg = getLabel(key, PREFIX_MESSAGE, this.labelsBundle);
		try {
			return MessageFormat.format(msg, objects);
		} catch (final IllegalArgumentException ae) {
			LOG.error("Ungültige Werte übergeben! objects: " + objects, ae);
		}
		return msg;
	}

	public void setCurrentLocale(final Locale currentLocale) {
		LOG.debug("this.currentLocale: {}, currentLocale: {}", this.currentLocale, currentLocale);
		this.currentLocale = currentLocale;
		this.labelsBundle = new MultiPropertyResourceBundle(currentLocale, BUNDLE_LABELS, BUNDLE_CUSTOMER_LABELS);
		this.errorCodesBundle = ResourceBundle.getBundle(BUNDLE_ERROR_CODES, currentLocale);
		if (!isChanging()) {
			LOG.debug("syncViews");
			syncViews();
		}
	}

	/**
	 * Diese Methode benachrichtigt alle Observer, dass sich die Daten ge�ndert haben und nun ihre GUI aktualisieren
	 * müssen.
	 */
	protected final void syncViews() {
		setChanged();
		notifyObservers(this);
		clearChanged();
	}

	/**
	 * Diese Methode benachrichtigt alle Observer, dass sich die Daten geändert haben und nun ihre GUI aktualisieren
	 * müssen. über den Parametr kann gesteuert werden, welche GUI Elemente aktualisiert werden sollen. Üblicherweise wird
	 * dazu eine Enumeration im ViewModel erstellt und einer der Werte mit übergeben. In den entsprechenden Forms muss auf
	 * diese Enumeration dann geprüft werden.
	 * 
	 * @param updateFlag Option zur Steuerung der zu aktualisierenden GUI Elemente
	 */
	protected final void syncViews(final long updateFlag) {
		setChanged();
		notifyObservers(this, updateFlag);
		clearChanged();
	}

	/**
	 * Diese Methode prüft, ob ein entsprechender Beschriftungstext verfügbar ist und gibt diesen sofern möglich zurück.
	 * Ist keine Beschriftung vorhanden, so wird als Beschriftungstext der Schlüssel mit seinem Prefix zurückgegeben und
	 * ein Warnhinweis gelogt.
	 * 
	 * @param key der Schlüsselname ohne vorangestelltes Prefix.
	 * @param prefix ein Prefix für den Schlüsselnamen, welches die Art des Labels angibt.
	 * @param bundle Das Bundle aus dem der Key genommen werden soll.
	 * @return Einen Beschriftungstext.
	 */
	private String getLabel(final String key, final String prefix, final ResourceBundle bundle) {
		if ((key == null) || key.isEmpty()) {
			return "";
		}
		final String keyWithPrefix = prefix + key;
		String value = "";
		try {
			value = bundle.getString(keyWithPrefix);
			if ("".equals(value)) {
				LOG.warn("Für den Key " + keyWithPrefix + " wird ein Leerstring übergeben.");
			}
		} catch (final MissingResourceException e) {
			LOG.warn("Für den Key " + keyWithPrefix + " ist der Wert nicht vorhanden.");
			value = "<" + keyWithPrefix + ">";
		} catch (final ClassCastException e) {
			LOG.warn("Für den Key " + keyWithPrefix + " ist der Wert kein <String>.");
			value = "<" + keyWithPrefix + ">";
		}
		return value;
	}
}
