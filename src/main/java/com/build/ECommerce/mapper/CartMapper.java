package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.entity.Cart;

import java.util.List;

public interface CartMapper {

    CartResponseDto toCartResponseDto(Cart cart);

    List<CartResponseDto> toCartResponseDtoList(List<Cart> carts);

}
