package com.fresh.coding.schoolmanagementapi.controllers;

import com.fresh.coding.schoolmanagementapi.dto.StudentDTO;
import com.fresh.coding.schoolmanagementapi.sercices.students.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDTO> getAllStudents(){
        return studentService.findAllStudents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO saveStudent(@RequestBody StudentDTO toSave){
        return studentService.save(toSave);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        studentService.delete(id);
    }
}
