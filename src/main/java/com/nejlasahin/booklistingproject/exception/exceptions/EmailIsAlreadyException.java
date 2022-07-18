package com.nejlasahin.booklistingproject.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailIsAlreadyException extends RuntimeException {

    public EmailIsAlreadyException(String message) {
        super(message);
    }
}
