package org.tushar.todolist.dao.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreationResponse extends BaseResponse {
    private String userId;
    private String userName;

    @Builder(builderMethodName = "userCreationResponseBuilder")
    public UserCreationResponse(int code, String description, String userId, String userName) {
        super(code, description);
        this.userId = userId;
        this.userName = userName;
    }
}
