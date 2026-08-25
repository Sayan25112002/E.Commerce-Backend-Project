package com.build.ECommerce.service;

import com.build.ECommerce.dto.responseDto.OrderResponseDto;
import com.build.ECommerce.entity.Order;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(String userEmail, String address, String phoneNumber);

    List<OrderResponseDto> getAllOrders();

    List<OrderResponseDto> getAllOrdersByUserEmail(String userEmail);

    OrderResponseDto updateOrderStatus(Long orderId, Order.OrderStatus orderStatus);

}
