package com.ni.product.exception;

/**
 * 
 * @author rafifzayed
 *
 */
//4 digit start with 4, 0000 for unknown error
public class ErrorCode {

	private ErrorCode() {
	}
	public static String UNKNOWN_ERROR = "0000";
	public static String FAILED_TO_SAVE_PRODUCT = "4001";
	public static String FAILED_TO_GET_PRODUCT = "4002";
	public static String FAILED_TO_GET_PRODUCT_NAME = "4003";
	public static String FAILED_TO_ADD_PRODUCT_ITM = "4004";
	public static String FAILED_TO_BOOK_PRODUCT_ITM = "4005";
	public static String PRODUCT_NOT_FOUND = "4006";
	public static String FAILED_TO_GET_PRODUCTS = "4007";
	public static String VALIDATION_ERROR= "4008";
}
