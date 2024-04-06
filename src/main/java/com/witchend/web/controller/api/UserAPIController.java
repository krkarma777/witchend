package com.witchend.web.controller.api;

import com.witchend.domain.dto.user.UserCreateRequestDTO;
import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.sevice.user.UserRequestProcessService;
import com.witchend.domain.validator.user.UserAuthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAPIController {

    private final UserAuthValidator userAuthValidator;
    private final UserRequestProcessService processService;

    /**
     * Handles user creation (registration) requests.
     *
     * @param requestDTO DTO containing data required for user creation
     * @return ResponseEntity object containing a success message
     */
    @PostMapping // Maps POST requests to '/api/user' path
    public ResponseEntity<?> create(@RequestBody UserCreateRequestDTO requestDTO) {
        processService.registerProcess(requestDTO); // Processes user creation
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "회원가입이 정상적으로 완료되었습니다."));
    }

    /**
     * Handles user information update requests.
     *
     * @param requestDTO DTO containing data required for user update
     * @param principal Information of the authenticated user
     * @return ResponseEntity object containing a success message
     */
    @PatchMapping // Maps PATCH requests to '/api/user' path
    public ResponseEntity<?> update(@RequestBody UserUpdateRequestDTO requestDTO, Principal principal) {
        processService.updateProcess(requestDTO, principal); // Processes user information update
        return ResponseEntity.ok(Map.of("message", "회원 수정이 완료되었습니다."));
    }

    /**
     * Handles requests to retrieve user information based on user ID.
     *
     * @param id ID of the user to be retrieved
     * @return ResponseEntity object containing user information
     */
    @GetMapping // Maps GET requests to '/api/user' path, retrieves user information
    public ResponseEntity<?> findById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(userAuthValidator.getCurrentUserById(id)); // Retrieves user information by ID
    }

    /**
     * Handles user deletion requests.
     *
     * @param id ID of the user to be deleted
     * @param principal Information of the authenticated user
     * @return ResponseEntity object containing a success message
     */
    @DeleteMapping("/{id}") // Maps DELETE requests to '/api/user/{id}' path
    public ResponseEntity<?> delete(@PathVariable("id") Long id, Principal principal) {
        processService.deleteProcess(id, principal); // Processes user deletion
        return ResponseEntity.ok(Map.of("message", "회원 탈퇴가 완료되었습니다."));
    }
}
