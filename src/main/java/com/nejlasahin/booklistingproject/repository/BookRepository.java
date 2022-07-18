package com.nejlasahin.booklistingproject.repository;

import com.nejlasahin.booklistingproject.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
