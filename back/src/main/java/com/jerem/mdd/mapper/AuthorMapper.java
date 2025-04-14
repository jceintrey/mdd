package com.jerem.mdd.mapper;

import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.AuthorDto;
import com.jerem.mdd.model.User;

@Component
public class AuthorMapper {
    public AuthorDto toDto(User author) {
        return new AuthorDto(author.getId(), author.getUsername());
    }
}
