package com.build.ECommerce.controller;

import com.build.ECommerce.dto.requestDto.CartItemRequestDto;
import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.entity.Cart;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.exception.InsufficientStockFoundation;
import com.build.ECommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponseDto> addCart(Authentication authentication,
                                                   @PathVariable Long productId,
                                                   @Valid @RequestBody CartItemRequestDto cartItemRequestDto)
            throws InsufficientStockFoundation {
        String email = authentication.getName();
        return ResponseEntity.ok(cartService.addCart(email, productId, cartItemRequestDto.getQuantity()));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponseDto> getCart(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(cartService.getCart(email));
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        String email = authentication.getName();
        cartService.clearCart(email);
        return ResponseEntity.noContent().build();
    }
}
