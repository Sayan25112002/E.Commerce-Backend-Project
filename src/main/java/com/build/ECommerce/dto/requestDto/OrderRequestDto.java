package com.build.ECommerce.dto.requestDto;

import com.build.ECommerce.entity.Order;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Address is Required")
    private String address;

    @NotBlank(message = "Phone Number is Required")
    private String phoneNumber;

    private Order.OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private List<OrderItemRequestDto> orderItems;

}
