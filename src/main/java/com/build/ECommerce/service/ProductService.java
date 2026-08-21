package com.build.ECommerce.service;

import com.build.ECommerce.dto.requestDto.ProductRequestDto;
import com.build.ECommerce.dto.responseDto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto updateProduct(ProductRequestDto productRequestDto);

    ProductResponseDto getProduct(Long id);

    List<ProductResponseDto> getAllProducts();

    void deleteProduct(Long id);

}
