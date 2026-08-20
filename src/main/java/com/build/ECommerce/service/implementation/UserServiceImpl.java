package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.requestDto.ChangePasswordRequest;
import com.build.ECommerce.dto.requestDto.UserRequestDto;
import com.build.ECommerce.dto.responseDto.UserResponseDto;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.exception.ResourceNotFoundException;
import com.build.ECommerce.mapper.UserMapper;
import com.build.ECommerce.repository.UserRepository;
import com.build.ECommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists. Please Login");
        };
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setEmail(userRequestDto.getEmail());
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.getUserByEmail(email);
        if(!passwordEncoder.matches(request.getCurrentPassword),user.getPassword()){
            throw new BadCredentialsException("Current Password is Incorrect");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
