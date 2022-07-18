package com.nejlasahin.booklistingproject.service.impl;

import com.nejlasahin.booklistingproject.dto.converter.BookConverter;
import com.nejlasahin.booklistingproject.dto.request.BookRequest;
import com.nejlasahin.booklistingproject.dto.response.BookResponse;
import com.nejlasahin.booklistingproject.exception.enumeration.BookErrorMessage;
import com.nejlasahin.booklistingproject.exception.enumeration.UserErrorMessage;
import com.nejlasahin.booklistingproject.exception.exceptions.BookNotFoundException;
import com.nejlasahin.booklistingproject.exception.exceptions.NotBelongToAuthorException;
import com.nejlasahin.booklistingproject.exception.exceptions.NotHavePermissionException;
import com.nejlasahin.booklistingproject.exception.exceptions.UserNotFoundException;
import com.nejlasahin.booklistingproject.model.Book;
import com.nejlasahin.booklistingproject.model.User;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import com.nejlasahin.booklistingproject.repository.BookRepository;
import com.nejlasahin.booklistingproject.repository.UserRepository;
import com.nejlasahin.booklistingproject.security.JwtUserDetails;
import com.nejlasahin.booklistingproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookConverter bookConverter;

    @Override
    public List<BookResponse> findAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookConverter.convertToBookResponseList(bookList);
    }

    @Override
    public BookResponse edit(String bookId, BookRequest request) {
        JwtUserDetails userPrincipal = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authorId = userPrincipal.getId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    logger.error("{} book is not found!", bookId);
                    throw new BookNotFoundException(BookErrorMessage.BOOK_NOT_FOUND.getMessage());
                });

        if (!Objects.equals(book.getUser().getId(), authorId)) {
            logger.error("Book {} is not the author of {}!", bookId, authorId);
            throw new NotBelongToAuthorException(BookErrorMessage.BOOK_NOT_BELONG_TO_AUTHOR.getMessage());
        }

        Book convertedBook = bookConverter.convertToBook(book, request);
        bookRepository.save(convertedBook);

        logger.info("{} book updated.", convertedBook.getTitle());

        return bookConverter.convertToBookResponse(convertedBook);
    }

    @Override
    public BookResponse add(BookRequest request) {
        JwtUserDetails userPrincipal = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userPrincipal.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("{} user is not found!", userId);
            throw new UserNotFoundException(UserErrorMessage.USER_NOT_FOUND.getMessage());
        });

        boolean isAuthor = userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleNameEnum.ROLE_AUTHOR.name()));
        if (!isAuthor) {
            logger.error("{} do not have permission for this action!", userId);
            throw new NotHavePermissionException(UserErrorMessage.USER_NOT_HAVE_PERMISSION.getMessage());
        }

        Book book = bookConverter.convertToBook(request, user);
        bookRepository.save(book);

        logger.info("{} book added.", request.getTitle());

        return bookConverter.convertToBookResponse(book);
    }
}
