package com.nejlasahin.booklistingproject.service.integration;

import com.nejlasahin.booklistingproject.dto.request.BookRequest;
import com.nejlasahin.booklistingproject.dto.response.BookResponse;
import com.nejlasahin.booklistingproject.model.Role;
import com.nejlasahin.booklistingproject.model.User;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import com.nejlasahin.booklistingproject.security.JwtUserDetails;
import com.nejlasahin.booklistingproject.service.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceIntegrationTest {

    @Autowired
    BookServiceImpl bookService;

    @Test
    public void shouldReturnBooks_WhenGetAll() {

        List<BookResponse> bookResponseList = bookService.findAll();

        assertTrue(bookResponseList.size() > 0);
    }

    @Test
    public void shouldReturnBookResponse_WhenEdit() {

        User user = buildUser("1", "user", "first name", "last name", "mail@gmail.com", "password", RoleNameEnum.ROLE_AUTHOR);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when((JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(JwtUserDetails.build(user));

        BookRequest request = buildBookRequest("Edit Title", 120, "isbn123456", new Date());

        BookResponse bookResponse = bookService.edit("1", request);

        assertEquals(bookResponse.getTitle(), request.getTitle());
    }

    @Test
    public void shouldReturnBookResponse_WhenAdd() {

        User user = buildUser("1", "user", "first name", "last name", "mail@gmail.com", "password", RoleNameEnum.ROLE_AUTHOR);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when((JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(JwtUserDetails.build(user));

        BookRequest request = buildBookRequest("Title", 120, "isbn123456", new Date());

        BookResponse bookResponse = bookService.add(request);

        assertEquals(bookResponse.getTitle(), request.getTitle());
    }

    private BookRequest buildBookRequest(String title, Integer totalPages, String isbn, Date publishedDate) {
        return BookRequest.builder()
                .title(title)
                .totalPages(totalPages)
                .isbn(isbn)
                .publishedDate(publishedDate).build();
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
