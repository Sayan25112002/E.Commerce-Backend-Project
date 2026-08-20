package com.build.ECommerce.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {

    @NotBlank(message = "Product Name is required")
    private String name;

    @NotBlank(message = "Product Description is required")
    private String description;

    @Positive(message = "Cannot be negative")
    private BigDecimal price;

    @Positive(message = "Cannot be negative")
    private Integer quantity;

    private MultipartFile imageFile;

    private List<CommentRequestDto> comments;

}
