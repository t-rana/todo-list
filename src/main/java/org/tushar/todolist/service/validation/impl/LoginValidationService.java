package org.tushar.todolist.service.validation.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.tushar.todolist.dao.request.LoginRequest;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.validation.ValidationService;

import java.util.Arrays;
import java.util.List;

@Service
public class LoginValidationService extends ValidationService<LoginRequest> {

    private final List<ValidationRule<LoginRequest>> loginRules = Arrays.asList(
            request -> StringUtils.isBlank(request.getRequestId()) ?
                    ValidationResult.invalid("requestId", "requestId cannot be blank") : ValidationResult.valid(),
            request -> StringUtils.isBlank(request.getUserName()) ? ValidationResult.invalid("userName", "userName cannot be blank") :
                    ValidationResult.valid(),
            request -> StringUtils.isBlank(request.getPassword()) ? ValidationResult.invalid("password", "password cannot be blank") :
                    ValidationResult.valid()
    );


    public void validate(LoginRequest request) throws ValidationException {
        validate(request, loginRules);
    }
}
