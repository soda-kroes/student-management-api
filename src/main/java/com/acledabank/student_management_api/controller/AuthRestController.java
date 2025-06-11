package com.acledabank.student_management_api.controller;

import com.acledabank.student_management_api.config.jwt.JwtProvider;
import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.LoginRequest;
import com.acledabank.student_management_api.dto.request.UserRequest;
import com.acledabank.student_management_api.dto.response.AuthResponse;
import com.acledabank.student_management_api.enums.UserRole;
import com.acledabank.student_management_api.util.ApiResponseUtil;
import com.acledabank.student_management_api.exception.BadRequestErrorException;
import com.acledabank.student_management_api.model.User;
import com.acledabank.student_management_api.reposity.UserRepository;
import com.acledabank.student_management_api.service.CustomUserDetailService;
import com.acledabank.student_management_api.util.JsonLogger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private static final Logger log = LoggerFactory.getLogger(AuthRestController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/register")
    public ResponseEntity<?> createUserHandler(@Valid @RequestBody UserRequest userRequest) throws Exception {
        log.info("User registration request received: {}", JsonLogger.toJson(userRequest));

        User existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser != null) {
            throw new BadRequestErrorException("Email is already associated with another account.");
        }

        User newUser = new User();
        newUser.setFullName(userRequest.getFullName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setRole(userRequest.getRole());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setCreatedBy(Constant.SYSTEM);
        User savedUser = userRepository.save(newUser);

        // Authenticate user after registration
        Authentication authentication = new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(jwt);
        authResponse.setRole(savedUser.getRole());

        log.info("User registration successful for email: {}", savedUser.getEmail());
        log.debug("Registration response: {}", JsonLogger.toJson(authResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("User registration successful.", authResponse),
                HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        log.info("User login request received: {}", JsonLogger.toJson(loginRequest));

        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(jwt);
        authResponse.setRole(UserRole.valueOf(role));

        log.info("User login successful for email: {}", loginRequest.getEmail());
        log.debug("Login response: {}", JsonLogger.toJson(authResponse));

        return new ResponseEntity<>(
                ApiResponseUtil.successResponse("User login successful.", authResponse),
                HttpStatus.OK
        );
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadRequestErrorException("Invalid username.");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadRequestErrorException("Invalid password.");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

