package com.purpledocs.boxtracker.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.purpledocs.boxtracker.type.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails, CredentialsContainer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@NotBlank(message = "Username should not be blank")
	@Column(unique = true, nullable = false)
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "Password should not be blank")
	@Size(min = 6, message = "Password must be of 6 characters")
	@Column(nullable = false)
	private String password;

	@Column(name = "user_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus = UserStatus.ACTIVE;

	public User(String username, String password, UserStatus userStatus) {
		this.username = username;
		this.password = password;
		this.userStatus = userStatus;
	}

	@Override
	public void eraseCredentials() {
		this.password = null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}