package org.tushar.todolist.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tushar.todolist.dao.request.CreateTodoRequest;
import org.tushar.todolist.dao.request.UpdateTodoRequest;
import org.tushar.todolist.dao.response.BaseResponse;
import org.tushar.todolist.exceptions.ServiceException;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.TodoService;
import org.tushar.todolist.service.validation.TodoValidationService;

import java.util.Arrays;

@RestController
@RequestMapping("/item")
public class TodoController {

    private final TodoService todoService;
    private final TodoValidationService todoValidationService;

    public TodoController(@NotNull TodoService todoService,
                          @NotNull TodoValidationService todoValidationService) {
        this.todoService = todoService;
        this.todoValidationService = todoValidationService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<BaseResponse> createTodo(@RequestBody CreateTodoRequest createTodoRequest) throws ServiceException, ValidationException {
        todoValidationService.validateCreateTodoRequest(createTodoRequest);
        return new ResponseEntity<>(todoService.createTodo(createTodoRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<BaseResponse> updateTodo(@PathVariable String id, @RequestBody UpdateTodoRequest updateTodoRequest) throws ServiceException, ValidationException {
        updateTodoRequest.setId(id);
        todoValidationService.validateUpdateTodoRequest(updateTodoRequest);
        return new ResponseEntity<>(todoService.updateTodo(updateTodoRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BaseResponse> deleteTodo(@RequestHeader String requestId, @PathVariable String id) throws ServiceException, ValidationException {
        todoValidationService.validateDeleteTodoRequest(Arrays.asList(requestId, id));
        return new ResponseEntity<>(todoService.deleteTodo(id), HttpStatus.OK);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<BaseResponse> getTodo(@RequestHeader String requestId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int limit,
                                                @RequestParam(defaultValue = "deadline") String sortBy,
                                                @RequestParam(defaultValue = "DESC") String sortDirection) throws ServiceException, ValidationException {
        todoValidationService.validateGetTodoRequest(Arrays.asList(requestId));
        return new ResponseEntity<>(todoService.getTodoItems(page, limit, sortBy, sortDirection), HttpStatus.OK);
    }
}
