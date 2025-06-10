package com.acledabank.student_management_api.service.impl;


import com.acledabank.student_management_api.config.jwt.JwtProvider;
import com.acledabank.student_management_api.model.User;
import com.acledabank.student_management_api.reposity.UserRepository;
import com.acledabank.student_management_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwtToken) {
        String email = jwtProvider.getEmailFromJwtToken(jwtToken);
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }
}
