package org.tushar.todolist.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tushar.todolist.dao.models.User;
import org.tushar.todolist.dao.request.LoginRequest;
import org.tushar.todolist.dao.request.UserCreationRequest;
import org.tushar.todolist.dao.response.FindAllUserResponse;
import org.tushar.todolist.dao.response.LoginResponse;
import org.tushar.todolist.dao.response.UserCreationResponse;
import org.tushar.todolist.exceptions.ServiceException;
import org.tushar.todolist.service.UserService;
import org.tushar.todolist.service.db.UserDao;
import org.tushar.todolist.service.jwt.JwtService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDao userDao;

    public UserServiceImpl(@NotNull AuthenticationManager authenticationManager,
                           @NotNull JwtService jwtService,
                           @NotNull UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDao = userDao;
    }

    @Override
    public UserCreationResponse createUser(UserCreationRequest userCreationRequest) throws ServiceException {
        Optional<User> existingUserOptional = userDao.findByUserName(userCreationRequest.getUserName());
        if (existingUserOptional.isPresent()) {
            throw new ServiceException(String.format("User with %s userName already exist", userCreationRequest.getUserName()));
        }
        Optional<User> createdUserOptional = userDao.createUser(userCreationRequest);
        if (createdUserOptional.isEmpty()) {
            throw new ServiceException("User cannot be created");
        }
        User user = createdUserOptional.get();
        return UserCreationResponse.userCreationResponseBuilder().code(200)
                .description("User created successfully")
                .userName(user.getUsername())
                .userId(user.getUserId()).build();
    }

    @Override
    public FindAllUserResponse findAll() {
        List<User> allUsers = userDao.findAll();
        if (CollectionUtils.isEmpty(allUsers)) {
            return FindAllUserResponse.findAllUserResponseBuilder().code(200)
                    .description("No users found in database")
                    .build();
        }

        return FindAllUserResponse.findAllUserResponseBuilder().code(200)
                .description("success")
                .users(allUsers)
                .build();
    }

    @Override
    public LoginResponse login(@NotNull LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        if (!authenticate.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid user request");
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setCode(200);
        loginResponse.setDescription("user authenticated");
        loginResponse.setToken(jwtService.generateToken(loginRequest.getUserName()));
        return loginResponse;
    }
}
