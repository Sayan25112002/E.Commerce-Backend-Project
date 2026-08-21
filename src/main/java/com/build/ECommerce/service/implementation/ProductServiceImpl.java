package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.requestDto.ProductRequestDto;
import com.build.ECommerce.dto.responseDto.ProductResponseDto;
import com.build.ECommerce.entity.Product;
import com.build.ECommerce.mapper.ProductMapper;
import com.build.ECommerce.repository.ProductRepository;
import com.build.ECommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.toProduct(productRequestDto);
        //product.setImage(saveFile(productRequestDto.getImageFile()));
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponseDto(savedProduct);
    }

//    private String saveFile(MultipartFile imageFile) {
//
//    }

    @Override
    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        return null;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return List.of();
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
