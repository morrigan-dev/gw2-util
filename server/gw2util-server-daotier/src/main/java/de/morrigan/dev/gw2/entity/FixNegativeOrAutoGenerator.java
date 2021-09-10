package de.morrigan.dev.gw2.entity;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import de.morrigan.dev.gw2.entity.interfaces.IEntity;

public class FixNegativeOrAutoGenerator extends IdentityGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor s, Object obj) {
		long id = ((IEntity) obj).getId();
		Serializable result;
		if (id < 0) {
			result = id;
		} else {
			result = super.generate(s, obj);
		}
		return result;
	}
}
