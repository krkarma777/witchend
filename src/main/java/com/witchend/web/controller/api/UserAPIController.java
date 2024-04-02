package com.witchend.web.controller.api;

import com.witchend.domain.UserCreateRequestDTO;
import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.sevice.user.RegisterService;
import com.witchend.domain.sevice.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAPIController {

    private final UserService userService;
    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateRequestDTO requestDTO) {
        registerService.registerProcess(new UserEntity(requestDTO));
        return ResponseEntity.ok(Map.of("message", "회원가입이 정상적으로 완료되었습니다."));
    }

    @GetMapping
    public ResponseEntity<?> update() {
        return null;
    }
    @PatchMapping
    public ResponseEntity<?> findById() {
            return null;
    }
    @DeleteMapping
    public ResponseEntity<?> delete() {
        return null;
    }
}
