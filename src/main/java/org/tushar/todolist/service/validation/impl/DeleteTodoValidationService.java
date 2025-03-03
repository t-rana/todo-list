package org.tushar.todolist.service.validation.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.validation.ValidationService;

import java.util.Arrays;
import java.util.List;

@Service
public class DeleteTodoValidationService extends ValidationService<List<String>> {
    private final List<ValidationRule<List<String>>> rules = Arrays.asList(
            list -> StringUtils.isBlank(list.get(0)) ?
                    ValidationResult.invalid("requestId", "requestId cannot be blank") : ValidationResult.valid(),
            list -> StringUtils.isBlank(list.get(1)) ?
                    ValidationResult.invalid("id", "id cannot be blank") : ValidationResult.valid()
    );

    public void validate(List<String> request) throws ValidationException {
        validate(request, rules);
    }
}
