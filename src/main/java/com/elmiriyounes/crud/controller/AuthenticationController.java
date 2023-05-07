package com.elmiriyounes.crud.controller;

import com.elmiriyounes.crud.Service.UserService;
import com.elmiriyounes.crud.Utils.UserUtil;
import com.elmiriyounes.crud.dto.UserLoginDTO;
import com.elmiriyounes.crud.dto.UserRegisterDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String register(Model model, HttpServletRequest request, HttpSession session){
		model.addAttribute("userForm", new UserRegisterDTO());

		/**
		 * If we are logged in, when going to register page = we logged out automatically
		 */
		if(session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY) != null){
			model.addAttribute("alertType", "success");
			model.addAttribute("alerts", Arrays.asList("Logged out successfully"));

			SecurityContextHolder.clearContext();
			session.invalidate();
		}

		return "register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("userForm") UserRegisterDTO userRegisterDTO, Model model, HttpSession session){

		List<String> alerts = new ArrayList<>();

		if(!UserUtil.isValidEmail(userRegisterDTO.getEmail().toLowerCase())){
			alerts.add("Invalid email");
		}

		if(!UserUtil.matchPasswords(userRegisterDTO.getPassword(), userRegisterDTO.getConfirmPassword())){
			alerts.add("Passwords do not match");
		}

		if(!alerts.isEmpty()){
			model.addAttribute("alertType", "danger");
			model.addAttribute("alerts", alerts);
		}else if(userService.ifUserExists(userRegisterDTO.getEmail().toLowerCase())){
			alerts.add("Account already exists");
			model.addAttribute("alertType", "danger");
			model.addAttribute("alerts", alerts);
		}else if(userService.userRegistered(userRegisterDTO)){
			alerts.add("Account registered successfully");
			model.addAttribute("alertType", "success");
			model.addAttribute("alerts", alerts);
		}else {
			alerts.add("Registration failed, try again");
			model.addAttribute("alertType", "danger");
			model.addAttribute("alerts", alerts);
		}

		return "register";
	}

	@GetMapping("/login")
	public String authenticate(Model model, HttpSession session){
		model.addAttribute("userForm", new UserLoginDTO());

		/**
		 * Detect if the user deleted his account
		 * show message deleting
		 * clear session and context security
		 */
		String deletedSuccessfully = (String) session.getAttribute("deletedSuccessfully");
		if(deletedSuccessfully != null){
			model.addAttribute("alertType", "success");
			model.addAttribute("alert", deletedSuccessfully);
			session.removeAttribute("deletedSuccessfully");

			SecurityContextHolder.clearContext();
			session.invalidate();

			return "login";
		}

		/**
		 * If we still logged in when going to login page = we logged out
		 */
		if(session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY) != null){
			return "redirect:/logout";
		}

		/**
		 * Detect if we have been logged out
		 */
		String successLogoutHandled = (String) session.getAttribute("successLogoutHandled");
		if(successLogoutHandled != null){
			model.addAttribute("alertType", "success");
			model.addAttribute("alert", "Logged out successfully");
			session.removeAttribute("successLogoutHandled");
		}

		return "login";
	}

	@PostMapping("/login")
	public String authenticate(@ModelAttribute("userForm") UserLoginDTO userLoginDTO,
							   HttpServletRequest request, Model model) {

		/**
		 * Trying to authenticate with the email and password from login page
		 */
		try{

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail().toLowerCase(), userLoginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(auth);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			// generate session for the user
			HttpSession session = request.getSession(true);
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

			return "redirect:/";
		}catch (AuthenticationException ex){
			// if UsernamePasswordAuthenticationToken failed to authenticate
			model.addAttribute("alertType", "danger");
			model.addAttribute("alert", "email or password incorrect");
		}

		return "login";
	}

}
