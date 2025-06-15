package com.acledabank.student_management_api.config.jwt;

import com.acledabank.student_management_api.dto.response.ApiResponseEntityDto;
import com.acledabank.student_management_api.exception.EmptyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        //Log the attempted access without leaking sensitive info
        log.warn("Unauthorized request: [{}] {}", request.getMethod(), request.getRequestURI());

        //Custom error response
        ApiResponseEntityDto errorResponse = ApiResponseEntityDto.builder()
                .errorCode("401")
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Unauthorized access")
                .messageDescription("Authentication failed or missing. Please log in again.")
                .timeStamp(LocalDateTime.now())
                .responseData(new EmptyResponse())
                .build();

        //Prepare response
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        //Write JSON response
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        response.getWriter().flush();
    }
}
