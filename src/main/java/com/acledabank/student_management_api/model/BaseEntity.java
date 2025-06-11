package com.acledabank.student_management_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_by")
    @JsonIgnore
    private String createdBy;

    @Column(name = "created_at", updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    @JsonIgnore
    private String updatedBy;

    @Column(name = "updated_at", insertable = false)
    @JsonIgnore
    private LocalDateTime updatedAt;


}
