package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.entity.Cart;
import com.build.ECommerce.entity.CartItem;
import com.build.ECommerce.entity.Product;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.exception.InsufficientStockFoundation;
import com.build.ECommerce.exception.ResourceNotFoundException;
import com.build.ECommerce.mapper.CartMapper;
import com.build.ECommerce.repository.CartRepository;
import com.build.ECommerce.repository.ProductRepository;
import com.build.ECommerce.repository.UserRepository;
import com.build.ECommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public CartResponseDto addCart(Long userId, Long productId, Integer quantity) throws InsufficientStockFoundation {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));
        if(product.getQuantity()<quantity){
            throw new InsufficientStockFoundation("Not enough available");
        }
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(null,user,new ArrayList<>()));
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst();
        if(existingCartItem.isPresent()){
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }else {
            CartItem cartItem = new CartItem(null,cart,product,quantity);
            cart.getItems().add(cartItem);
        }
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toCartResponseDto(savedCart);
    }

    @Override
    public CartResponseDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart not found"));
        return cartMapper.toCartResponseDto(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
