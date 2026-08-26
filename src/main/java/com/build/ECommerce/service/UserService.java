package com.build.ECommerce.service;

import com.build.ECommerce.dto.requestDto.ChangePasswordRequest;
import com.build.ECommerce.dto.requestDto.LoginRequestDto;
import com.build.ECommerce.dto.requestDto.UserRequestDto;
import com.build.ECommerce.dto.responseDto.LoginResponseDto;
import com.build.ECommerce.dto.responseDto.UserResponseDto;
import com.build.ECommerce.entity.User;

public interface UserService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    UserResponseDto registerUser(UserRequestDto userRequestDto);

    UserResponseDto getUserByEmail(String email);

    void changePassword(String email, ChangePasswordRequest request);

    void confirmEmail(String email, String confirmationCode);

}
