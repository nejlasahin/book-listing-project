package com.nejlasahin.booklistingproject.exception;

import com.nejlasahin.booklistingproject.dto.base.RestResponse;
import com.nejlasahin.booklistingproject.exception.exceptions.BookNotFoundException;
import com.nejlasahin.booklistingproject.exception.exceptions.EmailIsAlreadyException;
import com.nejlasahin.booklistingproject.exception.exceptions.NotBelongToAuthorException;
import com.nejlasahin.booklistingproject.exception.exceptions.NotHavePermissionException;
import com.nejlasahin.booklistingproject.exception.exceptions.RoleNotFoundException;
import com.nejlasahin.booklistingproject.exception.exceptions.RoleNotValidException;
import com.nejlasahin.booklistingproject.exception.exceptions.UserNotFoundException;
import com.nejlasahin.booklistingproject.exception.exceptions.UsernameIsAlreadyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exceptions) {
        Map<String, String> validationErrors = new HashMap<>();
        exceptions.getBindingResult().getFieldErrors()
                .forEach(fieldError -> validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.error(validationErrors));
    }

    @ExceptionHandler(EmailIsAlreadyException.class)
    public ResponseEntity<Object> handleIsAlreadyException(EmailIsAlreadyException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(UsernameIsAlreadyException.class)
    public ResponseEntity<Object> handleIsAlreadyException(UsernameIsAlreadyException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(RoleNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(RoleNotValidException.class)
    public ResponseEntity<Object> handleNotValidException(RoleNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(BookNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(NotBelongToAuthorException.class)
    public ResponseEntity<Object> handleNotBelongToAuthorException(NotBelongToAuthorException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(NotHavePermissionException.class)
    public ResponseEntity<Object> handleNotHavePermissionException(NotHavePermissionException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(UsernameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RestResponse.error(exception.getMessage()));
    }
}
