package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.responseDto.CartItemResponseDto;
import com.build.ECommerce.dto.responseDto.CartResponseDto;
import com.build.ECommerce.entity.Cart;
import com.build.ECommerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart toCart(CartResponseDto cartResponseDto);

    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "items", target = "cartItems")
    CartResponseDto toCartResponseDto(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    CartItemResponseDto toCartItemResponseDto(CartItem cartItem);

    List<CartItemResponseDto> toCartItemResponseDtoList(List<CartItem> cartItems);

}
