package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.responseDto.OrderItemResponseDto;
import com.build.ECommerce.dto.responseDto.OrderResponseDto;
import com.build.ECommerce.entity.Order;
import com.build.ECommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toOrderResponseDto(Order order);

    List<OrderResponseDto> toOrderResponseDtoList(List<Order> orders);

    @Mapping(source = "product.id", target = "productId")
    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);

}
