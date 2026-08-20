package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.requestDto.ProductRequestDto;
import com.build.ECommerce.dto.responseDto.ProductResponseDto;
import com.build.ECommerce.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductRequestDto productRequestDto);

    ProductResponseDto toProductResponseDto(Product product);

    List<ProductResponseDto> toProductResponseDtoList(List<Product> products);

}
