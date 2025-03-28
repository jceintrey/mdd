package com.jerem.mdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jerem.mdd.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
