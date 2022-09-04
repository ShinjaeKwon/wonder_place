package com.wonderplace.model;

import java.sql.Timestamp;

import com.wonderplace.model.entity.PostEntity;
import com.wonderplace.model.entity.UserEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {

	private Long id;
	private String title;
	private String content;
	private UserEntity user;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static Post from(PostEntity entity) {
		return new Post(
			entity.getId(),
			entity.getTitle(),
			entity.getContent(),
			entity.getUser(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getDeletedAt()
		);
	}
}
