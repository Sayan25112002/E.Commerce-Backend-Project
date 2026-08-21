package com.build.ECommerce.service;

import com.build.ECommerce.dto.requestDto.ProductRequestDto;
import com.build.ECommerce.dto.responseDto.ProductResponseDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto) throws IOException;

    ProductResponseDto updateProduct(ProductRequestDto productRequestDto, Long id);

    ProductResponseDto getProduct(Long id);

    List<ProductResponseDto> getAllProducts();

    void deleteProduct(Long id);

}
