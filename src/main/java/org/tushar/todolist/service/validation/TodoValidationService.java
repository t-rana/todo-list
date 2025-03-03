package org.tushar.todolist.service.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.tushar.todolist.dao.request.CreateTodoRequest;
import org.tushar.todolist.dao.request.UpdateTodoRequest;
import org.tushar.todolist.exceptions.ValidationException;
import org.tushar.todolist.service.validation.impl.CreateTodoValidationService;
import org.tushar.todolist.service.validation.impl.DeleteTodoValidationService;
import org.tushar.todolist.service.validation.impl.GetTodoValidationService;
import org.tushar.todolist.service.validation.impl.UpdateTodoValidationService;

import java.util.List;

@Service
public class TodoValidationService {

    private final CreateTodoValidationService createTodoValidationService;
    private final UpdateTodoValidationService updateTodoValidationService;
    private final DeleteTodoValidationService deleteTodoValidationService;
    private final GetTodoValidationService getTodoValidationService;

    public TodoValidationService(@NotNull CreateTodoValidationService createTodoValidationService,
                                 @NotNull UpdateTodoValidationService updateTodoValidationService,
                                 @NotNull DeleteTodoValidationService deleteTodoValidationService,
                                 @NotNull GetTodoValidationService getTodoValidationService) {
        this.createTodoValidationService = createTodoValidationService;
        this.updateTodoValidationService = updateTodoValidationService;
        this.deleteTodoValidationService = deleteTodoValidationService;
        this.getTodoValidationService = getTodoValidationService;
    }

    public void validateCreateTodoRequest(@NotNull CreateTodoRequest createTodoRequest) throws ValidationException {
        createTodoValidationService.validate(createTodoRequest);
    }

    public void validateUpdateTodoRequest(@NotNull UpdateTodoRequest updateTodoRequest) throws ValidationException {
        updateTodoValidationService.validate(updateTodoRequest);
    }

    public void validateDeleteTodoRequest(List<String> paramsToValidate) throws ValidationException {
        deleteTodoValidationService.validate(paramsToValidate);
    }

    public void validateGetTodoRequest(List<String> paramsToValidate) throws ValidationException {
        getTodoValidationService.validate(paramsToValidate);
    }
}
