package com.build.ECommerce.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailConfirmationRequestDto {

    private String email;

    private String confirmationCode;

}
