package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.requestDto.ChangePasswordRequest;
import com.build.ECommerce.dto.requestDto.LoginRequestDto;
import com.build.ECommerce.dto.requestDto.UserRequestDto;
import com.build.ECommerce.dto.responseDto.LoginResponseDto;
import com.build.ECommerce.dto.responseDto.UserResponseDto;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.exception.ResourceNotFoundException;
import com.build.ECommerce.mapper.UserMapper;
import com.build.ECommerce.repository.UserRepository;
import com.build.ECommerce.service.EmailService;
import com.build.ECommerce.service.JwtService;
import com.build.ECommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        String jwt = jwtService.generateToken(loginRequestDto.getEmail());
        return new LoginResponseDto(jwt);
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists. Please Login");
        };
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRole(User.Role.USER);
        user.setConfirmationCode(generationCode());
        user.setEmail(userRequestDto.getEmail());
        user.setRole(User.Role.USER);
        emailService.sendEmailConfirmation(user);
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
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        if(!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            throw new BadCredentialsException("Current Password is Incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void confirmEmail(String email, String confirmationCode) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        if(user.getConfirmationCode().equals(confirmationCode)){
            user.setEmailConfirmation(true);
            user.setConfirmationCode(null);
            userRepository.save(user);
        }
        else{
            throw new BadCredentialsException("Confirmation Code is Incorrect");
        }
    }

    private String generationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }


}
