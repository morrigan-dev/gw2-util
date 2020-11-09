package de.morrigan.dev.gw2.entity.interfaces;

/**
 * Dieses Interface muss von allen ableitenden Interfaces implmentiert werden, die zu einer Entity gehören bzw. von den
 * Entities, die kein abgeleitetes Interface besitzen.
 * 
 * @author morrigan
 */
public interface IEntity {

	/**
	 * @return die ID dieses Datensatzes.
	 */
	public long getId();

	/**
	 * Setzt die ID dieses Datensatzes.
	 * 
	 * @param id eine ID
	 */
	public void setId(long id);
}
