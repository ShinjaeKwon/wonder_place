package com.wonderplace.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {

	private String resultCode;
	private T result;

	public static Response<Void> error(String errorCode) {
		return new Response<>(errorCode, null);
	}

	public static Response<Void> success() {
		return new Response<>("SUCCESS", null);
	}

	public static <T> Response<T> success(T result) {
		return new Response<>("SUCCESS", result);
	}

	public String toStream() {
		if (result == null) {
			return "{" +
				"\"resultCode\":" + "\"" + resultCode + "\"," +
				"\"result\":" + null + "}";
		}
		return "{" +
			"\"resultCode\":" + "\"" + resultCode + "\"," +
			"\"result\":" + "\"" + result + "\" }";
	}
}
