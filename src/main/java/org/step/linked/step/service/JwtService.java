package org.step.linked.step.service;

import io.jsonwebtoken.Claims;
import org.step.linked.step.model.User;

public interface JwtService {

    String createToken(User user);

    Boolean validateToken(String token);

    Claims extractTokenClaims(String token);
}
