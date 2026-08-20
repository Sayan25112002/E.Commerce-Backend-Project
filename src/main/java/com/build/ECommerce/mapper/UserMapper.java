package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.requestDto.UserRequestDto;
import com.build.ECommerce.dto.responseDto.UserResponseDto;
import com.build.ECommerce.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequestDto userRequestDto);

    UserResponseDto toUserResponseDto(User user);

    List<UserResponseDto> toUserResponseDtoList(List<User> users);

}
