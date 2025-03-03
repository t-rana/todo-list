package org.tushar.todolist.service.jwt.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tushar.todolist.exceptions.ServiceException;
import org.tushar.todolist.service.jwt.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.security.key}")
    private String jwtSecurityKey;

    @Value("${jwt.expiration.time}")
    private long jwtTokenExpirationTime;

    @Override
    public String generateToken(String userName) {
        return createToken(new HashMap<>(), userName);
    }

    @Override
    public String extractUserName(@NotNull String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public Date extractExpirationDate(@NotNull String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenValid(@NotNull String token, @NotNull String userName) {
        return StringUtils.equals(userName, extractUserName(token)) && !isTokenExpired(token);
    }

    @Override
    public String refreshToken(@NotNull String token) throws ServiceException {
        // token has already expired
        if (this.isTokenExpired(token)) {
            throw new ServiceException("provided jwt token has already expired");
        }

        String userName = extractUserName(token);

        return createToken(new HashMap<>(), userName);
    }


    private boolean isTokenExpired(@NotNull String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private String createToken(HashMap<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenExpirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        String sanitizedKey = jwtSecurityKey.replaceAll("[^A-Za-z0-9+/=]", "");
        byte[] keyBytes = Decoders.BASE64.decode(sanitizedKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaims(@NotNull String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
