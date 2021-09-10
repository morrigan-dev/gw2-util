package de.morrigan.dev.gw2.dto;

import java.io.Serializable;

import de.morrigan.dev.gw2.utils.enums.ItemRarity;

public class ItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID des Datensatzes */
	private long id;

	private long dataId;

	private String iconURL = "";

	private String name = "";

	private ItemRarity rarity;

	private ItemPriceDTO lastPrice;

	public ItemDTO() {
		super();
	}

	public ItemDTO(long id, long dataId, String iconURL, String name, ItemRarity rarity, ItemPriceDTO lastPrice) {
		super();
		this.id = id;
		this.dataId = dataId;
		this.iconURL = iconURL;
		this.name = name;
		this.rarity = rarity;
		this.lastPrice = lastPrice;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ItemDTO other = (ItemDTO) obj;
		if (this.dataId != other.dataId) {
			return false;
		}
		if (this.iconURL == null) {
			if (other.iconURL != null) {
				return false;
			}
		} else if (!this.iconURL.equals(other.iconURL)) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		if (this.lastPrice == null) {
			if (other.lastPrice != null) {
				return false;
			}
		} else if (!this.lastPrice.equals(other.lastPrice)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.rarity != other.rarity) {
			return false;
		}
		return true;
	}

	public long getDataId() {
		return this.dataId;
	}

	public String getIconURL() {
		return this.iconURL;
	}

	public long getId() {
		return this.id;
	}

	public ItemPriceDTO getLastPrice() {
		return this.lastPrice;
	}

	public String getName() {
		return this.name;
	}

	public ItemRarity getRarity() {
		return this.rarity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.dataId ^ (this.dataId >>> 32));
		result = (prime * result) + ((this.iconURL == null) ? 0 : this.iconURL.hashCode());
		result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
		result = (prime * result) + ((this.lastPrice == null) ? 0 : this.lastPrice.hashCode());
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		result = (prime * result) + ((this.rarity == null) ? 0 : this.rarity.hashCode());
		return result;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastPrice(ItemPriceDTO lastPrice) {
		this.lastPrice = lastPrice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRarity(ItemRarity rarity) {
		this.rarity = rarity;
	}

}
