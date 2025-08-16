package com.example.microservices.authservice.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservices.authservice.model.dto.AuthenticationRequest;
import com.example.microservices.authservice.model.dto.AuthenticationResponse;
import com.example.microservices.authservice.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public AuthenticationResponse login(@Valid @RequestBody final AuthenticationRequest request) {
		return authenticationService.authenticate(request);
	}
}
