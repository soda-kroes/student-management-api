package com.acledabank.student_management_api.dto.response;

import com.acledabank.student_management_api.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    @JsonProperty("jwt_token")
    private String jwtToken;
    private UserRole role;
}
