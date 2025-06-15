package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.config.jwt.JwtProvider;
import com.acledabank.student_management_api.dto.request.LoginRequest;
import com.acledabank.student_management_api.dto.request.RefreshTokenRequest;
import com.acledabank.student_management_api.dto.request.UserRequest;
import com.acledabank.student_management_api.dto.response.AuthResponse;
import com.acledabank.student_management_api.service.impl.UserServiceImpl;
import com.acledabank.student_management_api.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {

    private final UserServiceImpl userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> createUserHandler(@Valid @RequestBody UserRequest userRequest) {
        log.info("User registration request received: {}", userRequest);
        AuthResponse authResponse = userService.registerUser(userRequest);
        log.info("User registration successful for email: {}", userRequest.getEmail());
        return ResponseEntity.ok(ApiResponseUtil.successResponse("User registration successful.", authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("User login request received: {}", loginRequest);
        AuthResponse authResponse = userService.loginUser(loginRequest);
        log.info("User login successful for email: {}", loginRequest.getEmail());
        return ResponseEntity.ok(ApiResponseUtil.successResponse("User login successful.", authResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshTokenHandler(@Valid @RequestBody RefreshTokenRequest request) throws Exception {
        String refreshToken = request.getRefreshToken();
        log.info("Refresh token request received");

        // Validate token and check if it is a refresh token
        if (!jwtProvider.validateToken(refreshToken) || !"REFRESH".equalsIgnoreCase(jwtProvider.getTokenType(refreshToken))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseUtil.createApiResponseEntity(
                            "401",
                            401,
                            "Unauthorized",
                            "Invalid or expired refresh token.",
                            null
                    ));
        }

        String username = jwtProvider.getUsernameFromToken(refreshToken);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseUtil.createApiResponseEntity(
                            "401",
                            401,
                            "Unauthorized",
                            "Invalid refresh token payload.",
                            null
                    ));
        }

        Authentication authentication = userService.loadAuthenticationByUsername(username);
        String newAccessToken = jwtProvider.generateAccessToken(authentication);
        String newRefreshToken = jwtProvider.generateRefreshToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(newAccessToken);
        authResponse.setRefreshToken(newRefreshToken);

        return ResponseEntity.ok(ApiResponseUtil.successResponse("Token refreshed successfully.", authResponse));
    }
}
