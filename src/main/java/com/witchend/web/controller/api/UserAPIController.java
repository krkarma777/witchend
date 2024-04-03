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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateRequestDTO requestDTO) {
        processService.registerProcess(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "회원가입이 정상적으로 완료되었습니다."));
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody UserUpdateRequestDTO requestDTO, Principal principal) {
        processService.updateProcess(requestDTO, principal);
        return ResponseEntity.ok(Map.of("message", "회원 수정이 완료되었습니다."));
    }

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(userAuthValidator.getCurrentUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, Principal principal) {
        processService.deleteProcess(id, principal);
        return null;
    }


}
