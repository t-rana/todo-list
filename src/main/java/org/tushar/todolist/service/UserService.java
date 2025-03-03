package org.tushar.todolist.service;

import org.jetbrains.annotations.NotNull;
import org.tushar.todolist.dao.request.LoginRequest;
import org.tushar.todolist.dao.request.UserCreationRequest;
import org.tushar.todolist.dao.response.FindAllUserResponse;
import org.tushar.todolist.dao.response.LoginResponse;
import org.tushar.todolist.dao.response.UserCreationResponse;
import org.tushar.todolist.exceptions.ServiceException;

public interface UserService {
    UserCreationResponse createUser(UserCreationRequest userCreationRequest) throws ServiceException;
    FindAllUserResponse findAll();
    LoginResponse login(@NotNull LoginRequest loginRequest);
}
