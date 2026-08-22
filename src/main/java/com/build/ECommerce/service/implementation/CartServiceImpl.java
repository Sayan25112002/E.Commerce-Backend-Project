package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.mapper.CartMapper;
import com.build.ECommerce.repository.CartRepository;
import com.build.ECommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public CartResponseDto addCart(Long userId, Long productId, Integer quantity) {
        return null;
    }

    @Override
    public void clearCart(Long userId) {

    }
}
