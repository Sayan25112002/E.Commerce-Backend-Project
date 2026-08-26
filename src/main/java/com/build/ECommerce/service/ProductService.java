package com.build.ECommerce.service;

import com.build.ECommerce.dto.requestDto.ProductRequestDto;
import com.build.ECommerce.dto.responseDto.ProductListResponseDto;
import com.build.ECommerce.dto.responseDto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto) throws IOException;

    ProductResponseDto updateProduct(ProductRequestDto productRequestDto, Long id) throws IOException;

    ProductResponseDto getProduct(Long id);

    List<ProductResponseDto> getAllProducts();

    Page<ProductListResponseDto> getAllProductsInPages(Pageable pageable);

    void deleteProduct(Long id);

}
