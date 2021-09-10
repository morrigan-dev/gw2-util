package de.morrigan.dev.gw2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.morrigan.dev.gw2.entity.interfaces.IActiveStateEntity;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

@Entity
@Table(name = "Rights")
public class Right implements IEntity, IActiveStateEntity {

	// ========================================================================
	// Ab hier kommen die Member, die durch die Interfaces resultieren
	// ========================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	/** Gibt den Aktivstatus des Datensatzes an */
	@Enumerated(EnumType.STRING)
	@Column(name = "ActiveState", length = 50, nullable = false)
	private ActiveState activeState;

	// ========================================================================
	// Ab hier kommen die eigentlichen Datenspalten
	// ========================================================================

	/** Key für den Programmcode */
	@Column(name = "RightKey", length = 255, nullable = false, unique = true)
	private String rightKey = "";

	/** Key für dan Labelkey */
	@Column(name = "LabelKey", length = 255, nullable = false, unique = true)
	private String labelKey = "";

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	// Es gibt eine ManyToMany Relation mit Usergroup

	/** Konstruktor für Hibernate */
	public Right() {
		super();
	}
	
	public Right(ActiveState activeState, String rightKey, String labelKey) {
		super();
		this.activeState = activeState;
		this.rightKey = rightKey;
		this.labelKey = labelKey;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Right other = (Right) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public ActiveState getActiveState() {
		return this.activeState;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public String getLabelKey() {
		return this.labelKey;
	}

	public String getRightKey() {
		return this.rightKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	@Override
	public void setActiveState(final ActiveState activeState) {
		this.activeState = activeState;
	}

	@Override
	public void setId(final long id) {
		this.id = id;
	}

	public void setLabelKey(final String labelKey) {
		this.labelKey = labelKey;
	}

	public void setRightKey(final String rightKey) {
		this.rightKey = rightKey;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", activeState: ").append(this.activeState);
		sb.append(", rightKey: ").append(this.rightKey);
		sb.append(", labelKey: ").append(this.labelKey);
		sb.append("]");
		return sb.toString();
	}
}
