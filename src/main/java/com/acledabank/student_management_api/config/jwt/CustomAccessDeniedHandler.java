package com.acledabank.student_management_api.config.jwt;

import com.acledabank.student_management_api.dto.response.ApiResponseEntityDto;
import com.acledabank.student_management_api.exception.EmptyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.warn("Access denied for request: {} - {}", request.getMethod(), request.getRequestURI());
        log.warn("Access denied reason: {}", accessDeniedException.getMessage());

        // Create standardized error response
        ApiResponseEntityDto errorResponse = ApiResponseEntityDto.builder()
                .errorCode("403")
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message("Access Denied")
                .messageDescription("You don't have permission to access this resource.")
                .timeStamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        // Set response properties
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Write JSON response
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}