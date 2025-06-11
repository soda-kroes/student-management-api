package com.acledabank.student_management_api.service.impl;

import com.acledabank.student_management_api.constan.Constant;
import com.acledabank.student_management_api.dto.request.StudentPhotoRequest;
import com.acledabank.student_management_api.dto.response.StudentPhotoResponse;
import com.acledabank.student_management_api.exception.NotFoundErrorException;
import com.acledabank.student_management_api.model.StudentPhoto;
import com.acledabank.student_management_api.reposity.StudentPhotoRepository;
import com.acledabank.student_management_api.service.StudentPhotoService;
import com.acledabank.student_management_api.service.handler.StudentPhotoHandlerService;
import com.acledabank.student_management_api.util.StringClassUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class StudentPhotoServiceImpl implements StudentPhotoService {

    private final StudentPhotoRepository studentPhotoRepository;
    private final StudentPhotoHandlerService studentPhotoHandlerService;

    @Override
    public List<StudentPhotoResponse> upload(MultipartFile[] files, StudentPhotoRequest studentPhotoRequest) {
        studentPhotoHandlerService.validateFileUpload(files);
        studentPhotoHandlerService.validateValidFileUpload(files);

        List<StudentPhotoResponse> studentPhotoResponses = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String baseName = FilenameUtils.removeExtension(originalFilename);
                String extensionName = StringClassUtil.getFileExtension(originalFilename);

                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                String fileName = baseName + "_" + timestamp + "." + extensionName;

                // Ensure correct formatting for Windows path
                String filePath = Constant.FILE_UPLOAD_PATH + fileName;
                File filePathTemp = new File(filePath);
                file.transferTo(filePathTemp);

                // Store metadata
                studentPhotoRequest.setFileFormat(extensionName);
                studentPhotoRequest.setFileName(fileName);
                studentPhotoRequest.setFileSize(file.getSize());
                studentPhotoRequest.setPhotoUrl(filePath.replace("\\", "/")); // Normalize path for storage

                // Convert and save entity
                StudentPhoto studentPhoto = studentPhotoHandlerService.convertStudentPhotoRequestToStudentPhoto(studentPhotoRequest, new StudentPhoto());

                studentPhoto.setUploadedBy(Constant.SYSTEM);
                studentPhotoRepository.save(studentPhoto);

                // Convert to response
                studentPhotoResponses.add(studentPhotoHandlerService.convertStudentPhotoToStudentPhotoResponse(studentPhoto));
            }
        } catch (Exception ex) {
            System.err.println("Error uploading files: " + ex.getMessage());
        }

        return studentPhotoResponses;
    }


    @Override
    public StudentPhotoResponse update(Long id, StudentPhotoRequest studentPhotoRequest) {
        Optional<StudentPhoto> studentPhotoOptional = studentPhotoRepository.findById(id);
        if (studentPhotoOptional.isEmpty()) {
            log.info("Student photo not found.");
            throw new NotFoundErrorException("Student photo not found.");
        }
        StudentPhoto updateStudentPhoto = studentPhotoHandlerService.convertStudentPhotoRequestToStudentPhoto(studentPhotoRequest, studentPhotoOptional.get());
        updateStudentPhoto.setUpdatedAt(LocalDateTime.now());
        updateStudentPhoto.setUpdatedBy(Constant.SYSTEM);

        log.info("Updating student photo.");
        return studentPhotoHandlerService.convertStudentPhotoToStudentPhotoResponse(studentPhotoRepository.save(updateStudentPhoto));
    }

    @Override
    public void delete(Long id) {
        Optional<StudentPhoto> studentPhoto = studentPhotoRepository.findById(id);
        if (studentPhoto.isEmpty()) {
            log.info("Student Photo with id {} not found in DB.", id);
            throw new NotFoundErrorException("Student Photo With Id " + id + " not found.");
        }
        studentPhotoRepository.deleteById(id);
    }

    @Override
    public StudentPhotoResponse getById(Long id) {
        Optional<StudentPhoto> studentPhotoOptional = studentPhotoRepository.findById(id);
        if (studentPhotoOptional.isEmpty()) {
            log.info("Student Photo with id {} not found in DB.", id);
            throw new NotFoundErrorException("Student Photo With Id " + id + "not found.");
        }
        return studentPhotoHandlerService.convertStudentPhotoToStudentPhotoResponse(studentPhotoOptional.get());
    }

    @Override
    public List<StudentPhotoResponse> getAll() {
        List<StudentPhoto> studentPhotos = studentPhotoRepository.findAll();
        if (studentPhotos.isEmpty()) {
            return List.of();
        }
        List<StudentPhotoResponse> studentPhotoResponseList = new ArrayList<>();
        for (StudentPhoto studentPhoto : studentPhotos) {

            studentPhotoResponseList.add(studentPhotoHandlerService.convertStudentPhotoToStudentPhotoResponse(studentPhoto));
        }
        return studentPhotoResponseList;
    }
}
