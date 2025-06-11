package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.DepartmentRequest;
import com.acledabank.student_management_api.dto.response.DepartmentResponse;
import com.acledabank.student_management_api.exception.DuplicateResourceException;
import com.acledabank.student_management_api.exception.NotFoundErrorException;
import com.acledabank.student_management_api.model.Department;
import com.acledabank.student_management_api.reposity.DepartmentRepository;
import com.acledabank.student_management_api.service.DepartmentService;
import com.acledabank.student_management_api.service.handler.DepartmentHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        Department department = new Department();
        if (departmentRepository.existsByName(departmentRequest.getName())) {
            log.info("Department with name '{}' already exists.", departmentRequest.getName());
            throw new DuplicateResourceException("Department with name '" + departmentRequest.getName() + "' already exists.");
        }

        department = departmentHandlerService.convertDepartmentRequestToDepartment(departmentRequest, department);
        Department departmentSave = departmentRepository.save(department);
        return departmentHandlerService.convertDepartmentToDepartmentResponse(departmentSave);
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Department with ID '{}' not found.", id);
                    return new NotFoundErrorException("Department with ID '" + id + "' not found.");
                });

        if (!department.getName().equalsIgnoreCase(departmentRequest.getName()) &&
                departmentRepository.existsByName(departmentRequest.getName())) {
            log.info("Department name '{}' already exists.", departmentRequest.getName());
            throw new DuplicateResourceException("Department name '" + departmentRequest.getName() + "' already exists.");
        }

        Department updateDepartment = departmentHandlerService.convertDepartmentRequestToDepartment(departmentRequest, department);
        updateDepartment.setName(departmentRequest.getName());
        updateDepartment.setUpdatedAt(LocalDateTime.now());
        updateDepartment.setUpdatedBy(Constant.SYSTEM);
        return departmentHandlerService.convertDepartmentToDepartmentResponse(departmentRepository.save(updateDepartment));
    }

    @Override
    public List<DepartmentResponse> getAll() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> responseList = new ArrayList<>();
        for (Department department : departments) {
            responseList.add(departmentHandlerService.convertDepartmentToDepartmentResponse(department));
        }
        return responseList;
    }

    @Override
    public DepartmentResponse getById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Department with ID '{}' not found.", id);
                    return new NotFoundErrorException("Department with ID '" + id + "' not found.");
                });

        return departmentHandlerService.convertDepartmentToDepartmentResponse(department);
    }

    @Override
    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Department with ID '{}' not found.", id);
                    return new NotFoundErrorException("Department with ID '" + id + "' not found.");
                });

        departmentRepository.delete(department);
        log.info("Department with ID '{}' has been deleted.", id);
    }
}
