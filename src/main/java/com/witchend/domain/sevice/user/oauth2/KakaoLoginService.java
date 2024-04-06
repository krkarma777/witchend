package com.witchend.domain.sevice.user.oauth2;


import com.witchend.domain.RandomNicknameGenerator;
import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.enums.UserRole;
import com.witchend.domain.enums.UserStatus;
import com.witchend.domain.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RandomNicknameGenerator nicknameGenerator;

    @Value("${jwt.expiredMs}") private String expiredMs;

    @Override
    public String login(Map<String, Object> attributes) {

        UserEntity userEntity = new UserEntity();
        String username = attributes.get("id").toString();
        Optional<UserEntity> kakaoUserOpt = userRepository.findByUsername(username);
        String role = "USER";
        if (kakaoUserOpt.isEmpty()) {
            userEntity.setUsername(username);
            userEntity.setEmail(UUID.randomUUID().toString());
            userEntity.setNickname(nicknameGenerator.generateRandomNickname("KAKAO"));
            userEntity.setRole(UserRole.ROLE_USER);
            userEntity.setPassword(UUID.randomUUID().toString());
            userEntity.setStatus(UserStatus.ACTIVE);
            userRepository.save(userEntity);
        } else {
            role = UserRole.fromRoleString(kakaoUserOpt.get().getRole().toString()).toString();
        }

        // 필요한 정보를 바탕으로 JWT 생성 및 로그 출력
        return jwtUtil.createJwt(username, role, Long.parseLong(expiredMs));
    }
}
