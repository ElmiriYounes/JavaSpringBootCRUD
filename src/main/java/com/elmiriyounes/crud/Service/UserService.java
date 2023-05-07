package com.elmiriyounes.crud.Service;

import com.elmiriyounes.crud.dto.UserRegisterDTO;

public interface UserService {
	boolean ifUserExists(String email);
	boolean userRegistered(UserRegisterDTO userRegisterDTO);
	boolean isUserDeleted(String email);
	boolean isEmailUpdated(String oldEmail, String newEmail);

	boolean isPwdUpdated(String email, String pwd);
}
