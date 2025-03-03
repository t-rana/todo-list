package org.tushar.todolist.service.db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.tushar.todolist.dao.models.TodoItem;
import org.tushar.todolist.dao.request.CreateTodoRequest;
import org.tushar.todolist.dao.request.UpdateTodoRequest;
import org.tushar.todolist.exceptions.ServiceException;

import java.util.Optional;

public interface TodoItemDao {
    TodoItem createTodoItem(@NotNull CreateTodoRequest createTodoRequest);

    Optional<TodoItem> findById(@NotNull String id);

    void deleteById(@NotNull String id);

    TodoItem update(@Nullable TodoItem existingItem, @NotNull UpdateTodoRequest updateTodoRequest) throws ServiceException;
    Page<TodoItem> getItems(@NotNull String userName, @NotNull Pageable pageable);
}
