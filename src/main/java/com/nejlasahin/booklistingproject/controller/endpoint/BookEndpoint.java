package com.nejlasahin.booklistingproject.controller.endpoint;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookEndpoint {

    public static final String BASE_PATH = "/api/v1/books";
    public static final String GET_ALL = "/getAll";
    public static final String ADD = "/add";
    public static final String EDIT = "/{bookId}/edit";
}
