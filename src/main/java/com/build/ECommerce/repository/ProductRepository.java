package com.build.ECommerce.repository;

import com.build.ECommerce.dto.responseDto.ProductListResponseDto;
import com.build.ECommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT new com.build.ECommerce.dto.responseDto.ProductListResponseDto(p.id, p.name, p.description, p.price, p.quantity, p.image) FROM Product p")
    Page<ProductListResponseDto> findAllWithoutComments(Pageable pageable);

}
