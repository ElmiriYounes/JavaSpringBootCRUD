package com.elmiriyounes.crud.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Configuration
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		/**
		 * When logged out successfully, redirect to login page with message logged out
		 */
		if(authentication != null){
			HttpSession session = request.getSession();
			session.setAttribute("successLogoutHandled", "true");
		}

		response.sendRedirect("/login");
	}
}
