package org.step.linked.step.configuration.filter;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.step.linked.step.dto.ResponseFailed;
import org.step.linked.step.service.JwtService;
import org.step.linked.step.service.impl.SerializationDeserializationProcessor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        final int bearerIndex = 7;
        if (authorization == null || authorization.isBlank()) {
            chain.doFilter(request, response);
            return;
        }
        String authToken = authorization.substring(bearerIndex);
        try {
            Boolean isValid = jwtService.validateToken(authToken);
            if (!isValid) {
                chain.doFilter(request, response);
                return;
            }
        } catch (JwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String responseFailedJson = SerializationDeserializationProcessor.serialize(new ResponseFailed(
                    request.getRequestURI(), request.getMethod(), e.getLocalizedMessage(), LocalDateTime.now()
            ));
            response.getWriter().write(responseFailedJson);
            return;
        }
        String roles = request.getHeader("ROLES");
        if (roles == null || roles.isBlank()) {
            chain.doFilter(request, response);
            return;
        }
        String username = jwtService.extractSubject(authToken);
        List<SimpleGrantedAuthority> authorities = Stream.of(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username, null, authorities
        );
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}
