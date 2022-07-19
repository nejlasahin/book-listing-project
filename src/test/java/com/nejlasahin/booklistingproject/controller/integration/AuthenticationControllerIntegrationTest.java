package com.nejlasahin.booklistingproject.controller.integration;

import com.google.gson.Gson;
import com.nejlasahin.booklistingproject.controller.endpoint.AuthenticationEndpoint;
import com.nejlasahin.booklistingproject.dto.request.LoginRequest;
import com.nejlasahin.booklistingproject.dto.request.SignupRequest;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class AuthenticationControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldReturnLoginResponse_WhenLogin_AndRegisteredUser() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(AuthenticationEndpoint.BASE_PATH + AuthenticationEndpoint.LOGIN)
                .contentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        String requestStr = gson.toJson(buildLoginRequest("author", "123456"));
        request.content(requestStr);

        ResultActions response = mockMvc.perform(request);

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldReturnUnauthorized_WhenLogin_AndUnRegisteredUser() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(AuthenticationEndpoint.BASE_PATH + AuthenticationEndpoint.LOGIN)
                .contentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        String requestStr = gson.toJson(buildLoginRequest("login-user", "123456"));
        request.content(requestStr);

        ResultActions response = mockMvc.perform(request);

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void shouldReturnSignupResponse_WhenSignup() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(AuthenticationEndpoint.BASE_PATH + AuthenticationEndpoint.SIGNUP)
                .contentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        String requestStr = gson.toJson(buildSignupRequest("signup-user", "first name", "last name", "mail@gmail.com", "password", RoleNameEnum.ROLE_AUTHOR));
        request.content(requestStr);

        ResultActions response = mockMvc.perform(request);

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
    }

    private LoginRequest buildLoginRequest(String username, String password) {
        return LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
    }

    private SignupRequest buildSignupRequest(String username, String firstName, String lastName, String email, String password, RoleNameEnum roleName) {
        return SignupRequest.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .roleName(roleName.name())
                .build();
    }
}
