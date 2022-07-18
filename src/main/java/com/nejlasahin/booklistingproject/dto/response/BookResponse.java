package com.nejlasahin.booklistingproject.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookResponse {

    private String id;
    private String title;
    private Integer totalPages;
    private String isbn;
    private Date publishedDate;
    private String author;
}
