package com.witchend.domain.sevice.user;


import com.witchend.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void registerProcess(UserEntity newUser) {
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
}
