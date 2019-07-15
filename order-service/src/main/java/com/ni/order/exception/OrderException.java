package com.ni.order.exception;

import com.ni.common.exception.NIException;

/**
 * 
 * @author rafifzayed
 *
 */
public class OrderException extends NIException {

	private static final long serialVersionUID = 7239073089575319653L;

	public OrderException(String code, String message) {
		super(code, message);
	}

	public OrderException(String code, Throwable cause) {
		super(code, cause);
	}
}
