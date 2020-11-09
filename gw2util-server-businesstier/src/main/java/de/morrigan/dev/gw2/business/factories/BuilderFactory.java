package de.morrigan.dev.gw2.business.factories;

import java.io.Serializable;

import org.jboss.logging.Logger;

import de.morrigan.dev.gw2.business.builder.interfaces.IItemBuilder;
import de.morrigan.dev.gw2.business.builder.interfaces.IUserBuilder;
import de.morrigan.dev.gw2.business.builder.interfaces.IWaypointBuilder;

/**
 * Über diese Factory können spezielle Builder Klassen erstellt werden, die zu jeder Entity bzw. DTO nützliche Methoden
 * bereitstellen um das Eine in das Andere umzuwandeln.
 * 
 * @author morrigan
 */
public abstract class BuilderFactory implements Serializable {

	private static final long serialVersionUID = 4029979415572071481L;

	/** Logger für Debug/Fehlerausgaben */
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(BuilderFactory.class);

	/** Einzige Instanz dieser Factory */
	private static final BuilderFactory INSTANCE = new NoEJBBuilderFactory();

	/**
	 * @return die einzige Instanz dieser Factory.
	 */
	public static BuilderFactory getInstance() {
		return INSTANCE;
	}

	protected BuilderFactory() {
		super();
	}

	/** @return den Builder für die Items */
	public abstract IItemBuilder getItemBuilder();

	/** @return den Builder für die Benutzer */
	public abstract IUserBuilder getUserBuilder();

	/** @return den Builder für die Wegpunkte */
	public abstract IWaypointBuilder getWaypointBuilder();

}
