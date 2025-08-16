package com.example.microservices.userservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {
	private String id;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
