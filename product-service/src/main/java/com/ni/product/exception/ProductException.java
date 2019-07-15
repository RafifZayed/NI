package com.ni.product.exception;

import com.ni.common.exception.NIException;

/**
 * 
 * @author rafifzayed
 *
 */
public class ProductException extends NIException {

	private static final long serialVersionUID = 1920215219489552960L;

	public ProductException(String code, String message) {
		super(code, message);
	}

	public ProductException(String code, Throwable cause) {
		super(code, cause);

	}

}
