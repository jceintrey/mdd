package com.jerem.mdd.repository;

import com.jerem.mdd.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for {@link Comment} entities. Provides basic CRUD operations via
 * {@link JpaRepository}.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
