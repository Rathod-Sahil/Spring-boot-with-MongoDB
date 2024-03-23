package com.first.controller;

import com.first.model.Student;
import com.first.repository.StudentRepository;
import com.first.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    private StudentRepository repository;

    public StudentController(StudentService studentService, StudentRepository repository) {
        this.studentService = studentService;
        this.repository = repository;
    }

    @PostMapping(name = "Create new student")
    public Student createStudent(@RequestBody  Student student){
        return studentService.createStudent(student);
    }

    @GetMapping(value = "/{id}",name = "Find a student")
    public ResponseEntity<Student> getStudent(@PathVariable String id){
        Student student = studentService.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping(name = "Find all students")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }
    @DeleteMapping(value = "/{id}",name = "Delete student")
    public void deleteStudent(@PathVariable String id){
       studentService.deleteStudent(id);
    }

    @PutMapping(name = "Update student")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
}
