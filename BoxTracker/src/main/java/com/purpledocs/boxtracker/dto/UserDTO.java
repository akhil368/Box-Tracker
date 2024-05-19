package com.purpledocs.boxtracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.purpledocs.boxtracker.type.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private int userId;
    @NotBlank(message = "Username should not be blank")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password should not be blank")
    @Size(min = 6, message = "Password must be of 6 characters")
    private String password;

    private UserStatus userStatus = UserStatus.ACTIVE;
}