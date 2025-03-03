package org.tushar.todolist.dao.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.tushar.todolist.dao.models.User;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FindAllUserResponse extends BaseResponse {
    private List<User> users;

    @Builder(builderMethodName = "findAllUserResponseBuilder")
    public FindAllUserResponse(int code, String description, List<User> users) {
        super(code, description);
        this.users = users;
    }
}
