package com.jerem.mdd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing an author.
 * 
 * <p>
 * This class is used to transfer author information such as ID and username, as part of a response
 * payload in the context of posts or topics.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id;
    private String username;
}
