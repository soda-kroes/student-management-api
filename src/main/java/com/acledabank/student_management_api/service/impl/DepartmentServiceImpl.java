package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.dto.request.DepartmentRequest;
import com.acledabank.student_management_api.dto.response.DepartmentResponse;
import com.acledabank.student_management_api.model.Department;
import com.acledabank.student_management_api.reposity.DepartmentRepository;
import com.acledabank.student_management_api.service.DepartmentService;
import com.acledabank.student_management_api.service.handler.DepartmentHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentHandlerService departmentHandlerService;


    @Override
    public DepartmentResponse create(DepartmentRequest departmentRequest) {
        Department dep = departmentHandlerService.convertDepartmentRequestToDepartment(departmentRequest);
        Department department = departmentRepository.save(dep);
        return departmentHandlerService.convertDepartmentToDepartmentResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAll() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> departmentResponseList = new ArrayList<>();

        for (Department department : departments) {
            DepartmentResponse departmentResponse = departmentHandlerService.convertDepartmentToDepartmentResponse(department);
            departmentResponseList.add(departmentResponse);
        }

        return departmentResponseList;
    }

}
