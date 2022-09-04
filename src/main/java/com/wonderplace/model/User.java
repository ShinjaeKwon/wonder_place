package com.wonderplace.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wonderplace.model.entity.UserEntity;
import com.wonderplace.model.type.UserRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements UserDetails {

	private Long id;
	private String username;
	private String password;
	private UserRole role;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static User from(UserEntity entity) {
		return new User(
			entity.getId(),
			entity.getUsername(),
			entity.getPassword(),
			entity.getRole(),
			entity.getRegisteredAt(),
			entity.getUpdatedAt(),
			entity.getDeletedAt()
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.getRole().toString()));
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.deletedAt == null;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.deletedAt == null;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.deletedAt == null;
	}

	@Override
	public boolean isEnabled() {
		return this.deletedAt == null;
	}
}
