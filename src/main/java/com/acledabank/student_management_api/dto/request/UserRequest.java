package com.acledabank.student_management_api.dto.request;

import com.acledabank.student_management_api.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {


    @NotBlank(message = "Full name is required")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "At least one user role is required")
    private Set<UserRole> roles;
}
