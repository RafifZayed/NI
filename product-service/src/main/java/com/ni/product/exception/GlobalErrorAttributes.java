package com.ni.product.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 * @author rafifzayed
 *
 */

@Component
public class GlobalErrorAttributes<T extends Throwable> extends DefaultErrorAttributes {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductExceptionHandler.class);

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
		Map<String, Object> superErrorAttributes = super.getErrorAttributes(request, includeStackTrace);
		return addErrorDetails(superErrorAttributes, request);
	}

	protected Map<String, Object> addErrorDetails(Map<String, Object> superErrorAttributes, ServerRequest request) {
		
		Throwable ex = getError(request);	
		logException(ex);
		Map<String, Object> errorAttributes = new HashMap<>();
		errorAttributes.put("timestamp", new Date());
		errorAttributes.put("message", ex.getMessage());
		errorAttributes.put("details",request.path());
		if (ex instanceof ProductException) {
			errorAttributes.put("errorCode", ((ProductException) ex).getCode());
		}else if(ex instanceof WebExchangeBindException || ex instanceof ResponseStatusException){
			errorAttributes.put("errorCode", ErrorCode.VALIDATION_ERROR);
		}else {
			errorAttributes.put("errorCode", ErrorCode.UNKNOWN_ERROR);
		}
		
		return errorAttributes;
	}

	private void logException(Throwable exp) {
		LOGGER.error(exp.getMessage(), exp);
	}
}