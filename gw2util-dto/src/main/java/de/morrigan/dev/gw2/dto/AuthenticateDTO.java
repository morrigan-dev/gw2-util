package de.morrigan.dev.gw2.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dto.common.Protocol;

/**
 * Dieses DTO ist für die Autorisierung der Java Clients am Server da. Es wird in beide Richtungen verwendet. Der Client
 * übermittelt seine Logindaten und bekommt vom Server eine SessionId übermittel, mit der er in Zukunft sich beim Server
 * melden kann, um Aktionen auszuführen.
 * 
 * @author morrigan
 */
public class AuthenticateDTO extends ResultDTO {

	public enum ACTION {
		LOGIN, SEND_SESSION
	}

	private static final long serialVersionUID = 1L;

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticateDTO.class);

	/** Eindeutiger Schlüssel der Session, mit der der Client sich am Server meldet */
	private String sessionKey = "";

	/** Identifikationsmerkmal des Gerätes von dem aus Zugriff genommen wurde (z.B. MAC) */
	private String mac = "";

	/** IP Adresse des Clients von dem aus Zugriff genommen wirde */
	private String ip = "";

	/** Benutzername eines Benutzers, der sich anmelden möchte */
	private String userName = "";

	/** Passwort eines Benutzers, der sich anmelden möchte */
	private String password = "";

	/** Aktion, die über dieses DTO durchgeführt wird. (Kontrollmechanismus) */
	private ACTION action = ACTION.SEND_SESSION;

	/** Konstruktor für JSON */
	public AuthenticateDTO() {
		super(new Protocol());
	}

	/**
	 * Dieser Konstrukor wird bei Fehlerfällen benutzt, da lediglich das Fehlerprotokoll gesetzt werden muss.
	 * 
	 * @param protocol Enthält Protokoldaten zu diesem DTO bzw. der zuletzt durchgeführten Aktion. (not null)
	 * @author morrigan
	 */
	public AuthenticateDTO(final Protocol protocol) {
		super(protocol);
	}

	public ACTION getAction() {
		assert invariant();
		return this.action;
	}

	public String getIp() {
		return this.ip;
	}

	public String getMac() {
		assert invariant();
		return this.mac;
	}

	public String getPassword() {
		assert invariant();
		return this.password;
	}

	public String getSessionKey() {
		assert invariant();
		return this.sessionKey;
	}

	public String getUserName() {
		assert invariant();
		return this.userName;
	}

	public void setAction(final ACTION action) {
		assert invariant();
		this.action = action;
		assert invariant();
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setMac(final String mac) {
		assert invariant();
		this.mac = mac;
		assert invariant();
	}

	public void setPassword(final String password) {
		assert invariant();
		this.password = password;
		assert invariant();
	}

	public void setSessionKey(final String sessionKey) {
		assert invariant();
		this.sessionKey = sessionKey;
		assert invariant();
	}

	public void setUserName(final String userName) {
		assert invariant();
		this.userName = userName;
		assert invariant();
	}

	@Override
	public String toString() {
		assert invariant();
		final StringBuilder sb = new StringBuilder(super.toString());
		sb.append(", sessionKey: ").append(this.sessionKey);
		sb.append(", mac: ").append(this.mac);
		sb.append(", ip: ").append(this.ip);
		sb.append(", userName: ").append(this.userName);
		sb.append(", action: ").append(this.action);
		sb.append("]");
		assert invariant();
		return sb.toString();
	}

	/**
	 * Diese Methode prüft den internen Zustand dieses Objekts. Ist ein ungültiger Zustand vorhanden, so wird ein
	 * entsprechender Fehler gelogt.
	 * 
	 * @return true, falls alle Invarianten erfüllt sind. Ansonsten false.
	 * @author morrigan
	 */
	private boolean invariant() {
		final StringBuilder errMsg = new StringBuilder();
		try {
			// Null-Checks
			errMsg.append(this.sessionKey == null ? "sessionKey darf nicht null sein!" : "");
			errMsg.append(this.mac == null ? "mac darf nicht null sein!" : "");
			errMsg.append(this.ip == null ? "ip darf nicht null sein!" : "");
			errMsg.append(this.userName == null ? "userName darf nicht null sein!" : "");
			errMsg.append(this.password == null ? "password darf nicht null sein!" : "");
			errMsg.append(this.action == null ? "action darf nicht null sein!" : "");

			if (errMsg.length() > 0) {
				throw new IllegalStateException(errMsg.toString());
			}
			return true;
		} catch (final IllegalStateException e) {
			LOG.error(e.getMessage(), e);
		}
		return false;
	}

}
