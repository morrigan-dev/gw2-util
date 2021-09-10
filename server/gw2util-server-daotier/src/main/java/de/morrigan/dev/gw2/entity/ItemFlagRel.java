package de.morrigan.dev.gw2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.morrigan.dev.gw2.dto.common.enums.Flag;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;

@Entity
@Table(name = "ItemFlagRel")
public class ItemFlagRel implements IEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	@Column(name = "Flag")
	@Enumerated(EnumType.STRING)
	private Flag flag;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ItemID")
	private Item item;

	public ItemFlagRel() {
		super();
	}

	public ItemFlagRel(Flag flag, Item item) {
		super();
		this.id = 0;
		this.flag = flag;
		this.item = item;
	}

	public Flag getFlag() {
		return this.flag;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public Item getItem() {
		return this.item;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", flag: ").append(this.flag);
		sb.append(", item id: ").append(this.item.getId());
		sb.append("]");
		return sb.toString();
	}
}
