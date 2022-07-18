package com.nejlasahin.booklistingproject.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class SignupRequest {

    @NotNull(message = "username cannot be null")
    @Size(min = 3, max = 20, message = "username must be between 3 and 20 characters")
    private String username;

    @NotNull(message = "firstName cannot be null")
    @Size(min = 3, max = 20, message = "firstName must be between 3 and 20 characters")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    @Size(min = 3, max = 20, message = "lastName must be between 3 and 20 characters")
    private String lastName;

    @NotNull(message = "email cannot be null")
    @Size(min = 10, max = 50, message = "email must be between 10 and 50 characters")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min = 6, max = 40, message = "password must be between 1 and 200 characters")
    private String password;

    @NotNull(message = "roleName cannot be null")
    @Size(min = 6, max = 40, message = "roleName must be between 6 and 40 characters")
    private String roleName;
}
