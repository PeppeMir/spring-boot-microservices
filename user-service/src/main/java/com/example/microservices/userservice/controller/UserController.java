package com.example.microservices.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservices.userservice.model.dto.UserDto;
import com.example.microservices.userservice.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public Flux<UserDto> getAll() {
		return userService.getAll();
	}

	@GetMapping("/{id}")
	public Mono<UserDto> get(@PathVariable final String id) {
		return userService.get(id);
	}

	@PostMapping
	public Mono<UserDto> create(@RequestBody final UserDto user) {
		return userService.create(user);
	}
}
