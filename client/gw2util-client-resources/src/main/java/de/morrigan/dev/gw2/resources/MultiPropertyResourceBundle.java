package de.morrigan.dev.gw2.resources;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class MultiPropertyResourceBundle extends ResourceBundle {

	private static class ResourceBundleEnumeration implements Enumeration<String> {

		Set<String> set;
		Iterator<String> iterator;
		Enumeration<String> enumeration;

		String next = null;

		/**
		 * Constructs a resource bundle enumeration.
		 * 
		 * @param set an set providing some elements of the enumeration
		 * @param enumeration an enumeration providing more elements of the enumeration. enumeration may be null.
		 */
		ResourceBundleEnumeration(final Set<String> set, final Enumeration<String> enumeration) {
			this.set = set;
			this.iterator = set.iterator();
			this.enumeration = enumeration;
		}

		@Override
		public boolean hasMoreElements() {
			if (this.next == null) {
				if (this.iterator.hasNext()) {
					this.next = this.iterator.next();
				} else if (this.enumeration != null) {
					while ((this.next == null) && this.enumeration.hasMoreElements()) {
						this.next = this.enumeration.nextElement();
						if (this.set.contains(this.next)) {
							this.next = null;
						}
					}
				}
			}
			return this.next != null;
		}

		@Override
		public String nextElement() {
			if (hasMoreElements()) {
				final String result = this.next;
				this.next = null;
				return result;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private final Map lookup = new HashMap();

	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(MultiPropertyResourceBundle.class);

	public MultiPropertyResourceBundle(final Locale locale, final String... bundles) {

		for (final String bundle : bundles) {
			try {
				final ResourceBundle resBundle = ResourceBundle.getBundle(bundle, locale);
				final Map tmpMap = new HashMap();
				final Enumeration bundleKeys = resBundle.getKeys();
				while (bundleKeys.hasMoreElements()) {
					final String key = (String) bundleKeys.nextElement();
					final String value = resBundle.getString(key);
					this.lookup.put(key, value);
				}
			} catch (MissingResourceException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public Enumeration<String> getKeys() {
		return new ResourceBundleEnumeration(this.lookup.keySet(), (this.parent != null) ? this.parent.getKeys() : null);
	}

	public void initKundenname(final String kundenname) {
		final String KUNDEN_NAME_PLATZHALTER = "{kundenname}";

		final Set<Map.Entry<String, String>> entrySet = this.lookup.entrySet();
		final Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			final Map.Entry<String, String> entry = iterator.next();
			String value = entry.getValue();

			while (value.contains(KUNDEN_NAME_PLATZHALTER)) {
				value = value.replace(KUNDEN_NAME_PLATZHALTER, kundenname);
				entry.setValue(value);
			}
		}

	}

	@Override
	protected Object handleGetObject(final String key) {
		return this.lookup.get(key);
	}
}
