package com.build.ECommerce.config;

import com.build.ECommerce.entity.User;
import com.build.ECommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if(!userRepository.existsByRole(User.Role.ADMIN)){
            User user = User.builder()
                    .email("ocb@domain.com")
                    .password(passwordEncoder.encode("Password@123"))
                    .role(User.Role.ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}
