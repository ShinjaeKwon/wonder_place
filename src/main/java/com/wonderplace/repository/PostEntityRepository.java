package com.wonderplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wonderplace.model.entity.PostEntity;

public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {
}
