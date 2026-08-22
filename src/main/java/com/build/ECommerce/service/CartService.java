package com.build.ECommerce.service;

import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.exception.InsufficientStockFoundation;

public interface CartService {

    public CartResponseDto addCart(String email, Long productId, Integer quantity) throws InsufficientStockFoundation;

    CartResponseDto getCart(String email);

    void clearCart(String email);

}
