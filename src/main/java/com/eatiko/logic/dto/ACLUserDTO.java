package com.eatiko.logic.dto;

import com.eatiko.logic.annotations.PasswordMatches;
import com.eatiko.logic.utils.AppConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class ACLUserDTO {
    @Email(regexp = AppConstants.EMAIL_PATTERN, message = "Please, enter correct email format")
    @NotBlank(message = "User email field is required")
    private String email;
    @NotEmpty(message = "Please, enter first name")
    private String firstName;
    @NotEmpty (message = "Please, enter last name")
    private String lastName;
    @NotEmpty (message = "Please, enter user name")
    private String userName;
    @NotEmpty (message = "Please, enter password")
    @Size(min = 6, message = "Password must 6 symbols")
    private String password;
    @Size (min = 6, message = "Confirm Password must 6 symbols")
    private String confirmPassword;

}
