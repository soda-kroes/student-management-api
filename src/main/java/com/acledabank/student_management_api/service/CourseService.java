package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.dto.request.CourseRequest;
import com.acledabank.student_management_api.dto.request.DepartmentRequest;
import com.acledabank.student_management_api.dto.response.CourseResponse;
import com.acledabank.student_management_api.dto.response.DepartmentResponse;

import java.util.List;

public interface CourseService {
    CourseResponse create (CourseRequest courseRequest);
    List<CourseResponse> getAll();
    CourseResponse update (Long id, CourseRequest courseRequest);
    CourseResponse getById(Long id);
    void delete(Long id);
}
