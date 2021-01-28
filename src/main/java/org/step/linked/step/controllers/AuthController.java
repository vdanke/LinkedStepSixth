package org.step.linked.step.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.step.linked.step.dto.request.UserLoginRequest;
import org.step.linked.step.dto.request.UserRegistrationRequest;
import org.step.linked.step.dto.response.UserLoginResponse;
import org.step.linked.step.dto.response.UserRegistrationResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration")
    public ResponseEntity<UserRegistrationResponse> registration(@RequestBody UserRegistrationRequest request) {
        return ResponseEntity.ok().build();
    }
}
