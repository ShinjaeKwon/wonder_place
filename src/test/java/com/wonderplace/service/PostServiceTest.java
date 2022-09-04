package com.wonderplace.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wonderplace.exception.ErrorCode;
import com.wonderplace.exception.WonderPlaceException;
import com.wonderplace.model.entity.PostEntity;
import com.wonderplace.model.entity.UserEntity;
import com.wonderplace.repository.PostEntityRepository;
import com.wonderplace.repository.UserEntityRepository;

@SpringBootTest
public class PostServiceTest {

	@Autowired
	private PostService postService;

	@MockBean
	private PostEntityRepository postEntityRepository;

	@MockBean
	private UserEntityRepository userEntityRepository;

	@DisplayName("포스트 작성이 성공")
	@Test
	void given_PostInfo_when_CreatePost_then_Success() {
		//given
		String title = "title";
		String content = "content";
		String username = "username";

		//when
		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(mock(UserEntity.class)));
		when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

		//then
		Assertions.assertDoesNotThrow(() -> postService.create(title, content, username));
	}

	@DisplayName("포스트 작성시 요청한 유저가 존재하지 않는 경우")
	@Test
	void given_NonexistentUser_when_CreatePost_then_Fail() {  //given
		String title = "title";
		String content = "content";
		String username = "username";

		//when
		when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());

		//then
		WonderPlaceException e = Assertions.assertThrows(WonderPlaceException.class,
			() -> postService.create(title, content, username));
		Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
	}

}
