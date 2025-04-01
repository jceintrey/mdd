package com.jerem.mdd.mapper;

import org.modelmapper.ModelMapper;
import com.jerem.mdd.dto.CommentDto;
import com.jerem.mdd.model.Comment;

public class CommentMapper implements Mapper<Comment, CommentDto> {
    private final ModelMapper modelMapper;


    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

    }

    @Override
    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

        if (comment.getAuthor() != null) {
            commentDto.setAuthorId(comment.getAuthor().getId());
        } else {
            commentDto.setAuthorId(null);
        }

        return commentDto;
    }

    @Override
    public Comment toEntity(CommentDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

}
