package org.tushar.todolist.service.db;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.tushar.todolist.dao.models.TodoItem;

public interface TodoItemRepository extends MongoRepository<TodoItem, String> {
    Page<TodoItem> findAllByUserName(@NotNull String userName, Pageable pageable);
}
