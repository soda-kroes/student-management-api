package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.dto.request.StudentRequest;
import com.acledabank.student_management_api.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentResponse create(StudentRequest studentRequest);

    StudentResponse update(Long id, StudentRequest studentRequest);

    StudentResponse getById(Long id);

    void delete(Long id);

    List<StudentResponse> getAll();
}
