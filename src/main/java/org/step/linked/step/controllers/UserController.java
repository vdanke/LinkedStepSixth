package org.step.linked.step.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.step.linked.step.dto.UserDTO;
import org.step.linked.step.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtos = userService.findAll().stream().map(UserDTO::toUserDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") String id) {
        log.info("Update");
        return ResponseEntity.ok().build();
    }
}
