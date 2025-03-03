package org.tushar.todolist.service.validation.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.tushar.todolist.dao.request.UpdateTodoRequest;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.validation.ValidationService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UpdateTodoValidationService extends ValidationService<UpdateTodoRequest> {
    private final List<ValidationRule<UpdateTodoRequest>> rules = Arrays.asList(
            request -> StringUtils.isBlank(request.getRequestId()) ?
                    ValidationResult.invalid("requestId", "requestId cannot be blank") : ValidationResult.valid(),
            request -> StringUtils.isBlank(request.getId()) ?
                    ValidationResult.invalid("id", "id cannot be blank") : ValidationResult.valid(),
            request -> (StringUtils.isAllBlank(request.getTitle(), request.getDescription()) && Objects.isNull(request.getDeadline())) ?
                    ValidationResult.invalid("title / description / deadline", "all fields cannot be blank") :
                    ValidationResult.valid()
    );

    public void validate(UpdateTodoRequest createTodoRequest) throws ValidationException {
        validate(createTodoRequest, rules);
    }
}
