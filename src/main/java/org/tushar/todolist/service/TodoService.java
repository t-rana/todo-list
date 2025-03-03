package org.tushar.todolist.service;

import org.jetbrains.annotations.NotNull;
import org.tushar.todolist.dao.request.CreateTodoRequest;
import org.tushar.todolist.dao.request.UpdateTodoRequest;
import org.tushar.todolist.dao.response.GetTodoItemResponse;
import org.tushar.todolist.dao.response.TodoResponse;
import org.tushar.todolist.exceptions.ServiceException;

public interface TodoService {
    TodoResponse createTodo(@NotNull CreateTodoRequest createTodoRequest) throws ServiceException;

    TodoResponse updateTodo(@NotNull UpdateTodoRequest updateTodoRequest) throws ServiceException;

    TodoResponse deleteTodo(@NotNull String id) throws ServiceException;

    GetTodoItemResponse getTodoItems(int page, int limit, String sortBy, String sortDirection) throws ServiceException;
}
