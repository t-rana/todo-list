package org.tushar.todolist.service.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.tushar.todolist.dao.request.LoginRequest;
import org.tushar.todolist.dao.request.UserCreationRequest;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.validation.impl.LoginValidationService;
import org.tushar.todolist.service.validation.impl.UserCreationValidationService;

@Service
public class UserValidationService {
    private final UserCreationValidationService userCreationValidationService;
    private final LoginValidationService loginValidationService;

    public UserValidationService(@NotNull UserCreationValidationService userCreationValidationService,
                                 @NotNull LoginValidationService loginValidationService) {
        this.userCreationValidationService = userCreationValidationService;
        this.loginValidationService = loginValidationService;
    }


    public void validateCreateUserRequest(UserCreationRequest userCreationRequest) throws ValidationException {
        userCreationValidationService.validate(userCreationRequest);
    }

    public void validateLoginRequest(LoginRequest loginRequest) throws ValidationException {
        loginValidationService.validate(loginRequest);
    }
}
