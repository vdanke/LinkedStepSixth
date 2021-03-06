package org.step.linked.step.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.step.linked.step.configuration.security.assets.AllowAdmin;
import org.step.linked.step.configuration.security.assets.AllowAll;
import org.step.linked.step.dto.UserDTO;
import org.step.linked.step.model.User;
import org.step.linked.step.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
//@RolesAllowed({"ROLE_ADMIN", "ROLE_USER", "ROLE_AUTHOR"})
@AllowAll
public class UserController {

    private final UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtos = userService.findAll().stream().map(UserDTO::toUserDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @AllowAdmin
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") String id) {
        log.info("Update");
        return ResponseEntity.ok().build();
    }
}
