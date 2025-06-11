package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.dto.request.DepartmentRequest;
import com.acledabank.student_management_api.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse create (DepartmentRequest departmentRequest);
    DepartmentResponse update (Long id, DepartmentRequest departmentRequest);
    List<DepartmentResponse> getAll ();
    DepartmentResponse getById(Long id);
    void delete(Long id);
}
