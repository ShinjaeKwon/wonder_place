package com.wonderplace.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wonderplace.exception.WonderPlaceException;
import com.wonderplace.model.User;
import com.wonderplace.model.entity.UserEntity;
import com.wonderplace.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserEntityRepository userEntityRepository;

	public User join(String username, String password) {
		Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);

		userEntityRepository.save(new UserEntity());

		return new User();
	}

	public String login(String username, String password) {

		UserEntity userEntity = userEntityRepository.findByUsername(username)
			.orElseThrow(() -> new WonderPlaceException());

		userEntity.checkPassword(password);

		//토큰생성

		return "";
	}
}
