package com.build.ECommerce.dto.responseDto;

import com.build.ECommerce.dto.requestDto.OrderItemRequestDto;
import com.build.ECommerce.entity.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long id;

    private Long userId;

    private String address;

    private String phoneNumber;

    private Order.OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private List<OrderItemResponseDto> orderItems;
}
