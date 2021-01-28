package org.step.linked.step.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.step.linked.step.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final List<User> USER_DB;

    static {
        USER_DB = new ArrayList<>();
        USER_DB.add(new User("abc", "foo@mail.ru", "password"));
        USER_DB.add(new User("cda", "bar@mail.ru", "password"));
        USER_DB.add(new User("bdc", "top@mail.ru", "password"));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<User> getAllUsers() {
        return USER_DB;
    }
}
