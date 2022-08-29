package com.wonderplace.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
	INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),
	;

	private HttpStatus status;
	private String message;

}