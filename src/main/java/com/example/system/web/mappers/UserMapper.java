package com.example.system.web.mappers;

import com.example.system.domain.user.User;
import com.example.system.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
