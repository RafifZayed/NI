package com.ni.auth.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ni.common.exception.NIException;

/**
 * 
 * @author rafifzayed
 *
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AuthServiceException extends NIException {

	private static final long serialVersionUID = 4510799483713328539L;

	public AuthServiceException(String code, String message) {
		super(code, message);
	}

	public AuthServiceException(String code, Throwable cause) {
		super(code, cause);
	}
}
