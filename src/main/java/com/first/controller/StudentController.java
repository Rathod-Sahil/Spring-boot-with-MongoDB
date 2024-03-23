package com.first.controller;

import com.first.model.DataResponse;
import com.first.model.Response;
import com.first.model.Student;
import com.first.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(name = "Create new student")
    public DataResponse createStudent(@RequestBody  Student student){
        Student student1 = studentService.createStudent(student);
        Response response = new Response("New Student is created", HttpStatus.CREATED,201);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setData(student1);
        dataResponse.setResponse(response);
        return dataResponse;
    }

    @GetMapping(value = "/{id}",name = "Find a student")
    public DataResponse getStudent(@PathVariable String id){
        Optional<Student> student = studentService.getStudent(id);
        Response response = new Response("Find a student with given id", HttpStatus.OK,200);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setData(student.get());
        dataResponse.setResponse(response);
        return dataResponse;
    }

    @GetMapping(name = "Find all students")
    public List<DataResponse> getAllStudents(){
        List<Student> students = studentService.getAllStudents();
        List<DataResponse> dataResponseList = new ArrayList<>();
        Response response = new Response("Find a student with given id", HttpStatus.OK,200);

        for(Student st:students)
            dataResponseList.add(new DataResponse(st,response));
        return dataResponseList;
    }
    @DeleteMapping(value = "/{id}",name = "Delete student")
    public DataResponse deleteStudent(@PathVariable String id){
       studentService.deleteStudent(id);
       DataResponse dataResponse = new DataResponse();
        Response response = new Response("Student is deleted", HttpStatus.NO_CONTENT,204);
       dataResponse.setData(new Student());
       dataResponse.setResponse(response);
       return dataResponse;
    }

    @PutMapping(name = "Update student")
    public DataResponse updateStudent(@RequestBody Student student){
        Student student1 = studentService.updateStudent(student);
        Response response = new Response("Student updated", HttpStatus.OK,200);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setData(student1);
        dataResponse.setResponse(response);
        return dataResponse;
    }
}
