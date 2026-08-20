package com.build.ECommerce.dto.requestDto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequestDto {

    @Positive
    private Integer quantity;

    @Positive
    private BigDecimal price;

}
