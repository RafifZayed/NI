package com.ni.common.security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

// To use this class outside. You have to 
	// 1. Define it as a bean, either by adding @Component or use @Bean to instantiate an object from it
	// 2. Use the @Autowire to ask spring to auto create it for you, and inject all the values.
/**
 * 
 * @author rafifzayed
 *
 */
 @Getter 		// lombok will create getters auto.
 @ToString		// [IMP] You need to install lombok jar file: https://stackoverflow.com/a/11807022
public class JwtConfig {

	// Spring doesn't inject/autowire to "static" fields. 
	// Link: https://stackoverflow.com/a/6897406
	@Value("${security.jwt.uri:/auth/login}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret;
    
    // In case you want to use plain getters instead of lombok.
	public String getUri() {
		return Uri;
	}

	public String getHeader() {
		return header;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public String getSecret() {
		return secret;
	}
    
}
