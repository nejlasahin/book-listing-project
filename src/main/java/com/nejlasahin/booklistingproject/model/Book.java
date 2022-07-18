package com.nejlasahin.booklistingproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;
    private Integer totalPages;
    private String isbn;
    private Date publishedDate;

    @DBRef
    private User user;
}
