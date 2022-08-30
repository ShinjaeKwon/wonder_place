package com.wonderplace.fixture;

import com.wonderplace.model.entity.UserEntity;

public class UserEntityFixture {

	public static UserEntity get(String username, String password) {
		return new UserEntity(1L, username, password);
	}
}
