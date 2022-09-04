package com.wonderplace.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonderplace.controller.request.PostCreateRequest;
import com.wonderplace.service.PostService;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PostService postService;

	@DisplayName("포스트 작성")
	@WithMockUser
	@Test
	void given_PostInfo_when_CreatePost_then_IsOK() throws Exception {
		//given
		String title = "title";
		String content = "content";

		//when

		//then
		mock.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(PostCreateRequest.of(title, content)))
			).andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("포스트 작성시 로그인하지 않은 경우")
	@WithAnonymousUser
	@Test
	void given_NonLogin_when_CreatePost_then_IsOK() throws Exception {
		//given
		String title = "title";
		String content = "content";

		//when

		//then
		mock.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(PostCreateRequest.of(title, content)))
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

}
