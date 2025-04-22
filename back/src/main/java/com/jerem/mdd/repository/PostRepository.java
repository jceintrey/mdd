package com.jerem.mdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jerem.mdd.model.Post;


/**
 * Repository interface for {@link Post} entities. Inherits basic CRUD operations from
 * {@link JpaRepository}.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

}
