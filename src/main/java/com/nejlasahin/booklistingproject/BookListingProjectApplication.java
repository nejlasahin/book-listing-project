package com.nejlasahin.booklistingproject;

import com.nejlasahin.booklistingproject.model.Book;
import com.nejlasahin.booklistingproject.model.Role;
import com.nejlasahin.booklistingproject.model.User;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import com.nejlasahin.booklistingproject.repository.BookRepository;
import com.nejlasahin.booklistingproject.repository.RoleRepository;
import com.nejlasahin.booklistingproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Set;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class BookListingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookListingProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, BookRepository bookRepository, PasswordEncoder encoder) {
        return args -> {

            roleRepository.deleteAll();
            userRepository.deleteAll();
            bookRepository.deleteAll();

            Role roleAuthor = Role.builder().id("1").name(RoleNameEnum.ROLE_AUTHOR).build();
            Role roleReader = Role.builder().id("2").name(RoleNameEnum.ROLE_READER).build();
            roleRepository.save(roleAuthor);
            roleRepository.save(roleReader);

            User userAuthor = User.builder().id("1").username("author").password(encoder.encode("123456")).email("author@gmail.com").firstName("Author First Name").lastName("Author Last Name").roles(Set.of(roleAuthor)).build();
            User userReader = User.builder().id("2").username("reader").password(encoder.encode("123456")).email("reader@gmail.com").firstName("Reader First Name").lastName("Reader Last Name").roles(Set.of(roleReader)).build();
            userRepository.save(userAuthor);
            userRepository.save(userReader);

            Book book = Book.builder().id("1").title("Book Title").user(userAuthor).isbn("123456").publishedDate(new Date()).totalPages(123).build();
            bookRepository.save(book);
        };
    }

}
