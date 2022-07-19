package com.nejlasahin.booklistingproject.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotBelongToAuthorException extends RuntimeException {

    public NotBelongToAuthorException(String message) {
        super(message);
    }
}
