package com.acledabank.student_management_api.service.handler;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.StudentPhotoRequest;
import com.acledabank.student_management_api.dto.request.StudentRequest;
import com.acledabank.student_management_api.dto.response.StudentPhotoResponse;
import com.acledabank.student_management_api.model.Student;
import com.acledabank.student_management_api.model.StudentPhoto;
import com.acledabank.student_management_api.reposity.StudentPhotoRepository;
import com.acledabank.student_management_api.util.StringClassUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentPhotoHandlerService {
    private final StudentPhotoRepository studentPhotoRepository;
    private final List<String> FILE_EXTENSION = Arrays.asList("jpg", "jpeg", "png");

    public void updateFileByStudentAndFileId(Student student, Set<Long> photoIds) {
        if (photoIds == null || photoIds.isEmpty()) {
            return; // Avoid unnecessary processing
        }

        // Fetch student photos based on extracted IDs
        List<StudentPhoto> studentPhotos = studentPhotoRepository.findAllByIdIn(photoIds);

        // Update and save student photos
        for (StudentPhoto studentPhoto : studentPhotos) {
            studentPhoto.setStudent(student);
            studentPhotoRepository.saveAndFlush(studentPhoto);
        }
    }

    public void validateFileUpload(MultipartFile[] files) {
        //upload file to server
        if (files.length == 0) {
            log.warn("File is empty.");
            throw new IllegalArgumentException("File is empty.");
        }
    }

    public void validateValidFileUpload(MultipartFile[] files) {
        for (MultipartFile file : files) {
            var filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            var extension = StringClassUtil.getFileExtension(filename);
            if (!FILE_EXTENSION.contains(extension)) {
                log.info("File extension now allow to upload. please verify again.");
                throw new IllegalArgumentException("File extension now allow to upload. please verify again.");
            }
        }

    }


    public StudentPhoto convertStudentPhotoRequestToStudentPhoto(StudentPhotoRequest studentPhotoRequest, StudentPhoto studentPhoto) {
        studentPhoto.setFileName(studentPhotoRequest.getFileName());
        studentPhoto.setFileType(studentPhotoRequest.getFileType());
        studentPhoto.setFileFormat(studentPhotoRequest.getFileFormat());
        studentPhoto.setFileSize(studentPhotoRequest.getFileSize());
        studentPhoto.setPhotoUrl(studentPhotoRequest.getPhotoUrl());
        studentPhoto.setUploadedBy(studentPhotoRequest.getUploadedBy());

        if (studentPhoto.getId() == null) {
            studentPhoto.setCreatedBy(Constant.SYSTEM);
            studentPhoto.setCreatedAt(LocalDateTime.now());
        }
        return studentPhoto;

    }

    public StudentPhotoResponse convertStudentPhotoToStudentPhotoResponse(StudentPhoto studentPhoto) {
        return StudentPhotoResponse.builder()
                .id(studentPhoto.getId())
                .fileType(studentPhoto.getFileType())
                .fileFormat(studentPhoto.getFileFormat())
                .fileSize(studentPhoto.getFileSize())
                .fileName(studentPhoto.getFileName())
                .photoUrl(studentPhoto.getPhotoUrl())
                .uploadedBy(studentPhoto.getUploadedBy())
                .build();
    }

}
