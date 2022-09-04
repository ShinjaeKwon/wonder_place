package com.wonderplace.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wonderplace.model.entity.PostEntity;
import com.wonderplace.model.entity.UserEntity;
import com.wonderplace.repository.PostEntityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostEntityRepository postEntityRepository;
	private final UserService userService;

	@Transactional
	public PostEntity create(String title, String content, String username) {
		UserEntity userEntity = userService.findByUserName(username);
		PostEntity post = postEntityRepository.save(PostEntity.of(title, content, userEntity));
		return post;
	}
}
