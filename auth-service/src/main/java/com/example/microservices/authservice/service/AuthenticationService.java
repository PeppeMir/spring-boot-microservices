package com.example.microservices.authservice.service;

import com.example.microservices.authservice.model.dto.AuthenticationRequest;
import com.example.microservices.authservice.model.dto.AuthenticationResponse;

public interface AuthenticationService {

	AuthenticationResponse authenticate(AuthenticationRequest request);

}