package com.example.microservices.authservice.model.dto;

public class AuthenticationResponse {

	private String token;

	private AuthenticationResponse() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	public static class Builder {

		private String token;

		public Builder withToken(final String token) {
			this.token = token;
			return this;
		}

		public AuthenticationResponse build() {
			final AuthenticationResponse instance = new AuthenticationResponse();

			instance.token = this.token;

			return instance;
		}
	}
}
