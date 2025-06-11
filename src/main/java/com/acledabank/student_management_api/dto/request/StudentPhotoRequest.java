package com.acledabank.student_management_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudentPhotoRequest {


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
