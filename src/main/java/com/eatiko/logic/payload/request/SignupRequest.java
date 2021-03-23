package com.eatiko.logic.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignupRequest {
    @Email (message = "Please, enter correct email format")
    @NotBlank (message = "User email field is required")
    private String email;
    @NotEmpty (message = "Please, enter first name")
    private String firstName;
    @NotEmpty (message = "Please, enter last name")
    private String lastName;
    @NotEmpty (message = "Please, enter user name")
    private String userName;
    @NotEmpty (message = "Please, enter password")
    @Size (min = 6, message = "Password must 6 symbols")
    private String password;
    private String confirmPassword;
}
