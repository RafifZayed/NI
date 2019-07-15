package com.ni.auth.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 
 * @author rafifzayed
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder encoder;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// hard coding the UserBean. All passwords must be encoded.
		final List<UserBean> users = Arrays.asList(new UserBean(1, "user", encoder.encode("12345"), "USER"),
				new UserBean(2, "admin", encoder.encode("12345"), "ADMIN"));
		for (UserBean appUser : users) {
			if (appUser.getUsername().equals(username)) {
				List<GrantedAuthority> grantedAuthorities = AuthorityUtils
						.commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());
				return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
			}
		}

		// If user not found. Throw this exception.
		throw new UsernameNotFoundException("Username: " + username + " not found");

	}

	/*
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException {
	 * 
	 * UserBean user = getUser(username); if (user == null) { // If user not found.
	 * Throw this exception. throw new UsernameNotFoundException("Username: " +
	 * username + " not found"); }
	 * 
	 * // get user roles List<GrantedAuthority> grantedAuthorities = AuthorityUtils
	 * .commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
	 * 
	 * return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
	 * 
	 * }
	 * 
	 * private UserBean getUser(String username) throws UsernameNotFoundException {
	 * HttpHeaders headers = new HttpHeaders(); headers.set("Accept",
	 * MediaType.APPLICATION_JSON_VALUE); HttpEntity<?> entity = new
	 * HttpEntity<>(headers); ResponseEntity<UserBean> response =
	 * restTemplate.exchange(userServiceUrl, HttpMethod.GET, entity, UserBean.class,
	 * username); // TODO check response status if (response == null) { throw new
	 * UsernameNotFoundException("Failed to get user object"); }
	 * 
	 * return response.getBody();
	 * 
	 * }
	 */

	private static class UserBean {

		private int id;
		private String username;
		private String password;
		private String role;

		public UserBean(int id, String username, String password, String role) {
			this.id = id;
			this.username = username;
			this.password = password;
			this.role = role;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

	}

}