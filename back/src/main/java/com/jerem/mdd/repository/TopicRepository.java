package com.jerem.mdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jerem.mdd.model.Topic;



public interface TopicRepository extends JpaRepository<Topic, Long> {

    /**
     * Retrieves a topic by its id.
     * 
     * @param id the id of the topic
     * @return the {@link Topic} associated with the given id, or {@code null} if no topic is found
     */

    Optional<Topic> findByName(String name);


    // Optional<Topic> findById(Long id);



}
