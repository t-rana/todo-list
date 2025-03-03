package org.tushar.todolist.dao.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.tushar.todolist.dao.models.TodoItem;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetTodoItemResponse extends BaseResponse {
    private List<TodoItem> todos;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
}
