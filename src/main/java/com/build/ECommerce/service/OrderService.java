package com.build.ECommerce.service;

import com.build.ECommerce.dto.responseDto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(String userEmail, String address, String phoneNumber);

}
