package com.build.ECommerce.service;

import com.build.ECommerce.dto.requestDto.CommentRequestDto;
import com.build.ECommerce.dto.responseDto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto addComment(CommentRequestDto commentRequestDto, Long productId, Long userId);

    List<CommentResponseDto> getCommentsByProduct(Long productId);

}
