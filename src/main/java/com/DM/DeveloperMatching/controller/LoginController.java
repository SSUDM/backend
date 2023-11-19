package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.config.jwt.JwtTokenUtils;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.Login.LoginRequest;
import com.DM.DeveloperMatching.service.RegisterAndLoginService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class LoginController {

    private final RegisterAndLoginService registerAndLoginService;
//    @Value("${jwt.secret-key}")
//    private static String secretKey;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User loginUser = registerAndLoginService.login(loginRequest);

        //로그인 email이나 password가 틀린 경우 global error return
        if (loginUser == null) {
            return ResponseEntity.badRequest().body("로그인 Email 또는 설정한 비밀번호가 틀렸습니다.");
        }

        //로그인을 성공했으면 이제 Jwt 토큰을 발급해준다.
        String secretKey = " "; //시크릿 키 들어갈 자리

        long expireTimeMs = 1000 * 60 * 30;    // Token 유효 시간 = 30분
        String jwtToken = JwtTokenUtils.createToken(loginUser.getUId(), loginUser.getEmail(), secretKey, expireTimeMs);

//        Claims claims = JwtTokenUtils.extractClaims(jwtToken, secretKey);
//        System.out.println("claims = " + claims);
//        System.out.println("claims.getIssuedAt() = " + claims.getIssuedAt());
//        System.out.println("claims.getExpiration() = " + claims.getExpiration());

        return ResponseEntity.ok().body(jwtToken);
    }
}
