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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) throws IOException {
        Product product = productMapper.toProduct(productRequestDto);
        product.setImage(saveFile(productRequestDto.getImageFile()));
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponseDto(savedProduct);
    }

    private String saveFile(MultipartFile imageFile) throws IOException {
        String uploadDir = System.getProperty("user.dir")+"\\src\\main\\resources\\webapp\\images\\";
        Files.createDirectories(Paths.get(uploadDir));
        String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir,imageFileName);
        Files.write(path, imageFile.getBytes());
        return path.toString();
    }

    @Override
    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto, Long id) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("product not found"));
        productMapper.updateProduct(productRequestDto,product);
        MultipartFile imageFile = productRequestDto.getImageFile();
        if(imageFile!=null && !imageFile.isEmpty()){
            product.setImage(saveFile(imageFile));
        }
        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponseDto(updatedProduct);
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("product not found"));
        return productMapper.toProductResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toProductResponseDtoList(products);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("product not found"));
        productRepository.delete(product);
    }
}
