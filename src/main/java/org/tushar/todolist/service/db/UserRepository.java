package org.tushar.todolist.service.db;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.tushar.todolist.dao.models.User;

import java.util.Optional;

//@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserName(@NotNull String userName);
}
