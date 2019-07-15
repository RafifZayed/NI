package com.ni.order.exception;

/**
 * 
 * @author rafifzayed
 *
 */
//4 digit start with 1, 0000 for unknown error
public class ErrorCode {

	private ErrorCode() {
	}
	public static String UNKNOWN_ERROR = "0000";
	public static String FAILED_TO_SAVE_ORDER = "1000";
	public static String FAILED_TO_GET_CUSTOMER_ORDER = "1001";
	public static String FAILED_TO_GET_ORDER = "1002";
	public static String FAILED_TO_BOOK_ORDER_PRODUCT= "1003";
}
