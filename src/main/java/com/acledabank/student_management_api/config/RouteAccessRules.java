package com.acledabank.student_management_api.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class RouteAccessRules {

    public static void configureAuthorizedRoutes(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        // GET access for USER and ADMIN
        auth.requestMatchers(HttpMethod.GET, "/api/v1/student/**").hasAnyRole("USER", "ADMIN");
        auth.requestMatchers(HttpMethod.GET, "/api/v1/department/**").hasAnyRole("USER", "ADMIN");
        auth.requestMatchers(HttpMethod.GET, "/api/v1/student-photo/**").hasAnyRole("USER", "ADMIN");
        auth.requestMatchers(HttpMethod.GET, "/api/v1/course/**").hasAnyRole("USER", "ADMIN"); // <-- added course GET access

        // POST/PUT/DELETE access for ADMIN only
        auth.requestMatchers(HttpMethod.POST, "/api/v1/student/**").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.PUT, "/api/v1/student/**").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.DELETE, "/api/v1/student/**").hasRole("ADMIN");

        auth.requestMatchers(HttpMethod.POST, "/api/v1/department/**").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.PUT, "/api/v1/department/**").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.DELETE, "/api/v1/department/**").hasRole("ADMIN");

        auth.requestMatchers(HttpMethod.POST, "/api/v1/student-photo/**").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.PUT, "/api/v1/student-photo/**").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.DELETE, "/api/v1/student-photo/**").hasRole("ADMIN");

        auth.requestMatchers(HttpMethod.POST, "/api/v1/course/**").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.PUT, "/api/v1/course/**").hasRole("ADMIN");
        auth.requestMatchers(HttpMethod.DELETE, "/api/v1/course/**").hasRole("ADMIN");

        auth.requestMatchers(HttpMethod.POST, "/api/v1/enrollment/**").hasRole("ADMIN");
    }
}
