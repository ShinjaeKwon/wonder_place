package com.wonderplace.fixture;

import com.wonderplace.model.entity.UserEntity;

public class UserEntityFixture {

	public static UserEntity get(String username, String password) {
		return UserEntity.of(1L, username, password);
	}
}
