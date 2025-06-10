package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.model.User;

public interface UserService {
    User findUserByJwtToken(String jwtToken);
    User findUserByEmail(String email) throws Exception;
}
