package com.nejlasahin.booklistingproject.controller.unit;

import com.nejlasahin.booklistingproject.controller.BookController;
import com.nejlasahin.booklistingproject.dto.base.RestResponse;
import com.nejlasahin.booklistingproject.dto.request.BookRequest;
import com.nejlasahin.booklistingproject.dto.response.BookResponse;
import com.nejlasahin.booklistingproject.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerUnitTest {

    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    BookResponse bookResponse;
    BookResponse editBookResponse;
    BookRequest bookRequest;
    BookRequest editBookRequest;

    @Before
    public void setup() {
        bookResponse = buildBookResponse("1", "Book Title 1", "Author Name", "isbn123456", 123, new Date());
        editBookResponse = buildBookResponse("2", "Book Title 2", "Author Name", "isbn1234567", 246, new Date());
        bookRequest = buildBookRequest("Book Title 1", "isbn123456", 123, new Date());
        editBookRequest = buildBookRequest("Book Title 2", "isbn1234567", 246, new Date());
    }

    @Test
    public void shouldReturnBooks_WhenFindAll() {

        when(bookService.findAll()).thenReturn(List.of(bookResponse));

        ResponseEntity<RestResponse<List<BookResponse>>> response = bookController.getAll();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getData().get(0).getTitle(), bookResponse.getTitle());
    }

    @Test
    public void shouldReturnBook_WhenAdd() {

        when(bookService.add(any())).thenReturn(bookResponse);

        ResponseEntity<RestResponse<BookResponse>> response = bookController.add(bookRequest);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getData().getTitle(), bookRequest.getTitle());
        assertEquals(response.getBody().getData().getTotalPages(), bookRequest.getTotalPages());
    }

    @Test
    public void shouldReturnBook_WhenEdit() {

        when(bookService.edit(any(), any())).thenReturn(editBookResponse);

        ResponseEntity<RestResponse<BookResponse>> response = bookController.edit("2", editBookRequest);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getData().getTitle(), editBookRequest.getTitle());
        assertEquals(response.getBody().getData().getTotalPages(), editBookRequest.getTotalPages());
    }

    private BookResponse buildBookResponse(String id, String title, String author, String isbn, Integer totalPages, Date publishedDate) {
        return BookResponse.builder()
                .id(id)
                .title(title)
                .author(author)
                .isbn(isbn)
                .totalPages(totalPages)
                .publishedDate(publishedDate)
                .build();
    }

    private BookRequest buildBookRequest(String title, String isbn, Integer totalPages, Date publishedDate) {
        return BookRequest.builder()
                .title(title)
                .isbn(isbn)
                .totalPages(totalPages)
                .publishedDate(publishedDate)
                .build();
    }
}
