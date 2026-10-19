package com.store.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.store.dtos.RegisterUserRequest;
import com.store.dtos.UpdateUserRequest;
import com.store.dtos.UserDto;
import com.store.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDto toDto(User user);
	User toEntity(RegisterUserRequest request);
	void update(UpdateUserRequest request, @MappingTarget User user);
}
