package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.entity.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponseDto toCartResponseDto(Cart cart);

    List<CartResponseDto> toCartResponseDtoList(List<Cart> carts);

}
