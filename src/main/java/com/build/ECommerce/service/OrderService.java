package com.build.ECommerce.service;

import com.build.ECommerce.dto.requestDto.OrderRequestDto;
import com.build.ECommerce.dto.responseDto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto createOrder(Long userId, String address, String phoneNumber);

}
