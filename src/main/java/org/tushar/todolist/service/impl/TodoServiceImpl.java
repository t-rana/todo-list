package org.tushar.todolist.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tushar.todolist.TodoListApplication;
import org.tushar.todolist.dao.models.TodoItem;
import org.tushar.todolist.dao.request.CreateTodoRequest;
import org.tushar.todolist.dao.request.UpdateTodoRequest;
import org.tushar.todolist.dao.response.GetTodoItemResponse;
import org.tushar.todolist.dao.response.TodoResponse;
import org.tushar.todolist.exceptions.ServiceException;
import org.tushar.todolist.service.TodoService;
import org.tushar.todolist.service.db.TodoItemDao;

import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoItemDao todoItemDao;
    private final TodoListApplication todoListApplication;

    public TodoServiceImpl(@NotNull TodoItemDao todoItemDao, TodoListApplication todoListApplication) {
        this.todoItemDao = todoItemDao;
        this.todoListApplication = todoListApplication;
    }

    @Override
    public TodoResponse createTodo(@NotNull CreateTodoRequest createTodoRequest) throws ServiceException {
        TodoItem todoItem = todoItemDao.createTodoItem(createTodoRequest);
        if (todoItem == null) {
            throw new ServiceException("cannot create todo");
        }

        return TodoResponse.todoResponseBuilder().code(200)
                .description("todo created successfully")
                .id(todoItem.getId())
                .build();
    }

    @Override
    public TodoResponse updateTodo(@NotNull UpdateTodoRequest updateTodoRequest) throws ServiceException {
        Optional<TodoItem> existingItemOptional = todoItemDao.findById(updateTodoRequest.getId());
        if (existingItemOptional.isEmpty()) {
            throw new ServiceException(String.format("todo item with id %s does not exist", updateTodoRequest.getId()));
        }
        TodoItem updatedItem = todoItemDao.update(existingItemOptional.get(), updateTodoRequest);
        return TodoResponse.todoResponseBuilder()
                .code(200)
                .description("todo updated successfully")
                .id(updatedItem.getId())
                .build();
    }

    @Override
    public TodoResponse deleteTodo(@NotNull String id) throws ServiceException {
        Optional<TodoItem> todoItemOptional = todoItemDao.findById(id);
        if (todoItemOptional.isEmpty()) {
            throw new ServiceException(String.format("No todo item found with id %s", id));
        }
        todoItemDao.deleteById(id);
        return TodoResponse.todoResponseBuilder()
                .code(200)
                .description("Todo item deleted successfully")
                .id(id)
                .build();
    }

    @Override
    public GetTodoItemResponse getTodoItems(int page, int limit, String sortBy, String sortDirection) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ServiceException("User not authenticated");
        }
        String userName = authentication.getName();
        Pageable pageable = getPageable(page, limit, sortBy, sortDirection);
        Page<TodoItem> currentPageItems = todoItemDao.getItems(userName, pageable);

        GetTodoItemResponse response = new GetTodoItemResponse();
        response.setCode(200);
        response.setDescription("success");
        response.setTodos(currentPageItems.getContent());
        response.setCurrentPage(currentPageItems.getNumber());
        response.setTotalPages(currentPageItems.getTotalPages());
        response.setTotalItems(currentPageItems.getTotalElements());
        response.setPageSize(currentPageItems.getSize());
        return response;
    }

    @NotNull
    private Pageable getPageable(int page, int limit, @NotNull String sortBy, @NotNull String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        return PageRequest.of(page, limit, sort);
    }
}
