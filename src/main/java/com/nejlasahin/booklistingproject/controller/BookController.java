package com.nejlasahin.booklistingproject.controller;

import com.nejlasahin.booklistingproject.controller.endpoint.BookEndpoint;
import com.nejlasahin.booklistingproject.dto.base.RestResponse;
import com.nejlasahin.booklistingproject.dto.request.BookRequest;
import com.nejlasahin.booklistingproject.dto.response.BookResponse;
import com.nejlasahin.booklistingproject.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookEndpoint.BASE_PATH)
public class BookController {

    private final BookService bookService;

    @Operation(tags = "Book Controller")
    @GetMapping(BookEndpoint.GET_ALL)
    public ResponseEntity<RestResponse<List<BookResponse>>> getAll() {
        List<BookResponse> responseList = bookService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.of(responseList));
    }

    @Operation(tags = "Book Controller")
    @PostMapping(BookEndpoint.ADD)
    public ResponseEntity<RestResponse<BookResponse>> add(@RequestBody @Valid BookRequest request) {
        BookResponse response = bookService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.of(response));
    }

    @Operation(tags = "Book Controller")
    @PutMapping(BookEndpoint.EDIT)
    public ResponseEntity<RestResponse<BookResponse>> edit(@PathVariable String bookId, @RequestBody @Valid BookRequest request) {
        BookResponse response = bookService.edit(bookId, request);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.of(response));
    }
}
