package com.wonderplace.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequest {

	private String title;
	private String content;

	public static PostCreateRequest of(String title, String content) {
		return new PostCreateRequest(title, content);
	}
}
