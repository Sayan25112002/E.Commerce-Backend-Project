package com.build.ECommerce.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponseDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private String image;

}
