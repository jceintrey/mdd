package com.jerem.mdd.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jerem.mdd.model.Topic;



public interface TopicRepository extends JpaRepository<Topic, Long> {


}
