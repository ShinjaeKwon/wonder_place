package com.wonderplace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wonderplace.model.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);

}
