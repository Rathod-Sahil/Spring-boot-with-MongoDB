package com.first.Controller;

import com.first.Entity.Student;
import com.first.Repository.StudentRepository;
import com.first.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    private StudentRepository repository;

    public StudentController(StudentService studentService, StudentRepository repository) {
        this.studentService = studentService;
        this.repository = repository;
    }

    @PostMapping
    public Student createStudent(@RequestBody  Student student){
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable String id){
        Optional<Student> student = studentService.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id){
       studentService.deleteStudent(id);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
}
