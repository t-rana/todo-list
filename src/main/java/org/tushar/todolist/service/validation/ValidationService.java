package org.tushar.todolist.service.validation;

import org.apache.commons.collections4.CollectionUtils;
import org.tushar.todolist.dao.CustomError;
import org.tushar.todolist.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidationService<T> {
    protected void validate(T request, List<ValidationRule<T>> rules) throws ValidationException {
        if (request == null) {
            throw new ValidationException("request cannot be null");
        }

        List<CustomError> errors = new ArrayList<>();
        for (ValidationRule<T> rule : rules) {
            ValidationResult result = rule.validate(request);
            if (!result.isValid()) {
                errors.add(new CustomError(400, result.getField(), result.getMessage()));
            }
        }

        if (CollectionUtils.isNotEmpty(errors)) {
            throw new ValidationException(errors);
        }
    }

    protected interface ValidationRule<T> {
        ValidationResult validate(T request);
    }

//    protected record ValidationResult(boolean isValid, String field, String message) {
//        public static ValidationResult valid() {
//            return new ValidationResult(true, null, null);
//        }
//
//        public static ValidationResult invalid(String field, String message) {
//            return new ValidationResult(false, field, message);
//        }
//    }


    protected static class ValidationResult {
        private final boolean isValid;
        private final String field;
        private final String message;

        public ValidationResult(boolean isValid, String field, String message) {
            this.isValid = isValid;
            this.field = field;
            this.message = message;
        }

        public static ValidationResult valid() {
            return new ValidationResult(true, null, null);
        }

        public static ValidationResult invalid(String field, String message) {
            return new ValidationResult(false, field, message);
        }

        public boolean isValid() {
            return isValid;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
