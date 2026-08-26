package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.responseDto.OrderItemResponseDto;
import com.build.ECommerce.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);

}
