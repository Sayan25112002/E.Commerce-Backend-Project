package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.requestDto.CartItemRequestDto;
import com.build.ECommerce.dto.requestDto.OrderRequestDto;
import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.dto.responseDto.OrderResponseDto;
import com.build.ECommerce.entity.*;
import com.build.ECommerce.exception.InsufficientStockFoundation;
import com.build.ECommerce.mapper.CartMapper;
import com.build.ECommerce.mapper.OrderMapper;
import com.build.ECommerce.repository.OrderRepository;
import com.build.ECommerce.repository.ProductRepository;
import com.build.ECommerce.repository.UserRepository;
import com.build.ECommerce.service.CartService;
import com.build.ECommerce.service.EmailService;
import com.build.ECommerce.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderMapper.toOrderResponseDtoList(orderRepository.findAll());
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByUserEmail(Long userId) {
        return orderMapper.toOrderResponseDtoList(orderRepository.findByUserId(userId));
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, Order.OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order not found"));
        order.setStatus(orderStatus);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponseDto(updatedOrder);
    }

    private List<OrderItem> createOrderItems(Cart cart, Order order) {
        return cart.getItems().stream().map(cartItem->{
            Product product = productRepository.findById(cartItem
                    .getProduct()
                    .getId())
                    .orElseThrow(()->
                            new EntityNotFoundException("Product not found with id : "+cartItem.getProduct().getId()));
            if(product.getQuantity()==null){
                throw new IllegalStateException("Product Quantity is not set for product "+product.getName());
            }
            if(product.getQuantity()<cartItem.getQuantity()){
                throw new IllegalStateException("Not enough stock for product "+product.getName());
            }
            product.setQuantity(product.getQuantity()-cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(null,order,product,cartItem.getQuantity(),product.getPrice());
        }).collect(Collectors.toList());
    }
}
