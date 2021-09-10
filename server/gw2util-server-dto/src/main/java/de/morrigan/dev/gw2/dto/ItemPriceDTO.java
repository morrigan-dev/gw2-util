package de.morrigan.dev.gw2.dto;

import java.util.Date;

import de.morrigan.dev.gw2.dto.common.Protocol;

public class ItemPriceDTO extends ResultDTO {

	private static final long serialVersionUID = 1L;

	/** ID des Datensatzes */
	private long id;

	private int sell;

	private int buy;

	private Date updated;

	public ItemPriceDTO() {
		super();
	}

	public ItemPriceDTO(long id, int sell, int buy, Date updated) {
		super();
		this.id = id;
		this.sell = sell;
		this.buy = buy;
		this.updated = updated;
	}

	public ItemPriceDTO(Protocol protocol) {
		super(protocol);
	}

	public int getBuy() {
		return this.buy;
	}

	public long getId() {
		return this.id;
	}

	public int getSell() {
		return this.sell;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setBuy(int buy) {
		this.buy = buy;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSell(int sell) {
		this.sell = sell;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
