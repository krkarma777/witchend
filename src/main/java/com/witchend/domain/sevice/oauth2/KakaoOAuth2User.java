package com.witchend.domain.sevice.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class KakaoOAuth2User implements OAuth2User {

    private final OAuth2User oauth2User;
    private final String token;

    public KakaoOAuth2User(OAuth2User oauth2User, String token) {
        this.oauth2User = oauth2User;
        this.token = token;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        Map<String, Object> response = oauth2User.getAttributes();
        return response.get("id").toString();
    }

}
