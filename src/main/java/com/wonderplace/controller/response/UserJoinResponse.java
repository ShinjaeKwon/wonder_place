package com.wonderplace.controller.response;

import com.wonderplace.model.User;
import com.wonderplace.model.type.UserRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJoinResponse {

	private Long id;
	private String username;
	private UserRole role;

	public static UserJoinResponse from(User user) {
		return new UserJoinResponse(
			user.getId(),
			user.getUsername(),
			user.getRole()
		);
	}

}
