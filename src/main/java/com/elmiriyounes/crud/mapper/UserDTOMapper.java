package com.elmiriyounes.crud.mapper;

import com.elmiriyounes.crud.dto.UserRegisterDTO;
import com.elmiriyounes.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<UserRegisterDTO, User> {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User apply(UserRegisterDTO userRegisterDTO) {
		return new User(userRegisterDTO.getEmail().toLowerCase(), passwordEncoder.encode(userRegisterDTO.getPassword()));
	}
}
