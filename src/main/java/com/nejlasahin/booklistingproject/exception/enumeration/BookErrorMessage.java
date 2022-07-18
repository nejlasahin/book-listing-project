package com.nejlasahin.booklistingproject.exception.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BookErrorMessage {
    BOOK_NOT_FOUND("Book is not found!"),
    BOOK_NOT_BELONG_TO_AUTHOR("Book not belong to the author!");

    private String message;

    public String getMessage() {
        return message;
    }
}
