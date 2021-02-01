package org.step.linked.step.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationRequest {

    @NotBlank
    @Size(min = 5, max = 128)
    @Email
    private String username;
    @NotBlank
    @Size(min = 10, max = 128)
    private String password;
}
