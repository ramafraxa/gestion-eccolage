package com.fresh.coding.schoolmanagementapi.repositories;

import com.fresh.coding.schoolmanagementapi.dto.StudentDTO;
import com.fresh.coding.schoolmanagementapi.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("""
                SELECT NEW com.fresh.coding.schoolmanagementapi.dto.StudentDTO(
                    s.id,
                    s.name,
                    s.firstName,
                    s.className,
                    s.address,
                    s.gender
                )
                FROM Student s
            """
    )
    List<StudentDTO> findAllStudents();
}
