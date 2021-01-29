package org.step.linked.step.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.step.linked.step.model.User;

import java.util.Optional;

@Repository
public interface UserRepositoryImpl extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
