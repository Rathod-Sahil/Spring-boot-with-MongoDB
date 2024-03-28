package com.first.controller;

import com.first.model.StudentDTO;
import com.first.model.DataResponse;
import com.first.model.Response;
import com.first.model.Student;
import com.first.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(name = "createStudent")
    public DataResponse<Student> createStudent(@RequestBody StudentDTO student){
        DataResponse<Student> dataResponse = new DataResponse<>();

        //If email or password or phone number is empty return error
        if(student.getEmail()==null || student.getPassword()==null || student.getPhoneNo()==null){
            dataResponse.setResponse(new Response("One or more fields are empty", HttpStatus.BAD_REQUEST,"Invalid"));
            return dataResponse;
        }

        //If email or password or phone number are not empty then check for validation
        boolean isEmailValid = Pattern.matches("[a-zA-Z0-9_\\-.]+@[a-zA-Z_\\-]+[.][a-zA-Z]+[.]?[a-zA-Z]*",student.getEmail());

        if(!isEmailValid){
            dataResponse.setResponse(new Response("Invalid Email", HttpStatus.BAD_REQUEST,"Invalid"));
            return dataResponse;        }

        boolean isPasswordValid = Pattern.matches("^(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z])(?=\\D*\\d)(?=[^!@#$%&]*[!@#$%&])[A-Za-z0-9!@#$%&]{8,15}$",student.getPassword());

        if(!isPasswordValid){
            dataResponse.setResponse(new Response("Invalid Password", HttpStatus.BAD_REQUEST,"Invalid"));
            return dataResponse;        }

        boolean isPhoneNoValid = Pattern.matches("[0-9]{10}",student.getPhoneNo());

        if(!isPhoneNoValid){
            dataResponse.setResponse(new Response("Invalid PhoneNo", HttpStatus.BAD_REQUEST,"Invalid"));
            return dataResponse;        }

        dataResponse.setData(studentService.createStudent(student));
        dataResponse.setResponse(new Response("New Student is created", HttpStatus.OK,"Success"));
        return dataResponse;
    }

    @GetMapping(value = "/{id}",name = "findStudent")
    public DataResponse<Student> getStudent(@PathVariable String id){
        DataResponse<Student> dataResponse = new DataResponse<>();
        dataResponse.setData(studentService.getStudent(id));
        dataResponse.setResponse(new Response("Find a student with given id", HttpStatus.OK,"Success"));
        return dataResponse;
    }

    @GetMapping(name = "findAllStudents")
    public DataResponse<List<Student>> getAllStudents(){
        DataResponse<List<Student>> dataResponse = new DataResponse<>();
        dataResponse.setData(studentService.getAllStudents());
        dataResponse.setResponse(new Response("Find all students", HttpStatus.OK,"Success"));
        return dataResponse;
    }

    @DeleteMapping(value = "/{id}",name = "deleteStudent")
    public DataResponse<Student> deleteStudent(String id){
       studentService.deleteStudent(id);
        DataResponse<Student> dataResponse = new DataResponse<>();
        dataResponse.setResponse(new Response("Delete a student", HttpStatus.OK,"Success"));
        return dataResponse;
    }

    @PutMapping(value = "/{id}",name = "updateStudent")
    public DataResponse<Student> updateStudent(@RequestBody StudentDTO student, @PathVariable String id){

        DataResponse<Student> dataResponse = new DataResponse<>();

        if(student.getEmail()!=null){
            boolean isEmailValid = Pattern.matches("[a-zA-Z0-9_\\-.]+@[a-zA-Z_\\-]+[.][a-zA-Z]+[.]?[a-zA-Z]*",student.getEmail());

            if(!isEmailValid){
                dataResponse.setResponse(new Response("Invalid Email", HttpStatus.BAD_REQUEST,"Invalid"));
                return dataResponse;        }
        }

        if(student.getPassword()!=null){
            boolean isPasswordValid = Pattern.matches("^(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z])(?=\\D*\\d)(?=[^!@#$%&]*[!@#$%&])[A-Za-z0-9!@#$%&]{8,15}$",student.getPassword());

            if(!isPasswordValid){
                dataResponse.setResponse(new Response("Invalid Password", HttpStatus.BAD_REQUEST,"Invalid"));
                return dataResponse;        }
        }

        if(student.getPhoneNo()!=null){
            boolean isPhoneNoValid = Pattern.matches("[0-9]{10}",student.getPhoneNo());

            if(!isPhoneNoValid){
                dataResponse.setResponse(new Response("Invalid PhoneNo", HttpStatus.BAD_REQUEST,"Invalid"));
                return dataResponse;        }
        }

        dataResponse.setData(studentService.updateStudent(student,id));
        dataResponse.setResponse(new Response("Student updated", HttpStatus.OK,"Success"));
        return dataResponse;
    }
}
