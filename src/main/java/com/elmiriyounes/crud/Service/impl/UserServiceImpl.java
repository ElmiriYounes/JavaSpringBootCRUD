package com.elmiriyounes.crud.Service.impl;

import com.elmiriyounes.crud.Service.UserService;
import com.elmiriyounes.crud.dao.UserDAO;
import com.elmiriyounes.crud.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	@Override
	public boolean ifUserExists(String email) {
		return userDAO.getUserByEmail(email).isPresent();
	}

	@Override
	public boolean userRegistered(UserRegisterDTO userRegisterDTO) {
		return userDAO.saveUser(userRegisterDTO);
	}

	@Override
	public boolean isUserDeleted(String email) {
		return userDAO.deleteUser(email);
	}

	@Override
	public boolean isEmailUpdated(String oldEmail, String newEmail) {
		return userDAO.emailUpdate(oldEmail, newEmail);
	}

	@Override
	public boolean isPwdUpdated(String email, String pwd) {
		return userDAO.pwdUpdate(email, pwd);
	}
}
