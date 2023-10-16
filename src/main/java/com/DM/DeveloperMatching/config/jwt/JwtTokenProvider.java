package com.DM.DeveloperMatching.config.jwt;

import com.DM.DeveloperMatching.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    //사용자 정보와 토큰의 만료시간을 받아 토큰 생성
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return createToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    //토큰 생성 메소드, 실제로 토큰을 생성하는 로직
    private String createToken(Date expiry,User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("uId",user.getUId())
                .claim("userName",user.getUserName())
                .claim("email",user.getEmail())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
    //받은 토큰이 유효한지 검증하는 메소드
    public boolean validToken(String token) {
        try{
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) //서명 검증할 때 사용할 비밀키 설정
                    .parseClaimsJws(token);                      //실제 파싱 & 검증
            return true;  //아무 에러가 나지 않음
        }catch (Exception e){  //복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }

    //JWT 토큰에서 인증 정보를 추출
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.
                security.core.userdetails.User(claims.getSubject(),"",authorities), token, authorities);
    }

    //토큰에서 사용자 ID 추출
    public Long extractUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    //토큰에서 Claims 객체 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();  //payload부분 반환함
    }
}
