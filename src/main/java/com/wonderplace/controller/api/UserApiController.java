package com.wonderplace.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wonderplace.controller.request.UserJoinRequest;
import com.wonderplace.controller.request.UserLoginRequest;
import com.wonderplace.controller.response.Response;
import com.wonderplace.controller.response.UserJoinResponse;
import com.wonderplace.controller.response.UserLoginResponse;
import com.wonderplace.model.User;
import com.wonderplace.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;

	@PostMapping("/join")
	public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
		User user = userService.join(request.getUsername(), request.getPassword());
		return Response.success(UserJoinResponse.from(user));
	}

	@PostMapping("/login")
	public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
		String token = userService.login(request.getUsername(), request.getPassword());
		return Response.success(UserLoginResponse.of(token));
	}

}
