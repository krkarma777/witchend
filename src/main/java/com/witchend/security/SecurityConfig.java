package com.witchend.security;


import com.witchend.domain.sevice.security.CustomOAuth2UserService;
import com.witchend.security.handler.CustomAuthenticationSuccessHandler;
import com.witchend.security.jwt.JWTFilter;
import com.witchend.security.jwt.JWTUtil;
import com.witchend.security.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Value("${jwt.expiredMs}")
    String expiredMs;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("/", "/api/v1/auth/**", "/oauth2/**", "/login/social").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll() // 정적 리소스 허용
                                .requestMatchers("/register/**", "/registerProc", "/login/**", "/loginProc", "/").permitAll()
                                .requestMatchers("").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                );
        http
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                        .loginPage("/login/social")
                        .successHandler(customAuthenticationSuccessHandler)
                );

//                .formLogin((authorize) -> authorize
//                                .disable()
//                        .loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
//                        .successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
//
//                        })
//                        .failureHandler(new LoginAuthenticationFailureHandler()).permitAll())
//
        http
                .logout((logoutConfig) ->
                        logoutConfig
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("AuthToken")
                                .permitAll());
        //csrf disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        //Form 로그인 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        //http basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, Long.parseLong(expiredMs)), UsernamePasswordAuthenticationFilter.class);

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
