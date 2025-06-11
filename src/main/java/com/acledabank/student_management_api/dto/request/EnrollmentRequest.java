package com.acledabank.student_management_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EnrollmentRequest {

    private Long id;

    @NotNull(message = "Student ID is required")
    @Min(value = 1, message = "Student ID must be a positive number")
    @JsonProperty("student_id")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    @Min(value = 1, message = "Course ID must be a positive number")
    @JsonProperty("course_id")
    private Long courseId;

    @Pattern(regexp = "A|B|C|D|F", message = "Grade must be one of A, B, C, D, or F")
    private String grade;

}
