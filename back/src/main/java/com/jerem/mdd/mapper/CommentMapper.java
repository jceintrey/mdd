package com.jerem.mdd.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.CommentDto;
import com.jerem.mdd.model.Comment;


/**
 * Mapper class used to convert {@link Comment} objects to {@link CommentDto}. It uses
 * {@link AuthorMapper} to convert the auhtor related object
 */
@Component
public class CommentMapper implements Mapper<Comment, CommentDto> {
    private final ModelMapper modelMapper;
    private final AuthorMapper authorMapper;


    public CommentMapper(ModelMapper modelMapper, AuthorMapper authorMapper) {
        this.modelMapper = modelMapper;
        this.authorMapper = authorMapper;

    }

    @Override
    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

        if (comment.getAuthor() != null) {
            commentDto.setAuthor(authorMapper.toDto(comment.getAuthor()));
        } else {
            commentDto.setAuthor(null);
        }

        return commentDto;
    }

    @Override
    public Comment toEntity(CommentDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

}
