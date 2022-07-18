package com.nejlasahin.booklistingproject.service;

import com.nejlasahin.booklistingproject.dto.request.BookRequest;
import com.nejlasahin.booklistingproject.dto.response.BookResponse;

import java.util.List;

public interface BookService {

    List<BookResponse> findAll();

    BookResponse edit(String bookId, BookRequest bookRequest);

    BookResponse add(BookRequest bookRequest);
}
