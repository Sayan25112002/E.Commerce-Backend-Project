package com.build.ECommerce.controller;

import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.entity.Cart;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.exception.InsufficientStockFoundation;
import com.build.ECommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponseDto> addCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long productId, @RequestParam Integer quantity) throws InsufficientStockFoundation {
        Long userId = ((User) userDetails).getId();
        return ResponseEntity.ok(cartService.addCart(userId, productId, quantity));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponseDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponseDto> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
