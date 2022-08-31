package com.wonderplace.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonderplace.controller.request.UserJoinRequest;
import com.wonderplace.controller.request.UserLoginRequest;
import com.wonderplace.exception.ErrorCode;
import com.wonderplace.exception.WonderPlaceException;
import com.wonderplace.model.User;
import com.wonderplace.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@DisplayName("회원가입")
	@Test
	void given_UserInfo_when_Join_then_IsOK() throws Exception {
		//given
		String username = "username";
		String password = "password";

		//when
		when(userService.join(username, password)).thenReturn(mock(User.class));

		//then
		mock.perform(post("/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(UserJoinRequest.of(username, password)))
			).andDo(print())
			.andExpect(status().isOk());

	}

	@DisplayName("회원가입시 이미 회원가입이 되어 있는 username인 경우 에러 반환")
	@Test
	void given_UserInfo_when_Join_then_IsConflict() throws Exception {
		//given
		String username = "username";
		String password = "password";

		//when
		when(userService.join(username, password)).thenThrow(
			new WonderPlaceException(ErrorCode.DUPLICATED_USER_NAME, ""));

		//then
		mock.perform(post("/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(UserJoinRequest.of(username, password)))
			).andDo(print())
			.andExpect(status().isConflict());
	}

	@DisplayName("로그인")
	@Test
	void given_UserInfo_when_Login_then_IsOk() throws Exception {
		//given
		String username = "username";
		String password = "password";

		//when
		when(userService.login(username, password)).thenReturn("test_token");

		//then
		mock.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(UserLoginRequest.of(username, password)))
			).andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("회원가입이 안된 username으로 로그인 할 경우 에러 반환")
	@Test
	void given_NonexistentUsername_when_Login_then_IsNotFound() throws Exception {
		//given
		String username = "username";
		String password = "password";

		//when
		when(userService.login(username, password)).thenThrow(new WonderPlaceException(ErrorCode.USER_NOT_FOUND));

		//then
		mock.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(UserLoginRequest.of(username, password)))
			).andDo(print())
			.andExpect(status().isNotFound());
	}

	@DisplayName("username은 회원가입 되어있지만 패스워드가 틀린 경우")
	@Test
	void given_WrongPassword_when_Login_then_IsUnauthorized() throws Exception {
		//given
		String username = "username";
		String password = "password";

		//when
		when(userService.login(username, password)).thenThrow(new WonderPlaceException(ErrorCode.INVALID_PASSWORD));

		//then
		mock.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(UserLoginRequest.of(username, password)))
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

}
