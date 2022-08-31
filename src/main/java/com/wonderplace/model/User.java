package com.wonderplace.model;

import java.sql.Timestamp;

import com.wonderplace.model.entity.UserEntity;
import com.wonderplace.model.type.UserRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

	private Long id;
	private String username;
	private String password;
	private UserRole role;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static User from(UserEntity entity) {
		return new User(
			entity.getId(),
			entity.getUsername(),
			entity.getPassword(),
			entity.getRole(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getDeletedAt()
		);
	}

}
