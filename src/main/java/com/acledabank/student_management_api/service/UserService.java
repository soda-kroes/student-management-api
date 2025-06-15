package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.dto.request.LoginRequest;
import com.acledabank.student_management_api.dto.request.UserRequest;
import com.acledabank.student_management_api.dto.response.AuthResponse;
import com.acledabank.student_management_api.model.User;

public interface UserService {

    AuthResponse registerUser(UserRequest userRequest);

    AuthResponse loginUser(LoginRequest loginRequest);
}
