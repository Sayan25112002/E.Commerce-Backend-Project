package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.responseDto.OrderResponseDto;
import com.build.ECommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Override
    public OrderResponseDto createOrder(Long userId, String address, String phoneNumber) {
        return null;
    }
}
