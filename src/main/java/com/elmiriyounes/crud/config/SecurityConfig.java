package com.elmiriyounes.crud.config;

import com.elmiriyounes.crud.filter.MyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Autowired
	private MyFilter myFilter;
	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
				.csrf()
				.disable()
				.authorizeRequests()
				.requestMatchers("/register", "/login").permitAll()
				.requestMatchers("/css/**","/js/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.logoutSuccessHandler(myLogoutSuccessHandler)
				.and()
				.addFilterBefore(myFilter, BasicAuthenticationFilter.class);

		return http.build();
	}
}
