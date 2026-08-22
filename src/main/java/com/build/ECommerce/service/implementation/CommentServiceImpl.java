package com.build.ECommerce.service.implementation;

import com.build.ECommerce.dto.requestDto.CommentRequestDto;
import com.build.ECommerce.dto.responseDto.CommentResponseDto;
import com.build.ECommerce.entity.Comment;
import com.build.ECommerce.entity.Product;
import com.build.ECommerce.entity.User;
import com.build.ECommerce.exception.ResourceNotFoundException;
import com.build.ECommerce.mapper.CommentMapper;
import com.build.ECommerce.repository.CommentRepository;
import com.build.ECommerce.repository.ProductRepository;
import com.build.ECommerce.repository.UserRepository;
import com.build.ECommerce.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDto addComment(CommentRequestDto commentRequestDto, Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Comment comment = commentMapper.toComment(commentRequestDto);
        comment.setProduct(product);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentResponseDto(savedComment);
    }

    @Override
    public List<CommentResponseDto> getCommentsByProduct(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        return comments
                .stream()
                .map(commentMapper::toCommentResponseDto)
                .collect(Collectors.toList());
    }
}
