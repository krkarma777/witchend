package com.witchend.domain.sevice.user;

import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Saves a user entity.
     *
     * @param user The user entity to save
     */
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    /**
     * Deletes a user entity.
     *
     * @param user The user entity to delete
     */
    public void delete(UserEntity user) {
        userRepository.delete(user);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user to find
     * @return An optional containing the user entity if found, otherwise empty
     */
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find
     * @return An optional containing the user entity if found, otherwise empty
     */
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user by their email.
     *
     * @param email The email of the user to find
     * @return An optional containing the user entity if found, otherwise empty
     */
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Checks if a user exists with the given email.
     *
     * @param email The email to check
     * @return True if a user with the given email exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Checks if a user exists with the given username.
     *
     * @param username The username to check
     * @return True if a user with the given username exists, false otherwise
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Checks if a user exists with the given nickname.
     *
     * @param nickname The nickname to check
     * @return True if a user with the given nickname exists, false otherwise
     */
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
