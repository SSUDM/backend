package com.DM.DeveloperMatching.config.jwt;

import com.DM.DeveloperMatching.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;


@RequiredArgsConstructor
@Service
public class JwtTokenUtils {
    @Autowired private final JwtProperties jwtProperties;

    /**
     * JWT 토큰 발급
     * Claim은 JWT토큰에 들어갈 정보를 넣은 것
     * Claim에 userId(User 테이블에 저장되는 id), email을 저장할 것
     * 만료기간은 1일로 설정
     */
    public String createToken(String userId,String email, String key) {
        Date date = new Date();
        Date expiration = new Date(date.getTime() + Duration.ofDays(1).toMillis());

        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("email", email);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(date)
                .setExpiration(expiration)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    /**
     * secretkey를 사용해서 Token Parsing
     */
    public Claims extractClaims(String token, String secretKey) {
        Claims parsingBody = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();

        return parsingBody;
    }

    /**
     * JWT 토큰 유효성 검증
     */
    public boolean validateToken(String token,String secretKey) {
        try {
            extractClaims(token, secretKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Claim에서 userId를 추출
     */
    public String extractUserId(String token, String secretKey) {
        return extractClaims(token, secretKey)
                .get("userId").toString();
    }

    /**
     * Claim에서 email을 추출
     */
    public String extractUserEmail(String token, String secretKey) {
        return extractClaims(token, secretKey)
                .get("email").toString();
    }

    /**
     * 발급된 Token이 만료 시간이 지났는지 check
     */
    public boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();

        //Token의 만료날짜가 현재 시간의 이전 시간인지 check
        return expiredDate.before(new Date());
    }
}
