package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.responseDto.UserResponseDto;
import com.build.ECommerce.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toUserResponseDto(User user);

}
