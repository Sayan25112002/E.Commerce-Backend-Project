package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.requestDto.CartItemRequestDto;
import com.build.ECommerce.dto.requestDto.OrderRequestDto;
import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.dto.responseDto.OrderResponseDto;
import com.build.ECommerce.entity.Cart;
import com.build.ECommerce.entity.Order;
import com.build.ECommerce.entity.OrderItem;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.mapper.CartMapper;
import com.build.ECommerce.mapper.OrderMapper;
import com.build.ECommerce.repository.OrderRepository;
import com.build.ECommerce.repository.ProductRepository;
import com.build.ECommerce.repository.UserRepository;
import com.build.ECommerce.service.CartService;
import com.build.ECommerce.service.EmailService;
import com.build.ECommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class.getName());

    @Override
    public OrderResponseDto createOrder(String email, String address, String phoneNumber) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        CartResponseDto cartResponseDto = cartService.getCart(email);
        Cart cart = cartMapper.toCart(cartResponseDto);
        if(cart.getItems().isEmpty()){
            throw new IllegalStateException("Cannot create order with an empty card");
        }
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setStatus(Order.OrderStatus.PREPARING);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItem> orderItems = createOrderItems(cart, order);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(email);
        try{
            emailService.sendOrderConfirmationEmail(savedOrder);
        }catch(MailException e){
            logger.error("Failed to send order confirmation email for email ID : "+savedOrder.getId(),e);
        }
        return orderMapper.toOrderResponseDto(savedOrder);
    }


}
