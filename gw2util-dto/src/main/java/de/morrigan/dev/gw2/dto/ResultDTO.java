package de.morrigan.dev.gw2.dto;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

import de.morrigan.dev.gw2.dto.common.Protocol;

public class ResultDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Enthält Protokoldaten zu diesem DTO bzw. der zuletzt durchgeführten Aktion */
	private Protocol protocol;

	/** DTO mit Authentifizierungsdaten */
	private AuthenticateDTO auth;

	public ResultDTO() {
		super();
	}

	public ResultDTO(final Protocol protocol) {
		super();
		Validate.notNull(protocol, "Der Parameter (protocol) darf nicht null sein!");

		this.protocol = protocol;
	}

	public AuthenticateDTO getAuth() {
		return this.auth;
	}

	public Protocol getProtocol() {
		return this.protocol;
	}

	public void setAuth(AuthenticateDTO auth) {
		this.auth = auth;
	}

	public void setProtocol(final Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[protocol: ").append(this.protocol);
		sb.append("[auth: ").append(this.auth);
		return sb.toString();
	}
}
