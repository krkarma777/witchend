package com.witchend.domain.validator;

import com.witchend.domain.dto.user.UserCreateRequestDTO;
import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.entity.user.User;
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


    public void registerCheck(UserCreateRequestDTO requestDTO) {
        if (userService.existsByUsername(requestDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 아이디입니다.");
        }

        if (userService.existsByNickname(requestDTO.getNickname())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다.");
        }

        if (userService.existsByEmail(requestDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
        }

        if (!requestDTO.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\W_]).{8,20}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 영문, 숫자, 특수문자의 조합으로 8~20자여야 합니다.");
        }
    }


    public void updateCheck(UserUpdateRequestDTO requestDTO, User currentUser) {
        if (!bCryptPasswordEncoder.matches(requestDTO.getOriginalPassword(), currentUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "입력하신 기존 비밀번호가 맞지 않습니다.");
        }
    }

    public void deleteCheck(User userById, User currentUser) {
        if (!userById.equals(currentUser)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없는 요청입니다.");
        }
    }
}
