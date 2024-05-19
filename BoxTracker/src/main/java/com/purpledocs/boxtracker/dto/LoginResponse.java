package com.purpledocs.boxtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private int userId;
    private String username;
    private String userJwtToken;

    public LoginResponse(int userId, String username, String userJwtToken) {
        this.userId = userId;
        this.username = username;
        this.userJwtToken = userJwtToken;
    }
}