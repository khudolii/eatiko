package com.eatiko.logic.payload.request;

import com.eatiko.logic.annotations.PasswordMatches;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull(message = "User name cannot be empty")
    private String username;
    @NotNull(message = "Password cannot be empty")
    private String password;
}
