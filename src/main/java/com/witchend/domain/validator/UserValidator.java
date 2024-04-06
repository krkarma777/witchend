package com.witchend.domain.validator;

import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.sevice.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    /**
     * Validates the user information upon registration.
     * Checks if the username, nickname, and email are already in use,
     * and verifies if the password meets specified criteria.
     *
     * @param newUser The entity of the user to be registered
     */
    public void registerCheck(UserEntity newUser) {
        // Checks if the username is already in use
        if (userService.existsByUsername(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already in use.");
        }

        // Checks if the nickname is already in use
        if (userService.existsByNickname(newUser.getNickname())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nickname is already in use.");
        }

        // Checks if the email is already registered
        if (userService.existsByEmail(newUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered.");
        }

        // Checks if the password matches the specified regex pattern
        if (!newUser.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\W_]).{8,20}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain a combination of letters, numbers, and special characters, and be 8-20 characters long.");
        }
    }

    /**
     * Validates the user information upon update.
     * Particularly, it checks if the user's provided original password matches the current password.
     *
     * @param requestDTO The update request DTO
     * @param currentUser The entity of the currently logged-in user
     */
    public void updateCheck(UserUpdateRequestDTO requestDTO, UserEntity currentUser) {
        // Checks if the provided original password matches the current password
        if (!bCryptPasswordEncoder.matches(requestDTO.getOriginalPassword(), currentUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The provided original password is incorrect.");
        }
    }

    /**
     * Validates the request to delete a user.
     * Specifically, it verifies if the user requesting deletion is the same as the one to be deleted,
     * preventing requests to delete IDs of others.
     *
     * @param userById The entity of the user to be deleted
     * @param currentUser The entity of the currently logged-in user
     */
    public void deleteCheck(UserEntity userById, UserEntity currentUser) {
        // Checks if the user requesting deletion is the same as the one to be deleted
        if (!userById.equals(currentUser)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized request.");
        }
    }
}
