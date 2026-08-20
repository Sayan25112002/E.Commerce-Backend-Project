package com.build.ECommerce.dto.requestDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChangePasswordRequest {

    private String currentPassword;

    private String newPassword;

}
