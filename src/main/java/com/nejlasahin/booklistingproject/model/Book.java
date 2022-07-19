package com.nejlasahin.booklistingproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    @Field("id")
    private String id;

    @Field("title")
    private String title;

    @Field("total_pages")
    private Integer totalPages;

    @Field("isbn")
    private String isbn;

    @Field("published_date")
    private Date publishedDate;

    @DBRef
    private User user;
}
