package com.build.ECommerce.service;

import com.build.ECommerce.dto.responseDto.CartResponseDto;

public interface CartService {

    public CartResponseDto addCart(Long userId, Long productId, Integer quantity);

    void clearCart(Long userId);

}
