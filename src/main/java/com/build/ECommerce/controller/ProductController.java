package com.build.ECommerce.controller;

import com.build.ECommerce.dto.requestDto.ProductRequestDto;
import com.build.ECommerce.dto.responseDto.ProductResponseDto;
import com.build.ECommerce.entity.Product;
import com.build.ECommerce.repository.ProductRepository;
import com.build.ECommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/createProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> createProduct(@ModelAttribute ProductRequestDto product) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @PatchMapping("/updateProduct/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @ModelAttribute ProductRequestDto productRequestDto) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.updateProduct(productRequestDto, id));
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(id));
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @DeleteMapping("/deleteProduct/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws IOException {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
