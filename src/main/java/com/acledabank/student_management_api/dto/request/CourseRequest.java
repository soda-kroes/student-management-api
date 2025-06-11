package com.acledabank.student_management_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequest {

    @JsonProperty("code")
    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Title is required")
    private String title;

}
