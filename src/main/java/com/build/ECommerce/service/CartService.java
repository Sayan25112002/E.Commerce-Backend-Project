package com.build.ECommerce.service;

import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.entity.Cart;
import com.build.ECommerce.exception.InsufficientStockFoundation;

public interface CartService {

    CartResponseDto addCart(String email, Long productId, Integer quantity) throws InsufficientStockFoundation;

    CartResponseDto getCartDto(String email);

    Cart getCart(String email);

    void clearCart(String email);

}
