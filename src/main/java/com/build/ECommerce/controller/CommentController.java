package com.build.ECommerce.controller;

import com.build.ECommerce.dto.requestDto.CommentRequestDto;
import com.build.ECommerce.dto.responseDto.CommentResponseDto;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/product/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long productId,
                                                         @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.addComment(commentRequestDto, productId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(commentService.getCommentsByProduct(productId));
    }
}
