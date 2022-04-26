package com.example.microservices.userservice.service;

import com.example.microservices.userservice.model.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

	Flux<UserDto> getAll();

	Mono<UserDto> get(String id);

	Mono<UserDto> create(UserDto user);

}