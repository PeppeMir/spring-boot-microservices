package com.example.microservices.userservice.converter;

import com.example.microservices.userservice.model.document.UserDocument;
import com.example.microservices.userservice.model.dto.UserDto;

public interface UserConverter {

	UserDocument dtoToDoc(UserDto dto);

	UserDto docToDto(UserDocument document);
}