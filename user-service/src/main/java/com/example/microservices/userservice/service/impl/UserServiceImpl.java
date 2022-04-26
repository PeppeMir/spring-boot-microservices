package com.example.microservices.userservice.service.impl;

import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.microservices.userservice.converter.UserConverter;
import com.example.microservices.userservice.model.document.UserDocument;
import com.example.microservices.userservice.model.dto.UserDto;
import com.example.microservices.userservice.repository.UserRepository;
import com.example.microservices.userservice.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Flux<UserDto> getAll() {
		return userRepository	.findAll()
								.map(userConverter::docToDto);
	}

	@Override
	public Mono<UserDto> get(final String id) {
		return userRepository	.findById(id)
								.map(userConverter::docToDto)
								.switchIfEmpty(Mono.error(new InstanceNotFoundException()));
	}

	@Override
	public Mono<UserDto> create(final UserDto user) {
		Assert.isNull(user.getId(), "Cannot create an user that already has an identifier");

		final UserDocument document = userConverter.dtoToDoc(user);
		document.setPassword(passwordEncoder.encode(document.getPassword()));

		return userRepository	.save(document)
								.map(userConverter::docToDto);
	}
}
