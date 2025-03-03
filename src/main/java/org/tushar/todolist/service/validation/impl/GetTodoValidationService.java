package org.tushar.todolist.service.validation.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.validation.ValidationService;

import java.util.Arrays;
import java.util.List;

@Service
public class GetTodoValidationService extends ValidationService<List<String>> {
    List<ValidationRule<List<String>>> rules = Arrays.asList(
            list -> StringUtils.isBlank(list.getFirst()) ?
                    ValidationResult.invalid("requestId", "requestId cannot be blank") :
                    ValidationResult.valid()
    );

    public void validate(List<String> paramsToValidate) throws ValidationException {
        validate(paramsToValidate, rules);
    }
}
