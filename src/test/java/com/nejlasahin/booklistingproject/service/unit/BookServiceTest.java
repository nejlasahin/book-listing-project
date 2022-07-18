package com.nejlasahin.booklistingproject.service.unit;

import com.nejlasahin.booklistingproject.dto.converter.BookConverter;
import com.nejlasahin.booklistingproject.dto.request.BookRequest;
import com.nejlasahin.booklistingproject.dto.response.BookResponse;
import com.nejlasahin.booklistingproject.model.Book;
import com.nejlasahin.booklistingproject.model.Role;
import com.nejlasahin.booklistingproject.model.User;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import com.nejlasahin.booklistingproject.repository.BookRepository;
import com.nejlasahin.booklistingproject.repository.UserRepository;
import com.nejlasahin.booklistingproject.security.JwtUserDetails;
import com.nejlasahin.booklistingproject.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BookConverter bookConverter;

    BookResponse bookResponse;
    Book book;
    BookResponse editBookResponse;
    BookRequest bookRequest;
    BookRequest editBookRequest;
    User user;

    @Before
    public void setup() {
        bookResponse = buildBookResponse("1", "Book Title 1", "Author Name", "isbn123456", 123, new Date());
        editBookResponse = buildBookResponse("2", "Book Title 2", "Author Name", "isbn1234567", 246, new Date());
        bookRequest = buildBookRequest("Book Title 1", "isbn123456", 123, new Date());
        editBookRequest = buildBookRequest("Book Title 2", "isbn1234567", 246, new Date());
        user = buildUser("1", "user", "first name", "last name", "mail@gmail.com", "password", RoleNameEnum.ROLE_AUTHOR);
        book = buildBook("1", "Book Title 1", "Author Name", "isbn123456", 123, new Date(), user);
    }

    @Test
    public void shouldReturnBooks_WhenFindAll() {

        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookConverter.convertToBookResponseList(any())).thenReturn(List.of(bookResponse));

        List<BookResponse> response = bookService.findAll();

        assertEquals(response.size(), 1);
        assertEquals(response.get(0).getTitle(), bookResponse.getTitle());
    }

    @Test
    public void shouldReturnBook_WhenAdd() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when((JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(JwtUserDetails.build(user));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(bookConverter.convertToBook(any(BookRequest.class), any())).thenReturn(book);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookConverter.convertToBookResponse(any())).thenReturn(bookResponse);

        BookResponse response = bookService.add(bookRequest);

        assertEquals(response.getTitle(), bookRequest.getTitle());
    }

    @Test
    public void shouldReturnBook_WhenEdit() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when((JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(JwtUserDetails.build(user));
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(bookConverter.convertToBook(any(Book.class), any())).thenReturn(book);
        when(bookRepository.save(any())).thenReturn(book);
        when(bookConverter.convertToBookResponse(any())).thenReturn(bookResponse);

        BookResponse response = bookService.edit("1", bookRequest);

        assertEquals(response.getTitle(), bookRequest.getTitle());
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

    private Book buildBook(String id, String title, String author, String isbn, Integer totalPages, Date publishedDate, User user) {
        return Book.builder()
                .id(id)
                .title(title)
                .user(user)
                .isbn(isbn)
                .totalPages(totalPages)
                .publishedDate(publishedDate)
                .build();
    }

    private User buildUser(String id, String username, String firstName, String lastName, String email, String password, RoleNameEnum roleName) {
        return User.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .roles(Set.of(new Role("1", roleName)))
                .build();
    }
}
