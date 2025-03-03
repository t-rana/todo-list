package org.tushar.todolist.service.db.impl;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tushar.todolist.dao.models.TodoItem;
import org.tushar.todolist.dao.request.CreateTodoRequest;
import org.tushar.todolist.dao.request.UpdateTodoRequest;
import org.tushar.todolist.exceptions.ServiceException;
import org.tushar.todolist.service.db.TodoItemDao;
import org.tushar.todolist.service.db.TodoItemRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TodoItemDaoImpl implements TodoItemDao {
    private final TodoItemRepository todoItemRepository;

    public TodoItemDaoImpl(@NotNull TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @Override
    public TodoItem createTodoItem(@NotNull CreateTodoRequest createTodoRequest) {
        TodoItem todoItem = TodoItem.from(createTodoRequest);
        return todoItemRepository.save(todoItem);
    }

    @Override
    public Optional<TodoItem> findById(@NotNull String id) {
        return todoItemRepository.findById(id);
    }

    @Override
    public void deleteById(@NotNull String id) {
        todoItemRepository.deleteById(id);
    }

    @Override
    public TodoItem update(@Nullable TodoItem existingItem, @NotNull UpdateTodoRequest updateTodoRequest) throws ServiceException {
        if (existingItem == null) {
            Optional<TodoItem> todoItemOptional = this.findById(updateTodoRequest.getId());
            if (todoItemOptional.isEmpty()) {
                throw new ServiceException(String.format("no todo item found with id %s", updateTodoRequest.getId()));
            }
            existingItem = todoItemOptional.get();
        }
        updateExistingItem(existingItem, updateTodoRequest);
        return todoItemRepository.save(existingItem);
    }

    @Override
    public Page<TodoItem> getItems(@NotNull String userName, @NotNull Pageable pageable) {
        return todoItemRepository.findAllByUserName(userName, pageable);
    }

    private void updateExistingItem(@NotNull TodoItem existingItem, @NotNull UpdateTodoRequest updateTodoRequest) {
        if (StringUtils.isNotBlank(updateTodoRequest.getTitle())) {
            existingItem.setTitle(updateTodoRequest.getTitle());
        }
        if (StringUtils.isNotBlank(updateTodoRequest.getDescription())) {
            existingItem.setDescription(updateTodoRequest.getDescription());
        }

        if (updateTodoRequest.getDeadline() != null) {
            existingItem.setDeadline(updateTodoRequest.getDeadline());
        }
        existingItem.setUpdateAt(LocalDateTime.now());
    }
}
