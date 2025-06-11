package com.acledabank.student_management_api.service;

import com.acledabank.student_management_api.dto.request.StudentPhotoRequest;
import com.acledabank.student_management_api.dto.response.StudentPhotoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentPhotoService {
    List<StudentPhotoResponse> upload(MultipartFile[] files, StudentPhotoRequest studentPhotoRequest);
    StudentPhotoResponse update(Long id, StudentPhotoRequest studentPhotoRequest);
    void delete(Long id);
    StudentPhotoResponse getById(Long id);
    List<StudentPhotoResponse> getAll();
}
