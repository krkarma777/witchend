package com.witchend.domain.repository;

import com.witchend.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Checks if a user exists with the given username.
     *
     * @param username The username to check
     * @return True if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists with the given email.
     *
     * @param email The email to check
     * @return True if a user with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the given nickname.
     *
     * @param nickname The nickname to check
     * @return True if a user with the given nickname exists, false otherwise
     */
    boolean existsByNickname(String nickname);

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find
     * @return An optional containing the user entity if found, otherwise empty
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Finds a user by their email.
     *
     * @param email The email of the user to find
     * @return An optional containing the user entity if found, otherwise empty
     */
    Optional<UserEntity> findByEmail(String email);
}
