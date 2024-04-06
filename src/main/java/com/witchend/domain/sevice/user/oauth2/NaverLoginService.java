package com.witchend.domain.sevice.user.oauth2;

import com.witchend.domain.RandomNicknameGenerator;
import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.enums.UserRole;
import com.witchend.domain.enums.UserStatus;
import com.witchend.domain.repository.UserRepository;
import com.witchend.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NaverLoginService implements SocialOauth2Service{

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RandomNicknameGenerator nicknameGenerator;

    @Value("${jwt.expiredMs}") private String expiredMs;

    @Override
    public String login(Map<String, Object> attributes) {

        // 메시지 상태 확인 후, 성공적으로 정보를 가져왔는지 검증
        if (!"success".equals(attributes.get("message").toString())) {
            throw new OAuth2AuthenticationException("OAuth2 공급자로부터 사용자 정보를 성공적으로 가져오지 못했습니다.");
        }
        // attributes에서 response를 추출하여 사용자 정보 설정
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String id = response.get("id").toString();
        Optional<UserEntity> userOpt = userRepository.findByUsername(id);
        String role = "USER";
        UserEntity userEntity = new UserEntity();
        if (userOpt.isEmpty()) {
            userEntity.setUsername(response.get("id").toString());
            userEntity.setEmail(response.get("email").toString());
            userEntity.setNickname(nicknameGenerator.generateRandomNickname("NAVER"));
            userEntity.setRole(UserRole.ROLE_USER);
            userEntity.setStatus(UserStatus.ACTIVE);
            userEntity.setPassword(UUID.randomUUID().toString());
            userRepository.save(userEntity);
        } else {
            String gettedRole = userOpt.get().getRole().toString();
            UserRole userRole = UserRole.fromRoleString(gettedRole);
            role = userRole.getDescription();
        }
        return jwtUtil.createJwt(response.get("id").toString(), role, Long.parseLong(expiredMs));
    }
}
