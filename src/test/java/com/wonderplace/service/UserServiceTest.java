package com.wonderplace.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wonderplace.exception.ErrorCode;
import com.wonderplace.exception.WonderPlaceException;
import com.wonderplace.fixture.UserEntityFixture;
import com.wonderplace.model.entity.UserEntity;
import com.wonderplace.repository.UserEntityRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserEntityRepository userEntityRepository;

	@MockBean
	private BCryptPasswordEncoder encoder;

	@DisplayName("회원가입이 정상적으로 동작하는 경우")
	@Test
	void given_UserInfo_when_Join_then_DoesNotThrow() {
		//given
		String username = "username";
		String password = "password";

		//when
		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
		when(encoder.encode(password)).thenReturn("encrypt password");
		when(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(username, password));

		//then
		Assertions.assertDoesNotThrow(() -> userService.join(username, password));
	}

	@DisplayName("회원가입시 username이 이미 존재하는 경우")
	@Test
	void given_AlreadyUsername_when_Join_then_ThrowException() {
		//given
		String username = "username";
		String password = "password";
		UserEntity fixture = UserEntityFixture.get(username, password);

		//when
		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
		when(encoder.encode(password)).thenReturn("encrypt password");
		when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

		//then
		WonderPlaceException e = Assertions.assertThrows(WonderPlaceException.class,
			() -> userService.join(username, password));
		Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME, e.getErrorCode());
	}

	@DisplayName("로그인이 정상적으로 동작하는 경우")
	@Test
	void given_UserInfo_when_Login_then_DoesNotThrow() {
		//given
		String username = "username";
		String password = "password";

		UserEntity fixture = UserEntityFixture.get(username, password);

		//when
		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
		when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

		//then
		Assertions.assertDoesNotThrow(() -> userService.login(username, password));
	}

	@DisplayName("로그인시 username이 존재하지 않는 경우")
	@Test
	void given_NonexistentUsername_when_Login_Join_then_ThrowException() {
		//given
		String username = "username";
		String password = "password";

		//when
		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
		when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntity.class));

		//then
		WonderPlaceException e = Assertions.assertThrows(WonderPlaceException.class,
			() -> userService.login(username, password));
		Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());

	}

	@DisplayName("로그인시 password가 틀린경우")
	@Test
	void given_WrongPassword_when_Login_Join_then_ThrowException() {
		//given
		String username = "username";
		String password = "password";
		String wrongPassword = "wrongPassword";

		UserEntity fixture = UserEntityFixture.get(username, password);

		//when
		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));

		//then
		WonderPlaceException e = Assertions.assertThrows(WonderPlaceException.class,
			() -> userService.login(username, wrongPassword));
		Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());

	}

}
