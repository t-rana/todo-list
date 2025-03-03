package org.tushar.todolist.service.db;

import org.jetbrains.annotations.NotNull;
import org.tushar.todolist.dao.models.User;
import org.tushar.todolist.dao.request.UserCreationRequest;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> createUser(@NotNull UserCreationRequest userCreationRequest);
    Optional<User> findByUserName(@NotNull String userName);
    List<User> findAll();
}
