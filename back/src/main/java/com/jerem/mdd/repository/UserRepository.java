package com.jerem.mdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jerem.mdd.model.User;



/**
 * Repository interface for managing {@link User} persistence.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and database interactions for
 * message entities.
 * </p>
 * 
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Retrieves a user by the email address.
     * 
     * @param email the email address of the user
     * @return the {@link User} associated with the given email address, or {@code null} if no user
     *         is found
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieves a user by the username address.
     * 
     * @param username the username of the user
     * @return the {@link User} associated with the given email address, or {@code null} if no user
     *         is found
     */
    Optional<User> findByUsername(String username);


    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
