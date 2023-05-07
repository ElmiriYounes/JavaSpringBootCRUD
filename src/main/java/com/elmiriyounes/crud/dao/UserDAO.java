package com.elmiriyounes.crud.dao;

import com.elmiriyounes.crud.dto.UserRegisterDTO;
import com.elmiriyounes.crud.model.User;

import java.util.Optional;

public interface UserDAO {
	Optional<User> getUserByEmail(String email);
	boolean saveUser(UserRegisterDTO userRegisterDTO);
	boolean deleteUser(String email);
	boolean emailUpdate(String oldEmail, String newEmail);
	boolean pwdUpdate(String email, String pwd);
}
