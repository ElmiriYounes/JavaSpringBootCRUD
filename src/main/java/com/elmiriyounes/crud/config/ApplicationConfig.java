package com.elmiriyounes.crud.config;

import com.elmiriyounes.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

	@Autowired
	private UserRepository userRepository;

	/**
	 * By default, userDetailsService.loadByUsername uses findByUsername = we have to edit it to findByEmail
	 * because in this project "email" is a unique key of the database
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	/**
	 * in this project, we will use the authenticationManager to authenticate the user in the context security
	 * UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail().toLowerCase(), userLoginDTO.getPassword());
	 * Authentication authentication = authenticationManager.authenticate(auth);
	 * SecurityContextHolder.getContext().setAuthentication(authentication);
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
