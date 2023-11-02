package com.DM.DeveloperMatching.config;

import com.DM.DeveloperMatching.config.jwt.JwtTokenFilter;
import com.DM.DeveloperMatching.domain.UserRole;
import com.DM.DeveloperMatching.service.RegisterAndLoginService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final RegisterAndLoginService registerAndLoginService;
    //시크릿 키 들어갈 자리


    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorize ->
                        authorize
                                .requestMatchers("/jwt-api-login/info").authenticated()
                                .requestMatchers("/jwt-api-login/admin/**").hasAuthority(UserRole.ADMIN.name())
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenFilter(registerAndLoginService, secretKey),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
