package org.step.linked.step.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.step.linked.step.model.User;
import org.step.linked.step.service.JwtService;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final ObjectMapper objectMapper;

    @Value("${token.secret}")
    private String tokenSecret;

    @Override
    @SneakyThrows
    public String createToken(User user) {
        HashMap<String, Object> claimsMap = new HashMap<>();

        String jsonUser = objectMapper.writeValueAsString(user);

        claimsMap.put("user", jsonUser);

        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + 8600 * 1000);

        return Jwts
                .builder()
                .setClaims(claimsMap)
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(tokenSecret.getBytes()))
                .compact();
    }

    @Override
    public Boolean validateToken(String token) {
        return extractTokenClaims(token)
                .getExpiration()
                .after(new Date());
    }

    @Override
    public Claims extractTokenClaims(String token) {
        String secret = Base64.getEncoder().encodeToString(tokenSecret.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String extractSubject(String token) {
        return this.extractTokenClaims(token).getSubject();
    }
}
