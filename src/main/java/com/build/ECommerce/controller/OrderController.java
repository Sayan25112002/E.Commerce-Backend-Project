package com.build.ECommerce.controller;

import com.build.ECommerce.dto.requestDto.OrderRequestDto;
import com.build.ECommerce.dto.responseDto.OrderResponseDto;
import com.build.ECommerce.service.OrderService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDto> createOrder(Authentication authentication,
                                                        @Valid @RequestBody OrderRequestDto orderRequestDto) {
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(email,orderRequestDto.getAddress(),orderRequestDto.getPhoneNumber()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersByUser(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersByUserEmail(email));
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderRequestDto orderRequestDto) throws JRException, MessagingException {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, orderRequestDto.getOrderStatus()));
    }
}
