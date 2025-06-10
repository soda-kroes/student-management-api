package com.acledabank.student_management_api.exception;


import com.acledabank.student_management_api.dto.response.ApiResponseEntityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ApiResponseEntityDto> handleInternalServerErrorException(InternalServerErrorException ex) {
        log.error("Internal server error: {}", ex.getMessage(), ex);

        var response = ApiResponseEntityDto.builder()
                .errorCode("500")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected server error occurred.")
                .messageDescription(ex.getLocalizedMessage())
                .timeStamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundErrorException.class)
    public ResponseEntity<ApiResponseEntityDto> handleNotFoundErrorException(NotFoundErrorException ex) {
        log.warn("Resource not found: {}", ex.getMessage());

        var response = ApiResponseEntityDto.builder()
                .errorCode("404")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("The requested resource was not found.")
                .messageDescription(ex.getLocalizedMessage())
                .timeStamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestErrorException.class)
    public ResponseEntity<ApiResponseEntityDto> handleBadRequestErrorException(BadRequestErrorException ex) {
        log.warn("Bad request: {}", ex.getMessage());

        var response = ApiResponseEntityDto.builder()
                .errorCode("400")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("The request is invalid or malformed.")
                .messageDescription(ex.getLocalizedMessage())
                .timeStamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponseEntityDto> handleDuplicateResourceException(DuplicateResourceException ex) {
        log.warn("Conflict error: {}", ex.getMessage());

        var response = ApiResponseEntityDto.builder()
                .errorCode("409")
                .statusCode(HttpStatus.CONFLICT.value())
                .message("Conflict: resource already exists.")
                .messageDescription(ex.getLocalizedMessage())
                .timeStamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseEntityDto> handleGenericException(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);

        var response = ApiResponseEntityDto.builder()
                .errorCode("500")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An internal error occurred. Please contact support.")
                .messageDescription("Unexpected error: " + ex.getLocalizedMessage())
                .timeStamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseEntityDto> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        var response = ApiResponseEntityDto.builder()
                .errorCode("400")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .messageDescription("One or more fields have invalid values.")
                .timeStamp(LocalDateTime.now())
                .responseData(errors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
