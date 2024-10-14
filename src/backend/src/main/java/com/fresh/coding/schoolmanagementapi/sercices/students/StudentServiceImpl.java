package com.fresh.coding.schoolmanagementapi.sercices.students;

import com.fresh.coding.schoolmanagementapi.dto.StudentDTO;
import com.fresh.coding.schoolmanagementapi.entities.Student;
import com.fresh.coding.schoolmanagementapi.exceptions.HttpNotFoundException;
import com.fresh.coding.schoolmanagementapi.repositories.StudentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public @NonNull List<StudentDTO> findAllStudents() {
        log.info("Fetching all students...");
        var students = studentRepository.findAllStudents();
        log.info("Found {} students", students.size());
        return students;
    }

    @Transactional
    @Override
    public StudentDTO save(StudentDTO toSave) {
        log.info("Saving student with ID: {}", toSave.getId() != null ? toSave.getId() : "New student");

        var student = toSave.getId() != null ?
                studentRepository.findById(toSave.getId())
                        .orElseThrow(() -> {
                            log.error("Students not found with ID: {}", toSave.getId());
                            return new HttpNotFoundException("Student not found with id: " + toSave.getId());
                        })
                : new Student();

        BeanUtils.copyProperties(toSave, student);

        student = studentRepository.save(student);
        log.info("Student saved successfully with ID: {}", student.getId());

        return toStudentSummarized(student);
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting student with ID: {}", id);
        if (!studentRepository.existsById(id)) {
            log.error("Student not found with ID: {}", id);
            throw new HttpNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
        log.info("Student deleted successfully with ID: {}", id);
    }

    private StudentDTO toStudentSummarized(@NonNull Student student) {
        log.debug("Converting student entity to DTO for student ID: {}", student.getId());
        var studentSummarized = new StudentDTO();
        BeanUtils.copyProperties(student, studentSummarized, "payments");
        return studentSummarized;
    }
}
