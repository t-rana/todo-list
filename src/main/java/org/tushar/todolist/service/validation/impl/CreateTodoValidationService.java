package org.tushar.todolist.service.validation.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.tushar.todolist.dao.request.CreateTodoRequest;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.validation.ValidationService;

import java.util.Arrays;
import java.util.List;

@Service
public class CreateTodoValidationService extends ValidationService<CreateTodoRequest> {
    private final List<ValidationRule<CreateTodoRequest>> rules = Arrays.asList(
            request -> StringUtils.isBlank(request.getRequestId()) ?
                    ValidationResult.invalid("requestId", "requestId cannot be blank") : ValidationResult.valid(),
            request -> StringUtils.isBlank(request.getTitle()) ? ValidationResult.invalid("title", "title cannot be blank") :
                    ValidationResult.valid(),
            request -> StringUtils.isBlank(request.getDescription()) ? ValidationResult.invalid("description", "description cannot be blank") :
                    ValidationResult.valid()
    );

    public void validate(CreateTodoRequest createTodoRequest) throws ValidationException {
        validate(createTodoRequest, rules);
    }
}
