package com.build.ECommerce.controller;

import com.build.ECommerce.dto.requestDto.OrderRequestDto;
import com.build.ECommerce.dto.responseDto.OrderResponseDto;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{address}/{phoneNumber}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable String address, @PathVariable String phoneNumber, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        OrderResponseDto orderResponseDto = orderService.createOrder(userId,address,phoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtos);
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersByUser(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtos);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderResponseDto>> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrdersByUserEmail(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtos);
    }

}
