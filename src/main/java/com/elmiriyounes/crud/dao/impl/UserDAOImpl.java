package com.elmiriyounes.crud.dao.impl;

import com.elmiriyounes.crud.dao.UserDAO;
import com.elmiriyounes.crud.dto.UserRegisterDTO;
import com.elmiriyounes.crud.mapper.UserDTOMapper;
import com.elmiriyounes.crud.model.User;
import com.elmiriyounes.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDTOMapper userDTOMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email.toLowerCase());
	}

	@Override
	public boolean saveUser(UserRegisterDTO userRegisterDTO) {

		try {
			userRepository.save(userDTOMapper.apply(userRegisterDTO));
		}catch (Exception ex){
			return false;
		}

		return true;
	}

	@Override
	public boolean deleteUser(String email) {

		try {
			userRepository.delete(
					userRepository.findByEmail(email).get()
			);
		}catch (Exception ex){
			return false;
		}

		return true;
	}

	@Override
	public boolean emailUpdate(String oldEmail, String newEmail) {

		try {
			User user = userRepository.findByEmail(oldEmail.toLowerCase()).get();
			user.setEmail(newEmail.toLowerCase());
			userRepository.save(user);
		}catch (Exception ex){
			return false;
		}

		return true;
	}

	@Override
	public boolean pwdUpdate(String email, String pwd) {
		try {
			User user = userRepository.findByEmail(email.toLowerCase()).get();
			user.setPassword(passwordEncoder.encode(pwd));
			userRepository.save(user);
		}catch (Exception ex){
			return false;
		}

		return true;
	}
}
