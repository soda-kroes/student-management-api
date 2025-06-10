package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.dto.request.CourseRequest;
import com.acledabank.student_management_api.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse create (CourseRequest courseRequest);
    List<CourseResponse> getAll();
}
