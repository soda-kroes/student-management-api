package com.acledabank.student_management_api.reposity;

import com.acledabank.student_management_api.model.StudentPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentPhotoRepository extends JpaRepository<StudentPhoto,Long> {
    List<StudentPhoto> findAllByIdIn(Set<Long> ids);
}
