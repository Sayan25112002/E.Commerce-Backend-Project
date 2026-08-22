package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.entity.Product;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.mapper.CartMapper;
import com.build.ECommerce.repository.CartRepository;
import com.build.ECommerce.repository.ProductRepository;
import com.build.ECommerce.repository.UserRepository;
import com.build.ECommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponseDto addCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));
        if(product.getQuantity()<quantity){
        }
    }

    @Override
    public void clearCart(Long userId) {

    }
}
