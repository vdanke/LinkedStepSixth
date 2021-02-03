package org.step.linked.step.configuration.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.step.linked.step.model.User;
import org.step.linked.step.service.JwtService;
import org.step.linked.step.service.SerializationDeserializationService;
import org.step.linked.step.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final SerializationDeserializationService<User, String> serializationDeserializationService;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = serializationDeserializationService.deserialize(request.getInputStream(), User.class);
            if (user == null) {
                return null;
            }
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            );
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = (String) authResult.getPrincipal();

        User userByUsername = userService.findByUsername(username);

        String token = jwtService.createToken(userByUsername);

        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(
                failed.getLocalizedMessage()
        );
    }
}
