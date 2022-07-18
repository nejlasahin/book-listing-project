package com.nejlasahin.booklistingproject.controller.integration;

import com.nejlasahin.booklistingproject.controller.endpoint.BookEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ROLE_READER"})
    public void shouldReturnBooks_WhenGetAll_AndRoleNameIsReaderOrAuthor() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BookEndpoint.BASE_PATH + BookEndpoint.GET_ALL)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(request);

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    public void shouldReturnForbidden_WhenGetAll_AndRoleNameIsNotReaderOrAuthor() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BookEndpoint.BASE_PATH + BookEndpoint.GET_ALL)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(request);

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void shouldReturnUnauthorized_WhenGetAll_AndWithoutUser() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BookEndpoint.BASE_PATH + BookEndpoint.GET_ALL)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(request);

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()));
    }

}
