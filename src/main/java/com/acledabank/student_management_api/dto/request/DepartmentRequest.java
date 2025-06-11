package com.acledabank.student_management_api.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentRequest {

    @NotBlank(message = "name is required")
    private String name;

    @Column(name = "created_by")
    private String createdBy;

}
