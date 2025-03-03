package org.tushar.todolist.dao.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoResponse extends BaseResponse {
    private String id;
    @Builder(builderMethodName = "todoResponseBuilder")
    public TodoResponse(int code, String description, String id) {
        super(code, description);
        this.id = id;
    }
}
