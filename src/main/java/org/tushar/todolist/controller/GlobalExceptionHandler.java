package org.tushar.todolist.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.tushar.todolist.dao.response.BaseResponse;
import org.tushar.todolist.exceptions.ServiceException;
import org.tushar.todolist.exceptions.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(exception = ValidationException.class)
    public ResponseEntity<BaseResponse> handleValidationException(ValidationException exception) {
        return new ResponseEntity<>(BaseResponse.builder().code(400)
                .description("request validation failed")
                .errors(exception.getErrors())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = ServiceException.class)
    public ResponseEntity<BaseResponse> handleServiceException(ServiceException exception) {
        return new ResponseEntity<>(BaseResponse.builder().code(400)
                .description(exception.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<BaseResponse> handleGenericException(Exception exception) {
        return new ResponseEntity<>(BaseResponse.builder().code(501)
                .description(exception.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        BaseResponse response = BaseResponse.builder()
                .code(statusCode.value())
                .description("An unexpected error occurred: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(response, headers, statusCode);
    }
}
