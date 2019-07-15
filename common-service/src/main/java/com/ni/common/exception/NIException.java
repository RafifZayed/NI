package com.ni.common.exception;

/**
 * 
 * @author rafifzayed
 *
 */
public class NIException extends Exception {

	private static final long serialVersionUID = -884978586863119004L;
	protected String code;

	public String getCode() {
		return code;
	}

	public NIException(String code, String message) {
		super(message);
		this.code = code;
	}

	public NIException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public NIException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
}
