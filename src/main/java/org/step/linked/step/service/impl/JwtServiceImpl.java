package org.step.linked.step.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.step.linked.step.model.User;
import org.step.linked.step.service.JwtService;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final ObjectMapper objectMapper;

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
                .signWith(Keys.hmacShaKeyFor("verysecdskajndkwbdiahuwiundhxiwuhqduixgnwquydgiqwuhnxdiuretkey".getBytes()))
                .compact();
    }

    @Override
    public Boolean validateToken(String token) {
        return null;
    }

    @Override
    public Claims extractTokenClaims(String token) {
        return null;
    }
}
