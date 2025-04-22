package com.jerem.mdd.mapper;

import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.AuthorDto;
import com.jerem.mdd.model.User;


/**
 * Mapper class used to convert {@link User} objects to {@link AuthorDto}. It is used PostMapper to
 * convert Author Object to dto.
 */
@Component
public class AuthorMapper {
    public AuthorDto toDto(User author) {
        return new AuthorDto(author.getId(), author.getUsername());
    }
}
