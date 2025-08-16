package com.example.microservices.authservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {

	@NotBlank
	private String username;

	@NotBlank
	// TODO: add regular expression for password
	private String password;

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
