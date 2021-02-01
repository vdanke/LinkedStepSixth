package org.step.linked.step.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.step.linked.step.model.User;

@Data
@AllArgsConstructor
public class UserDTO {

    private String id;
    private String username;

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
}
