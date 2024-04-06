package com.witchend.domain.validator.user;

import com.witchend.domain.entity.User;
import com.witchend.domain.sevice.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthValidator {

    private final UserService userService;


    public User getCurrentUser(Principal principal) {
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    public User getCurrentUserByUsername(String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    public User getCurrentUserById(Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }
}
