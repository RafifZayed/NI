package com.ni.order.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author rafifzayed
 *
 */
@Aspect
@Configuration
public class LoggingAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)" + "|| @annotation(org.springframework.web.bind.annotation.PostMapping)"
			+ "|| @annotation(org.springframework.web.bind.annotation.PutMapping)")
	public Object logServiceTime(ProceedingJoinPoint joinPoint) throws Throwable {

		LOGGER.info("Request: " + getRequestBody(joinPoint));
		long startTime = System.currentTimeMillis();
		Object res = joinPoint.proceed();
		long timeTaken = System.currentTimeMillis() - startTime;
		LOGGER.info("Response: " + objectMapper.writeValueAsString(res));
		LOGGER.info("Time Taken by {} is {}", joinPoint, timeTaken);
		return res;

	}

	private String getRequestBody(ProceedingJoinPoint joinPoint) throws JsonProcessingException {
		Object[] args = joinPoint.getArgs();
		MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
		Method method = methodSignature.getMethod();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		StringBuilder builder = new StringBuilder();
		for (int argIndex = 0; argIndex < args.length; argIndex++) {
			for (Annotation annotation : parameterAnnotations[argIndex]) {
				if (annotation instanceof RequestParam) {
					RequestParam requestParam = (RequestParam) annotation;
					if (!"accessToken".equals(requestParam.value()))
						continue;
					builder = builder.append("  ").append(requestParam.value()).append(" = ").append(args[argIndex]);
				} else if (annotation instanceof RequestBody) {
					builder = builder.append(args[argIndex]);
				} else if (annotation instanceof PathVariable) {
					PathVariable pathVariable = (PathVariable) annotation;
					builder = builder.append("  ").append(pathVariable.value()).append(" = ").append(objectMapper.writeValueAsString(args[argIndex]));
				}
			}
		}

		return builder.toString();
	}

}
