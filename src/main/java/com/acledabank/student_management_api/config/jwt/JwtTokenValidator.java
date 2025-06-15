package com.acledabank.student_management_api.config.jwt;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.exception.EmptyResponse;
import com.acledabank.student_management_api.util.ApiResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenValidator extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String path = request.getRequestURI();
        log.debug("JwtTokenValidator processing request to: {}", path);

        // Skip validation for public endpoints
        if (path.startsWith("/api/v1/auth/login") ||
                path.startsWith("/api/v1/auth/register") ||
                path.startsWith("/api/v1/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(Constant.JWT_HEADER_KEY);
        log.debug("Authorization header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header");
            // You can choose to reject immediately or just continue unauthenticated:
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or invalid");
            return;
        }

        String token = authHeader.substring(7);
        if (!jwtProvider.validateToken(token)) {
            log.warn("JWT token validation failed");
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired access token");
            return;
        }

        if (!jwtProvider.isAccessToken(token)) {
            log.warn("JWT token is not an access token");
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is not an access token");
            return;
        }

        try {
            Claims claims = jwtProvider.parseClaims(token);
            String username = claims.getSubject();
            String roles = claims.get("roles", String.class);

            if (username == null || roles == null) {
                log.warn("JWT token missing required claims");
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token claims");
                return;
            }

            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("JWT token validated successfully for user: {}", username);

        } catch (Exception e) {
            log.error("Error parsing or validating JWT token", e);
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT token processing failed");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        var apiResponse = ApiResponseUtil.createApiResponseEntity(
                String.valueOf(status),
                status,
                "Request Failed",
                message,
                new EmptyResponse()
        );

        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(jsonResponse);
            writer.flush();
        }
    }
}
