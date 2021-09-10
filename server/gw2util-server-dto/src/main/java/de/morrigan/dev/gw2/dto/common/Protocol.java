package de.morrigan.dev.gw2.dto.common;

import java.io.Serializable;
import java.util.Date;

public class Protocol implements Serializable {

	public enum SEVERITY {
		INFO, WARN, ERROR
	}

	private static final long serialVersionUID = 1L;

	private SEVERITY severity = SEVERITY.INFO;

	private int errorCode;

	private String errorMsg = "";

	private Date currentDate;

	public Protocol() {
		super();
	}

	public Protocol(final SEVERITY severity, final int errorCode, final String errorMsg) {
		super();

		this.severity = severity;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.currentDate = new Date();
	}

	public Date getCurrentDate() {
		return this.currentDate;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public SEVERITY getSeverity() {
		return this.severity;
	}

	public void setCurrentDate(final Date currentDate) {
		this.currentDate = currentDate;
	}

	public void setErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMsg(final String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setSeverity(final SEVERITY severity) {
		this.severity = severity;
	}
}
