package com.elmiriyounes.crud.controller;


import com.elmiriyounes.crud.Service.UserService;
import com.elmiriyounes.crud.Utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailsService userDetailsService;


	@GetMapping("/")
	public String home(Model model, HttpSession session){

		/**
		 * If any alerts exist (from edit email modal or password for example)
		 */
		if(session.getAttribute("alert") != null){
			model.addAttribute("alertType", session.getAttribute("alertType"));
			model.addAttribute("alert", session.getAttribute("alert"));
			System.out.println(session.getAttribute("alert"));
			session.removeAttribute("alertType");
			session.removeAttribute("alert");
		}

		return "index";
	}

	@PostMapping("/delete")
	public String deleteUser(HttpServletRequest request, @RequestParam("email") String email, Model model){

		if(userService.isUserDeleted(email)){
			HttpSession session = request.getSession();
			session.setAttribute("deletedSuccessfully", "Account deleted successfully");

			return "redirect:/login";
		}

		/**
		 * We use session to store alert because we redirect and model.AddAttribute won't be recognized after
		 * redirect
		 */
		HttpSession session = request.getSession();
		session.setAttribute("alertType", "danger");
		session.setAttribute("alert", "Deleting account failed");

		return "redirect:/";
	}

	@PostMapping("/editEmail")
	public String editEmail(@RequestParam("editEmail") String newEmail, @RequestParam("oldEmail") String oldEmail, HttpServletRequest request){

		if(!UserUtil.isValidEmail(newEmail)){
			HttpSession session = request.getSession();
			session.setAttribute("alertType", "danger");
			session.setAttribute("alert", "Invalid email");

			return "redirect:/";
		}

		if(userService.ifUserExists(newEmail)){
			HttpSession session = request.getSession();
			session.setAttribute("alertType", "danger");
			session.setAttribute("alert", "Email is already used");

			return "redirect:/";
		}

		if(!userService.isEmailUpdated(oldEmail, newEmail)){
			HttpSession session = request.getSession();
			session.setAttribute("alertType", "danger");
			session.setAttribute("alert", "Editing email failed, try again");
		}else{
			HttpSession session = request.getSession();
			session.setAttribute("alertType", "success");
			session.setAttribute("alert", "Email edited successfully");

			UserDetails userDetails = userDetailsService.loadUserByUsername(newEmail.toLowerCase());
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		return "redirect:/";
	}

	@PostMapping("/editPwd")
	public String editPwd(
			@RequestParam("newPwd") String newPwd,
			@RequestParam("newConfirmPwd") String newConfirmPwd,
			@RequestParam("email") String email,
			HttpServletRequest request
	){

		if(!UserUtil.matchPasswords(newPwd, newConfirmPwd)){
			HttpSession session = request.getSession();
			session.setAttribute("alertType", "danger");
			session.setAttribute("alert", "Password do not match");

			return "redirect:/";
		}

		if(!userService.isPwdUpdated(email, newPwd)){
			HttpSession session = request.getSession();
			session.setAttribute("alertType", "danger");
			session.setAttribute("alert", "Ediitng password failed, try again");
		}else{
			HttpSession session = request.getSession();
			session.setAttribute("alertType", "success");
			session.setAttribute("alert", "Password edited successfully");
		}

		return "redirect:/";
	}
}
