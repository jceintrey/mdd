package com.jerem.mdd.repository;

import com.jerem.mdd.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Long> {

}
