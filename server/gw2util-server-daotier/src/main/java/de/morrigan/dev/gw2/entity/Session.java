package de.morrigan.dev.gw2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.morrigan.dev.gw2.entity.interfaces.IEntity;

@Entity
@Table(name = "Session")
public class Session implements IEntity {

	// ========================================================================
	// Ab hier kommen die Member, die durch die Interfaces resultieren
	// ========================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	// ========================================================================
	// Ab hier kommen die eigentlichen Datenspalten
	// ========================================================================

	/** Eindeutiger Schlüssel der Session, mit der der Client sich am Server meldet */
	@Column(name = "SessionKey", length = 255, nullable = false, unique = true)
	private String sessionKey;

	/** Zeitpunkt des letzten Zugriffs */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TimeOfLastAccess", nullable = false)
	private Date timeOfLastAccess;

	/** Zeitpunkt der letzten Authorisierungsbestätigung */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TimeOfLastAuth", nullable = false)
	private Date timeOfLastAuth;

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	/** Benutzer zu dem diese Session gehört */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserID", nullable = false)
	private User user;

	/** Konstruktor für Hibernate */
	public Session() {
		super();
	}

	/**
	 * Konstruktor zur Erstellung einer neuen Session.
	 * 
	 * @param sessionKey eindeutiger Schlüssel der Session, mit der der Client sich am Server meldet. (not null, unique)
	 * @param timeOfLastAccess Zeitpunkt des letzten Zugriffs. (not null)
	 * @param timeOfLastAuth Zeitpunkt der letzten Authorisierungsbestätigung. (not null)
	 * @param user Benutzer zu dem diese Session gehört. (not null)
	 */
	public Session(String sessionKey, Date timeOfLastAccess, Date timeOfLastAuth, User user) {
		super();
		this.id = 0;
		this.sessionKey = sessionKey;
		this.timeOfLastAccess = timeOfLastAccess;
		this.timeOfLastAuth = timeOfLastAuth;
		this.user = user;
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
		final Session other = (Session) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public String getSessionKey() {
		return this.sessionKey;
	}

	public Date getTimeOfLastAccess() {
		return this.timeOfLastAccess;
	}

	public Date getTimeOfLastAuth() {
		return this.timeOfLastAuth;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	@Override
	public void setId(final long id) {
		this.id = id;
	}

	public void setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public void setTimeOfLastAccess(final Date timeOfLastAccess) {
		this.timeOfLastAccess = timeOfLastAccess;
	}

	public void setTimeOfLastAuth(final Date timeOfLastAuth) {
		this.timeOfLastAuth = timeOfLastAuth;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", sessionKey: ").append(this.sessionKey);
		sb.append(", timeOfLastAccess: ").append(this.timeOfLastAccess);
		sb.append(", timeOfLastAuth: ").append(this.timeOfLastAuth);
		sb.append(", user: ").append(this.user);
		sb.append("]");
		return sb.toString();
	}
}
