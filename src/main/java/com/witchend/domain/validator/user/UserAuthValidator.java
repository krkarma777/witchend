package com.witchend.domain.validator.user;

import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.sevice.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthValidator {

    private final UserService userService;

    /**
     * Retrieves the current authenticated user based on the provided principal.
     *
     * @param principal The security principal of the user
     * @return The user entity corresponding to the authenticated user
     * @throws ResponseStatusException if the user cannot be found
     */
    public UserEntity getCurrentUser(Principal principal) {
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    /**
     * Retrieves the user by username.
     *
     * @param username The username of the user to retrieve
     * @return The user entity corresponding to the given username
     * @throws ResponseStatusException if the user cannot be found
     */
    public UserEntity getCurrentUserByUsername(String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    /**
     * Retrieves the user by ID.
     *
     * @param id The ID of the user to retrieve
     * @return The user entity corresponding to the given ID
     * @throws ResponseStatusException if the user cannot be found
     */
    public UserEntity getCurrentUserById(Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }
}
