package com.example.microservices.userservice.converter.impl;

import org.springframework.stereotype.Service;

import com.example.microservices.userservice.converter.UserConverter;
import com.example.microservices.userservice.model.document.UserDocument;
import com.example.microservices.userservice.model.dto.UserDto;

@Service
public class UserConverterImpl implements UserConverter {

	@Override
	public UserDocument dtoToDoc(final UserDto dto) {
		final UserDocument document = new UserDocument();

		document.setId(dto.getId());
		document.setUsername(dto.getUsername());
		document.setPassword(dto.getPassword());

		return document;
	}

	@Override
	public UserDto docToDto(final UserDocument document) {
		final UserDto dto = new UserDto();

		dto.setId(document.getId());
		dto.setUsername(document.getUsername());
		dto.setPassword(document.getPassword());

		return dto;
	}
}
