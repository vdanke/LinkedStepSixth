package org.step.linked.step.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.step.linked.step.dto.request.UserLoginRequest;
import org.step.linked.step.dto.request.UserRegistrationRequest;
import org.step.linked.step.dto.response.UserLoginResponse;
import org.step.linked.step.dto.response.UserRegistrationResponse;
import org.step.linked.step.model.User;
import org.step.linked.step.model.UserDetailsImpl;
import org.step.linked.step.service.JwtService;
import org.step.linked.step.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(token);
        if (authenticate != null) {
            log.info(authenticate.toString());
            UserDetailsImpl principal = (UserDetailsImpl) authenticate.getPrincipal();
            User user = principal.getUser();
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtService.createToken(user)))
                    .build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/registration")
    public ResponseEntity<UserRegistrationResponse> registration(@Valid @RequestBody UserRegistrationRequest request) {
        log.info(String.format("Registration request {username:%s,time:%s}", request.getUsername(), LocalDateTime.now().toString()));
        User user = User.builder().username(request.getUsername()).password(request.getPassword()).build();
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserRegistrationResponse(savedUser.getId()));
    }
}
