package com.witchend.domain.sevice.security;

import com.witchend.domain.entity.User;
import com.witchend.domain.enums.UserRole;
import com.witchend.domain.enums.UserStatus;
import com.witchend.domain.exception.UserLoginAuthenticatedException;
import com.witchend.domain.validator.user.UserAuthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAuthValidator userAuthValidator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userAuthValidator.getCurrentUserByUsername(username);

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new UserLoginAuthenticatedException("이메일 인증이 필요합니다.");
        }
        if (user.getStatus() == UserStatus.BANNED) {
            throw new UserLoginAuthenticatedException("차단된 사용자입니다.");
        }

        return new CustomUserDetails(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserRole role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
}
