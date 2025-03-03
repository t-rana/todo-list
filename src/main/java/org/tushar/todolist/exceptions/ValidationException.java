package org.tushar.todolist.exceptions;

import lombok.Getter;
import org.tushar.todolist.dao.CustomError;

import java.util.List;

@Getter
public class ValidationException extends Exception {
    private List<CustomError> errors;
    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(List<CustomError> errors) {
        this.errors = errors;
    }
}
