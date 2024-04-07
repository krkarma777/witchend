package com.witchend.domain.sevice.oauth2;

import com.witchend.domain.entity.user.User;
import com.witchend.domain.enums.CharacterClass;
import com.witchend.domain.enums.UserRole;
import com.witchend.domain.enums.UserStatus;
import com.witchend.domain.generator.GameCharacterGenerator;
import com.witchend.domain.generator.RandomNicknameGenerator;
import com.witchend.domain.sevice.user.UserService;
import com.witchend.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoLoginService implements SocialOauth2Service{

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final RandomNicknameGenerator nicknameGenerator;
    private final GameCharacterGenerator gameCharacterGenerator;

    @Value("${jwt.expiredMs}") private String expiredMs;

    @Override
    public String login(Map<String, Object> attributes) {

        User newUser = new User();
        String username = attributes.get("id").toString();
        Optional<User> kakaoUserOpt = userService.findByUsername(username);
        String role = "USER";
        if (kakaoUserOpt.isEmpty()) {
            newUser.setUsername(username);
            newUser.setEmail(UUID.randomUUID().toString());
            newUser.setNickname(nicknameGenerator.generateRandomNickname("KAKAO"));
            newUser.setRole(UserRole.ROLE_USER);
            newUser.setPassword(UUID.randomUUID().toString());
            newUser.setStatus(UserStatus.ACTIVE);
            User savedUser = userService.save(newUser);

            gameCharacterGenerator.generate(CharacterClass.DIAMOND, savedUser);
        } else {
            role = UserRole.fromRoleString(kakaoUserOpt.get().getRole().toString()).toString();
        }

        // 필요한 정보를 바탕으로 JWT 생성 및 로그 출력
        return jwtUtil.createJwt(username, role, Long.parseLong(expiredMs));
    }
}
