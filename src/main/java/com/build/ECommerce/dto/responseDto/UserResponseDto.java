package com.build.ECommerce.dto.responseDto;

import com.build.ECommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private String email;

    private String password;

    private User.Role role;

}
