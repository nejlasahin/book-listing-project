package com.nejlasahin.booklistingproject.dto.converter;

import com.nejlasahin.booklistingproject.dto.request.BookRequest;
import com.nejlasahin.booklistingproject.dto.response.BookResponse;
import com.nejlasahin.booklistingproject.model.Book;
import com.nejlasahin.booklistingproject.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter {

    public List<BookResponse> convertToBookResponseList(List<Book> bookList) {
        return bookList.stream().map(this::convertToBookResponse).collect(Collectors.toList());
    }

    public BookResponse convertToBookResponse(Book book) {
        if (book == null) {
            return null;
        }

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getUser().getFullName())
                .isbn(book.getIsbn())
                .totalPages(book.getTotalPages())
                .publishedDate(book.getPublishedDate())
                .build();
    }

    public Book convertToBook(Book book, BookRequest request) {
        if (book == null || request == null) {
            return book;
        }

        return Book.builder()
                .id(book.getId())
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .totalPages(request.getTotalPages())
                .publishedDate(request.getPublishedDate())
                .user(book.getUser())
                .build();
    }

    public Book convertToBook(BookRequest request, User user) {
        if (request == null) {
            return null;
        }

        return Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .totalPages(request.getTotalPages())
                .publishedDate(request.getPublishedDate())
                .user(user)
                .build();
    }
}
