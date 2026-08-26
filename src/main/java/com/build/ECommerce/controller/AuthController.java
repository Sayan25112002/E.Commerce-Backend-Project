package com.build.ECommerce.controller;

import com.build.ECommerce.dto.requestDto.ChangePasswordRequest;
import com.build.ECommerce.dto.requestDto.EmailConfirmationRequestDto;
import com.build.ECommerce.dto.requestDto.LoginRequestDto;
import com.build.ECommerce.dto.requestDto.UserRequestDto;
import com.build.ECommerce.dto.responseDto.LoginResponseDto;
import com.build.ECommerce.dto.responseDto.UserResponseDto;
import com.build.ECommerce.exception.ResourceNotFoundException;
import com.build.ECommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponse = userService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRequestDto));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.changePassword(email,changePasswordRequest);
        return ResponseEntity.ok().body("Password Changed Successfully");
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@Valid @RequestBody EmailConfirmationRequestDto emailConfirmationRequestDto) {
        try{
            userService.confirmEmail(emailConfirmationRequestDto.getEmail(),emailConfirmationRequestDto.getConfirmationCode());
            return ResponseEntity.ok().body("Email Confirmed Successfully");
        }catch(BadCredentialsException e){
            return ResponseEntity.badRequest().body("Invalid Confirmation Code");
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
