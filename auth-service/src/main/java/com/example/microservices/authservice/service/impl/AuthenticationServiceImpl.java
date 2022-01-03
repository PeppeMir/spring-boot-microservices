package com.example.microservices.authservice.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservices.authservice.model.dto.AuthenticationRequest;
import com.example.microservices.authservice.model.dto.AuthenticationResponse;
import com.example.microservices.authservice.service.AuthenticationService;
import com.example.microservices.authservice.service.jwt.JWTUtilsService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private JWTUtilsService jwtUtilsService;

	@Override
	public AuthenticationResponse authenticate(final AuthenticationRequest request) {
		// TODO: check username and password against a DB. By the moment, assume data
		// are correct

		// FIXME: pass parameter from retrieved user
		final String token = jwtUtilsService.generateToken(UUID	.randomUUID()
																.toString(),
				request.getUsername());

		return new AuthenticationResponse.Builder()	.withToken(token)
													.build();
	}

}
