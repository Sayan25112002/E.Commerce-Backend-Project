package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.requestDto.OrderRequestDto;
import com.build.ECommerce.dto.responseDto.OrderResponseDto;
import com.build.ECommerce.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto toOrderResponseDto(Order order);

    List<OrderResponseDto> toOrderResponseDtoList(List<Order> orders);

}
