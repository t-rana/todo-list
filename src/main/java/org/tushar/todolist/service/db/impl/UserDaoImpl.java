package org.tushar.todolist.service.db.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tushar.todolist.dao.models.User;
import org.tushar.todolist.dao.request.UserCreationRequest;
import org.tushar.todolist.service.db.UserDao;
import org.tushar.todolist.service.db.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDaoImpl(@NotNull UserRepository userRepository, @NotNull PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> createUser(@NotNull UserCreationRequest userCreationRequest) {
        return Optional.of(userRepository.save(getUser(userCreationRequest)));
    }

    @Override
    public Optional<User> findByUserName(@NotNull String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private User getUser(@NotNull UserCreationRequest userCreationRequest) {
        User user = new User();
        user.setUserName(userCreationRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }
}
