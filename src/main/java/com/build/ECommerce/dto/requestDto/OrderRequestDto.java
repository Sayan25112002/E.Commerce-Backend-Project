package com.build.ECommerce.dto.requestDto;

import com.build.ECommerce.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    private String address;

    private String phoneNumber;

    private Order.OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private List<OrderItemRequestDto> orderItems;

}
