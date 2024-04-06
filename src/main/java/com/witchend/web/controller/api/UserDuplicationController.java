package com.witchend.web.controller.api;


import com.witchend.domain.sevice.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserDuplicationController {

    private final UserService userService;

    @GetMapping("/username-check")
    public ResponseEntity<Map<String, Boolean>> checkUsernameAvailability(@RequestParam("username") String username) {
        System.out.println("username = " + username);
        return checkAvailability(username, userService::existsByUsername);
    }

    @GetMapping("/email-check")
    public ResponseEntity<Map<String, Boolean>> checkEmailAvailability(@RequestParam("email") String email) {
        System.out.println("email = " + email);
        return checkAvailability(email, userService::existsByEmail);
    }

    private ResponseEntity<Map<String, Boolean>> checkAvailability(String value, Predicate<String> existsByPredicate) {
        boolean isAvailable = !existsByPredicate.test(value);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return ResponseEntity.ok(response);
    }
}
