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

import de.morrigan.dev.gw2.dto.common.enums.GameType;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;

@Entity
@Table(name = "ItemGameTypeRel")
public class ItemGameTypeRel implements IEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	@Column(name = "GameType")
	@Enumerated(EnumType.STRING)
	private GameType gameType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ItemID")
	private Item item;

	public ItemGameTypeRel() {
		super();
	}

	public ItemGameTypeRel(GameType gameType, Item item) {
		super();
		this.id = 0;
		this.gameType = gameType;
		this.item = item;
	}

	public GameType getGameType() {
		return this.gameType;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public Item getItem() {
		return this.item;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
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
		sb.append(", gameType: ").append(this.gameType);
		sb.append(", item id: ").append(this.item.getId());
		sb.append("]");
		return sb.toString();
	}
}
