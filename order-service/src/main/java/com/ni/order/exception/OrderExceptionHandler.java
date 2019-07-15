package com.ni.order.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ni.common.exception.ErrorDetails;

/**
 * 
 * @author rafifzayed
 *
 */
@ControllerAdvice
public class OrderExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderExceptionHandler.class);

	@ExceptionHandler(OrderException.class)
	public final ResponseEntity<Object> handleOrderException(OrderException ex, WebRequest request) {
		logException(ex);
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false),
				ex.getCode());
		return new ResponseEntity<Object>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
		logException(ex);
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false),
				ErrorCode.UNKNOWN_ERROR);
		return new ResponseEntity<Object>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void logException(Exception exp) {
		LOGGER.error(exp.getMessage(), exp);
	}
}
