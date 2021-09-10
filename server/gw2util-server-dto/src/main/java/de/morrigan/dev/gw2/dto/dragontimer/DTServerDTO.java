package de.morrigan.dev.gw2.dto.dragontimer;

import java.io.Serializable;
import java.util.UUID;

/**
 * Dieses DTO beinhaltet Daten für die Serverauswahl im Bereich DragonTimer für den Client.
 * 
 * @author morrigan
 */
public class DTServerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Eindeutige UUID des DTOs, falls die ID gleich 0 sein sollte */
	private String uuid = UUID.randomUUID().toString();

	/** ID des Datensatzes */
	private long id;

	/** Name des Servers */
	private String name;

	public DTServerDTO(long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
		DTServerDTO other = (DTServerDTO) obj;
		if (this.id != other.id) {
			return false;
		}
		if ((this.id == 0) && (!this.uuid.equals(other.uuid))) {
			return false;
		}
		return true;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
		if (this.id == 0) {
			result = (prime * result) + ((this.uuid == null) ? 0 : this.uuid.hashCode());
		}
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", name: ").append(this.name);
		sb.append("]");
		return sb.toString();
	}
}
