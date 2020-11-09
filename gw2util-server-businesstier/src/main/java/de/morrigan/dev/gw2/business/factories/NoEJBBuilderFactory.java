package de.morrigan.dev.gw2.business.factories;

import de.morrigan.dev.gw2.business.builder.ItemBuilder;
import de.morrigan.dev.gw2.business.builder.UserBuilder;
import de.morrigan.dev.gw2.business.builder.WaypointBuilder;
import de.morrigan.dev.gw2.business.builder.interfaces.IItemBuilder;
import de.morrigan.dev.gw2.business.builder.interfaces.IUserBuilder;
import de.morrigan.dev.gw2.business.builder.interfaces.IWaypointBuilder;

public class NoEJBBuilderFactory extends BuilderFactory {

	private static final long serialVersionUID = 8621235849894018979L;

	@Override
	public IItemBuilder getItemBuilder() {
		return new ItemBuilder();
	}

	@Override
	public IUserBuilder getUserBuilder() {
		return new UserBuilder();
	}

	@Override
	public IWaypointBuilder getWaypointBuilder() {
		return new WaypointBuilder();
	}

}
