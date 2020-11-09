package de.morrigan.dev.gw2.business.factories;

import java.io.Serializable;

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

	/** Einzige Instanz dieser Factory */
	private static BuilderFactory instance;

	/**
	 * @return die einzige Instanz dieser Factory.
	 */
	public static BuilderFactory getInstance() {
		if (instance == null) {
			instance = new NoEJBBuilderFactory();
		}
		return instance;
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
