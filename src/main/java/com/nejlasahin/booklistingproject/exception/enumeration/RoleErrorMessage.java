package com.nejlasahin.booklistingproject.exception.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoleErrorMessage {
    ROLE_NOT_FOUND("Role is not found!"),
    ROLE_NOT_VALID("Role is not valid!");

    private String message;

    public String getMessage() {
        return message;
    }
}
