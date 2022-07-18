package com.nejlasahin.booklistingproject.controller;

import com.nejlasahin.booklistingproject.controller.endpoint.AuthenticationEndpoint;
import com.nejlasahin.booklistingproject.dto.base.RestResponse;
import com.nejlasahin.booklistingproject.dto.request.LoginRequest;
import com.nejlasahin.booklistingproject.dto.request.SignupRequest;
import com.nejlasahin.booklistingproject.dto.response.LoginResponse;
import com.nejlasahin.booklistingproject.dto.response.SignupResponse;
import com.nejlasahin.booklistingproject.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(AuthenticationEndpoint.BASE_PATH)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(tags = "Authentication Controller")
    @PostMapping(AuthenticationEndpoint.LOGIN)
    public ResponseEntity<RestResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.of(response));
    }

    @Operation(tags = "Authentication Controller")
    @PostMapping(AuthenticationEndpoint.SIGNUP)
    public ResponseEntity<RestResponse<SignupResponse>> signup(@RequestBody @Valid SignupRequest request) {
        SignupResponse response = authenticationService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.of(response));
    }
}
