package com.eatiko.logic.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private final String userName = "Invalid user name";
    private final String password = "Invalid password";
}
