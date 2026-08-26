package com.build.ECommerce.mapper;

import com.build.ECommerce.dto.requestDto.CommentRequestDto;
import com.build.ECommerce.dto.responseDto.CommentResponseDto;
import com.build.ECommerce.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toComment(CommentRequestDto commentRequestDto);

    CommentResponseDto toCommentResponseDto(Comment comment);

}
