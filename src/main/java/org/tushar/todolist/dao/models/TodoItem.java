package org.tushar.todolist.dao.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tushar.todolist.dao.request.CreateTodoRequest;
import org.tushar.todolist.utils.SecurityUtils;

import java.time.LocalDateTime;

@Document(collection = "todo-items")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoItem {
    @Id
    private String id;
    // index
    private String userName;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public static TodoItem from(@NotNull CreateTodoRequest createTodoRequest) {
        TodoItem todoItem = new TodoItem();
        // extract userName from securityContext
        String userName = SecurityUtils.getUserNameFromContenxt();
        todoItem.setUserName(userName);
        todoItem.setTitle(createTodoRequest.getTitle());
        todoItem.setDescription(createTodoRequest.getDescription());
        todoItem.setDeadline(createTodoRequest.getDeadline());
        todoItem.setCreatedAt(LocalDateTime.now());
        todoItem.setUpdateAt(LocalDateTime.now());
        return todoItem;
    }
}
