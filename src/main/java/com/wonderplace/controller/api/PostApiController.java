package com.wonderplace.controller.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wonderplace.controller.request.PostCreateRequest;
import com.wonderplace.controller.response.Response;
import com.wonderplace.model.entity.PostEntity;
import com.wonderplace.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostApiController {

	private final PostService postService;

	@PostMapping("/post")
	public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
		PostEntity post = postService.create(request.getTitle(), request.getContent(), authentication.getName());
		return Response.success();
	}
}
