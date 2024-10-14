package com.fresh.coding.schoolmanagementapi.sercices.students;

import com.fresh.coding.schoolmanagementapi.dto.StudentDTO;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    @NonNull
    List<StudentDTO> findAllStudents();

    StudentDTO save(StudentDTO toSave);

    void delete(UUID id);
}
