package org.tushar.todolist.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tushar.todolist.dao.request.LoginRequest;
import org.tushar.todolist.dao.request.UserCreationRequest;
import org.tushar.todolist.dao.response.BaseResponse;
import org.tushar.todolist.exceptions.ServiceException;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.UserService;
import org.tushar.todolist.service.validation.UserValidationService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    private final UserValidationService userValidationService;

    public UserController(@NotNull UserService userService,
                          @NotNull UserValidationService userValidationService) {
        this.userService = userService;
        this.userValidationService = userValidationService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserCreationRequest userCreationRequest) throws ServiceException, ValidationException {
        userValidationService.validateCreateUserRequest(userCreationRequest);
        return new ResponseEntity<>(userService.createUser(userCreationRequest), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<BaseResponse> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest loginRequest) throws ValidationException {
        userValidationService.validateLoginRequest(loginRequest);
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }
}
