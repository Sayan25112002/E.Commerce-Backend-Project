package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.requestDto.OrderItemRequestDto;
import com.build.ECommerce.dto.responseDto.OrderItemResponseDto;
import com.build.ECommerce.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItem toOrderItem(OrderItemRequestDto orderItemRequestDto);

    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);

    List<OrderItemResponseDto> toOrderItemResponseDtoList(List<OrderItem> orderItems);

}
