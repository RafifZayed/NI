package com.ni.product.exception;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * 
 * @author rafifzayed
 *
 */

@Component
@Order(-2)
public class ProductExceptionHandler extends AbstractErrorWebExceptionHandler {

	public ProductExceptionHandler(GlobalErrorAttributes<Exception> g, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
		super(g, new ResourceProperties(), applicationContext);
		super.setMessageWriters(serverCodecConfigurer.getWriters());
		super.setMessageReaders(serverCodecConfigurer.getReaders());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {

		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

		Map<String, Object> errorPropertiesMap = getErrorAttributes(request, false);

		return ServerResponse
				.status(ErrorCode.PRODUCT_NOT_FOUND.equals(errorPropertiesMap.get("errorCode")) ? HttpStatus.NOT_FOUND
						: ErrorCode.VALIDATION_ERROR.equals(errorPropertiesMap.get("errorCode")) ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(errorPropertiesMap));
	}

}
