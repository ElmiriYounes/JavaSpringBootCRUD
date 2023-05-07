package com.elmiriyounes.crud.dto;

public class UserRegisterDTO {
	private String email;
	private String password;
	private String confirmPassword;

	public UserRegisterDTO() {
	}

	public UserRegisterDTO(String email, String password, String confirmPAssword) {
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPAssword;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean matchPasswords(String password, String confirmPassword){
		return password.equals(confirmPassword);
	}
}
