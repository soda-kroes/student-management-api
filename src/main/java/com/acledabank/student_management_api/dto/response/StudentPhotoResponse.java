package com.acledabank.student_management_api.dto.response;

import com.acledabank.student_management_api.model.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentPhotoResponse {


    private Long id;

    @JsonProperty("file_type")
    private String fileType;

    @JsonProperty("file_format")
    private String fileFormat;

    @JsonProperty("file_size")
    private Long fileSize;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("photo_url")
    private String photoUrl;

    @JsonProperty("uploaded_by")
    private String uploadedBy;


}
