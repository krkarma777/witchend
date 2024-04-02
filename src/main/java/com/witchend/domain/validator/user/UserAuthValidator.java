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

    public Optional<UserEntity> authenticate(Principal principal) {
        if (principal == null) {
            return Optional.empty();
        }
        return userService.findByUsername(principal.getName());
    }

    public UserEntity getCurrentUser(Principal principal) {
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }


    public UserEntity getCurrentUserByUsername(String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    public UserEntity getCurrentUserById(Long Id) {
        return userService.findById(Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

}
