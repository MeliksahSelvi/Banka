package com.meliksah.banka.app.sec.security;

import com.meliksah.banka.app.gen.dto.JwtToken;
import com.meliksah.banka.app.gen.util.HazelCastCacheUtil;
import com.meliksah.banka.app.sec.enums.EnumJwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {

    @Value("${banka.jwt.security.app.key}")
    private String APP_KEY;

    @Value("${banka.jwt.security.expire.time}")
    private Long EXPIRE_TIME;

    private final HazelCastCacheUtil hazelCastCacheUtil;

    public JwtToken genereteJwtToken(Authentication authentication) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);

        String token = Jwts.builder()
                .setSubject(Long.toString(jwtUserDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_KEY)
                .compact();

        String fullToken = generateFullToken(token);

        JwtToken jwtToken = createJwtToken(fullToken, jwtUserDetails.getId());

        return jwtToken;
    }

    private String generateFullToken(String token) {
        String bearer = EnumJwtConstant.BEARER.toString();
        String fullToken = bearer + token;
        return fullToken;
    }

    private JwtToken createJwtToken(String fullToken, Long userId) {
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(fullToken);
        jwtToken.setTokenIssuedTime(EXPIRE_TIME);
        jwtToken.setUserId(userId);

        return jwtToken;
    }

    public Long findUserIdByToken(String token) {

        Jws<Claims> claimsJws = parseToken(token);

        String userIdStr = claimsJws
                .getBody()
                .getSubject();

        Long userId = Long.parseLong(userIdStr);
        return userId;
    }

    private Jws<Claims> parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(APP_KEY)
                .parseClaimsJws(token);
        return claimsJws;
    }

    public boolean validateToken(String token) {

        boolean isValid;
        try {
            isValid = !isTokenExpired(token);
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }

    private boolean isTokenExpired(String token) {

        JwtToken jwtToken = hazelCastCacheUtil.readTokenMap(token);

        Long userIdByToken = findUserIdByToken(token);

        if (jwtToken == null) {
            return true;
        }

        long expireMs = EXPIRE_TIME - (Instant.now().getEpochSecond() - jwtToken.getTokenIssuedTime());

        if (expireMs > 0 && userIdByToken.equals(jwtToken.getUserId())) {

            jwtToken.setTokenIssuedTime(Instant.now().getEpochSecond());
            hazelCastCacheUtil.writeTokenMap(jwtToken);
            return false;
        } else {
            hazelCastCacheUtil.deleteFromTokenMap(token);
            return true;
        }
    }
}
