package com.nejlasahin.booklistingproject.exception.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorMessage {
    EMAIL_IS_ALREADY("Email is already taken!"),
    USER_NOT_FOUND("User is not found!"),
    USER_NOT_HAVE_PERMISSION("You do not have permission for this action!"),
    USERNAME_IS_ALREADY("Username is already taken!");

    private String message;
}
