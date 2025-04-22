package com.jerem.mdd.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jerem.mdd.model.Topic;


/**
 * Repository interface for {@link Topic} entities. Provides basic CRUD operations via
 * {@link JpaRepository}.
 */
public interface TopicRepository extends JpaRepository<Topic, Long> {


}
