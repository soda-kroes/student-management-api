package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.DepartmentRequest;
import com.acledabank.student_management_api.dto.response.DepartmentResponse;
import com.acledabank.student_management_api.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DepartmentHandlerService {

    public Department convertDepartmentRequestToDepartment(DepartmentRequest departmentRequest,Department department) {
        department.setName(departmentRequest.getName());
        if (department.getId() == null){
            department.setCreatedAt(LocalDateTime.now());
            department.setCreatedBy(Constant.SYSTEM);
        }
        return department;
    }

    public DepartmentResponse convertDepartmentToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }
}
