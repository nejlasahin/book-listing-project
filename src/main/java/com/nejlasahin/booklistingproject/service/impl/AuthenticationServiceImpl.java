package com.nejlasahin.booklistingproject.service.impl;

import com.nejlasahin.booklistingproject.dto.request.LoginRequest;
import com.nejlasahin.booklistingproject.dto.request.SignupRequest;
import com.nejlasahin.booklistingproject.dto.response.LoginResponse;
import com.nejlasahin.booklistingproject.dto.response.SignupResponse;
import com.nejlasahin.booklistingproject.exception.enumeration.RoleErrorMessage;
import com.nejlasahin.booklistingproject.exception.enumeration.UserErrorMessage;
import com.nejlasahin.booklistingproject.exception.exceptions.EmailIsAlreadyException;
import com.nejlasahin.booklistingproject.exception.exceptions.RoleNotFoundException;
import com.nejlasahin.booklistingproject.exception.exceptions.RoleNotValidException;
import com.nejlasahin.booklistingproject.exception.exceptions.UsernameIsAlreadyException;
import com.nejlasahin.booklistingproject.model.Role;
import com.nejlasahin.booklistingproject.model.User;
import com.nejlasahin.booklistingproject.model.enumeration.RoleNameEnum;
import com.nejlasahin.booklistingproject.repository.RoleRepository;
import com.nejlasahin.booklistingproject.repository.UserRepository;
import com.nejlasahin.booklistingproject.security.JwtTokenProvider;
import com.nejlasahin.booklistingproject.security.JwtUserDetails;
import com.nejlasahin.booklistingproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateJwtToken(authentication);

        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("{} user successfully login.", request.getUsername());

        return LoginResponse.builder()
                .token(token)
                .type(TOKEN_TYPE)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public SignupResponse signup(SignupRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            logger.error("{} username is already taken!", request.getUsername());
            throw new UsernameIsAlreadyException(UserErrorMessage.USERNAME_IS_ALREADY.getMessage());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("{} email is already taken!", request.getEmail());
            throw new EmailIsAlreadyException(UserErrorMessage.EMAIL_IS_ALREADY.getMessage());
        }

        if (!RoleNameEnum.isValid(request.getRoleName())) {
            logger.error("{} role not valid!", request.getRoleName());
            throw new RoleNotValidException(RoleErrorMessage.ROLE_NOT_VALID.getMessage());
        }

        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleNameEnum.valueOf(request.getRoleName()))
                .orElseThrow(() -> {
                    logger.error("{} role not found!", request.getRoleName());
                    throw new RoleNotFoundException(RoleErrorMessage.ROLE_NOT_FOUND.getMessage());
                });
        roles.add(role);

        user.setRoles(roles);
        userRepository.save(user);

        logger.info("{} user registered successfully.", request.getUsername());

        return SignupResponse.builder()
                .message("User registered successfully!")
                .build();
    }
}
