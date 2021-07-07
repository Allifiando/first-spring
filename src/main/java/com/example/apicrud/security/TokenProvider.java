package com.example.apicrud.security;

import com.example.apicrud.Utils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String base64Secret;
    private final long tokenValidityInSeconds;
    private final long tokenValidityInSecondsForRememberMe;

    private Key key;

    public TokenProvider(
            @Value("${jwt.base64-secret}") String base64Secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${jwt.token-validity-in-seconds-for-remember-me}") long tokenValidityInSecondsForRememberMe) {
        this.base64Secret = base64Secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        LocalDateTime validity = Utils.getCurrentDateTime();
        ZonedDateTime zoned = validity.atZone(Utils.ZONE_JAKARTA);
        long epochSeconds = zoned.toInstant().getEpochSecond();
        epochSeconds += tokenValidityInSeconds;

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(java.sql.Date.from(Instant.ofEpochSecond(epochSeconds)))
                .compact();
    }

    public String createToken(Set<User> authentication, String email) {
        String authorities = authentication.stream()
                .map(User::getUsername)
                .collect(Collectors.joining(","));

        LocalDateTime validity = Utils.getCurrentDateTime();
        ZonedDateTime zoned = validity.atZone(Utils.ZONE_JAKARTA);
        long epochSeconds = zoned.toInstant().getEpochSecond();
        epochSeconds += tokenValidityInSeconds;

        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(Date.from(Instant.ofEpochSecond(epochSeconds)))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            // log.info(jwt.toString());
            Claims claims = jwt.getBody();
            // log.info(claims.getSubject());
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {0}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {0}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {0}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {0}", e);
        }
        return false;
    }
}
