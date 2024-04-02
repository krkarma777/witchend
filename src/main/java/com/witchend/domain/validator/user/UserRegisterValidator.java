package com.witchend.domain.validator.user;


import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.sevice.user.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserRegisterValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserEntity user = (UserEntity) target;

        // 아이디 중복 검사
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "duplicate.username", "이미 사용 중인 아이디입니다.");
        }

        // 이메일 중복 검사
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "duplicate.email", "이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 길이 및 조합 검사
        if (!user.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\W_]).{8,20}$")) {
            errors.rejectValue("password", "invalid.password", "비밀번호는 영문, 숫자, 특수문자의 조합으로 8~20자여야 합니다.");
        }
    }
}
