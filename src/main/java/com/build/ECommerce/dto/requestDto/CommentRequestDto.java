package com.build.ECommerce.dto.requestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {

    @NotBlank(message = "Content is Required")
    private String content;

    @Min(value = 1)
    @Max(value = 5)
    private Integer score;

}
