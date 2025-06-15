package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.config.jwt.CustomUserDetailService;
import com.acledabank.student_management_api.config.jwt.JwtProvider;
import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.LoginRequest;
import com.acledabank.student_management_api.dto.request.UserRequest;
import com.acledabank.student_management_api.dto.response.AuthResponse;
import com.acledabank.student_management_api.exception.BadRequestErrorException;
import com.acledabank.student_management_api.model.User;
import com.acledabank.student_management_api.reposity.UserRepository;
import com.acledabank.student_management_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public AuthResponse registerUser(UserRequest userRequest) {
        // Check if email already exists
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new BadRequestErrorException("Email is already associated with another account.");
        }

        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // Assuming roles is a comma-separated string or similar — adjust if needed
        user.setRoles(userRequest.getRoles());

        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(Constant.SYSTEM);

        userRepository.save(user);

        // Authenticate newly created user
        Authentication authentication = authenticate(userRequest.getEmail(), userRequest.getPassword());

        // Generate both tokens
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(accessToken);
        authResponse.setRefreshToken(refreshToken);

        return authResponse;
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(accessToken);
        authResponse.setRefreshToken(refreshToken);

        return authResponse;
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

    public Authentication loadAuthenticationByUsername(String username) throws Exception {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new Exception("User not found: " + username);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
