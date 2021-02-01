package org.step.linked.step.service;

import org.step.linked.step.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> findAll();

    User findByUsername(String username);
}
