package com.witchend.domain.sevice.oauth2;

import com.witchend.domain.enums.CharacterClass;
import com.witchend.domain.generator.GameCharacterGenerator;
import com.witchend.domain.generator.RandomNicknameGenerator;
import com.witchend.domain.entity.User;
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
    private final GameCharacterGenerator gameCharacterGenerator;

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
        Optional<User> userOpt = userRepository.findByUsername(id);
        String role = "USER";
        User newUser = new User();
        if (userOpt.isEmpty()) {
            newUser.setUsername(response.get("id").toString());
            newUser.setEmail(response.get("email").toString());
            newUser.setNickname(nicknameGenerator.generateRandomNickname("NAVER"));
            newUser.setRole(UserRole.ROLE_USER);
            newUser.setStatus(UserStatus.ACTIVE);
            newUser.setPassword(UUID.randomUUID().toString());
            User savedUser = userRepository.save(newUser);

            gameCharacterGenerator.generate(CharacterClass.DIAMOND, savedUser);
        } else {
            String gettedRole = userOpt.get().getRole().toString();
            UserRole userRole = UserRole.fromRoleString(gettedRole);
            role = userRole.getDescription();
        }
        return jwtUtil.createJwt(response.get("id").toString(), role, Long.parseLong(expiredMs));
    }
}
