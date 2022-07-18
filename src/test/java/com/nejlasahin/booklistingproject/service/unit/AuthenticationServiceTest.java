package com.nejlasahin.booklistingproject.service.unit;

import com.nejlasahin.booklistingproject.dto.request.LoginRequest;
import com.nejlasahin.booklistingproject.dto.request.SignupRequest;
import com.nejlasahin.booklistingproject.dto.response.LoginResponse;
import com.nejlasahin.booklistingproject.dto.response.SignupResponse;
import com.nejlasahin.booklistingproject.model.Role;
import com.nejlasahin.booklistingproject.model.User;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import com.nejlasahin.booklistingproject.repository.RoleRepository;
import com.nejlasahin.booklistingproject.repository.UserRepository;
import com.nejlasahin.booklistingproject.security.JwtTokenProvider;
import com.nejlasahin.booklistingproject.security.JwtUserDetails;
import com.nejlasahin.booklistingproject.service.impl.AuthenticationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    LoginRequest loginRequest;
    LoginResponse loginResponse;
    SignupRequest signupRequest;
    SignupResponse signupResponse;
    User user;
    Role role;

    @Before
    public void setup() {
        loginRequest = buildLoginRequest("username1", "password1");
        loginResponse = buildLoginResponse("token.1", "1", "username1", "username1@gmail.com", RoleNameEnum.ROLE_AUTHOR);
        signupRequest = buildSignupRequest("username1", "firstName1", "lastName1", "username1@gmail.com", "password1", RoleNameEnum.ROLE_AUTHOR);
        signupResponse = buildSignupResponse("User registered successfully!");
        user = buildUser("1", "user", "first name", "last name", "mail@gmail.com", "password", RoleNameEnum.ROLE_AUTHOR);
        role = buildRole("1", RoleNameEnum.ROLE_AUTHOR);
    }

    @Test
    public void shouldReturnLoginResponse_WhenLogin() {

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when((JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(JwtUserDetails.build(user));
        when(jwtTokenProvider.generateJwtToken(any())).thenReturn("generate.token");

        LoginResponse response = authenticationService.login(loginRequest);

        assertEquals(response.getUsername(), user.getUsername());
    }

    @Test
    public void shouldReturnSignupResponse_WhenSignup() {

        when(userRepository.existsByUsername(any())).thenReturn(Boolean.FALSE);
        when(userRepository.existsByEmail(any())).thenReturn(Boolean.FALSE);
        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
        when(userRepository.save(any())).thenReturn(user);

        SignupResponse response = authenticationService.signup(signupRequest);

        assertEquals(response.getMessage(), signupResponse.getMessage());

    }

    private Role buildRole(String username, RoleNameEnum roleName) {
        return Role.builder()
                .id(username)
                .name(roleName)
                .build();
    }

    private LoginRequest buildLoginRequest(String username, String password) {
        return LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
    }

    private LoginResponse buildLoginResponse(String token, String id, String username, String email, RoleNameEnum roleName) {
        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .id(id)
                .username(username)
                .email(email)
                .roles(List.of(roleName.name()))
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

    private SignupResponse buildSignupResponse(String message) {
        return SignupResponse.builder()
                .message(message)
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
