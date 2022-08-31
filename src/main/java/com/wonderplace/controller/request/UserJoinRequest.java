package com.wonderplace.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJoinRequest {

	private String username;
	private String password;

	public static UserJoinRequest of(String username, String password) {
		return new UserJoinRequest(username, password);
	}
}
