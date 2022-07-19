package com.nejlasahin.booklistingproject.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class BookRequest {

    @NotNull(message = "title cannot be null")
    @Size(min = 3, max = 20, message = "title must be between 3 and 20 characters")
    private String title;

    @NotNull(message = "totalPages cannot be null")
    @Positive(message = "totalPages be positive number.")
    private Integer totalPages;

    @NotNull(message = "isbn cannot be null")
    @Size(min = 10, max = 20, message = "isbn must be between 10 and 20 characters")
    private String isbn;

    @NotNull(message = "publishedDate cannot be null")
    @PastOrPresent(message = "publishedDate must be past.")
    private Date publishedDate;
}
