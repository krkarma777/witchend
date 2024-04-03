package com.witchend.domain.sevice.user;


import com.witchend.domain.dto.user.UserCreateRequestDTO;
import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.validator.user.UserAuthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserRequestProcessService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserAuthValidator userAuthValidator;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registerProcess(UserCreateRequestDTO requestDTO) {
        UserEntity newUser = new UserEntity(requestDTO);

        if (userService.existsByUsername(newUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 사용자명입니다.");
        }

        if (userService.existsByEmail(newUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 등록된 이메일입니다.");
        }

        if (!newUser.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\W_]).{8,20}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 영문, 숫자, 특수문자의 조합으로 8~20자여야 합니다.");
        }

        // 비밀번호 해시 처리
        String hashedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);

        // 사용자 저장
        userService.save(newUser);
    }

    public void updateProcess(UserUpdateRequestDTO requestDTO, Principal principal) {
        UserEntity currentUser = userAuthValidator.getCurrentUser(principal);
        if (!passwordEncoder.matches(currentUser.getPassword(), requestDTO.getOriginalPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "입력하신 기존 비밀번호가 맞지 않습니다.");
        }
        currentUser.update(requestDTO);
        userService.save(currentUser);
    }

    public void deleteProcess(Long id, Principal principal) {
        UserEntity userById = userAuthValidator.getCurrentUserById(id);
        UserEntity currentUser = userAuthValidator.getCurrentUser(principal);
        if (userById.equals(currentUser)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비정상적인 요청입니다.");
        }

        userService.delete(currentUser);
    }
}
