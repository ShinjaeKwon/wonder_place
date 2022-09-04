package com.wonderplace.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wonderplace.exception.ErrorCode;
import com.wonderplace.exception.WonderPlaceException;
import com.wonderplace.model.User;
import com.wonderplace.model.entity.UserEntity;
import com.wonderplace.repository.UserEntityRepository;
import com.wonderplace.util.JwtTokenUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserEntityRepository userEntityRepository;
	private final BCryptPasswordEncoder encoder;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.token.expired-time-ms}")
	private Long expiredTimeMS;

	public User loadUserByUsername(String username) {
		return userEntityRepository.findByUsername(username).map(User::from)
			.orElseThrow(
				() -> new WonderPlaceException(ErrorCode.USER_NOT_FOUND,
					String.format("\"%s\" not founded", username)));
	}

	@Transactional
	public User join(String username, String password) {
		userEntityRepository.findByUsername(username).ifPresent(it -> {
			throw new WonderPlaceException(ErrorCode.DUPLICATED_USER_NAME,
				String.format("\"%s\" is duplicated", username));
		});

		UserEntity userEntity = userEntityRepository.save(UserEntity.of(username, encoder.encode(password)));
		log.info("Success User Join - \"{}\"", username);
		return User.from(userEntity);
	}

	public String login(String username, String password) {
		UserEntity userEntity = userEntityRepository.findByUsername(username)
			.orElseThrow(
				() -> new WonderPlaceException(ErrorCode.USER_NOT_FOUND,
					String.format("\"%s\" not founded", username)));

		if (!encoder.matches(password, userEntity.getPassword())) {
			throw new WonderPlaceException(ErrorCode.INVALID_PASSWORD);
		}

		return JwtTokenUtils.generateToken(username, secretKey, expiredTimeMS);
	}

	public UserEntity findByUserName(String username) {
		return userEntityRepository.findByUsername(username)
			.orElseThrow(() -> new WonderPlaceException(ErrorCode.USER_NOT_FOUND,
				String.format("\"%s\" not founded", username)));
	}

}
