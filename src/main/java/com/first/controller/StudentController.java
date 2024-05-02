package com.first.controller;

import com.first.decorator.*;
import com.first.model.*;
import com.first.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController
{
    private final StudentService studentService;
    @PostMapping(value = "/register",name = "createStudent")
    public DataResponse<Student> createStudent(@RequestBody StudentDto studentDTO){
        return new DataResponse<>(studentService.createStudent(studentDTO),Response.getOkResponse("Student created successfully"));
    }
    @GetMapping(value = "/{id}",name = "findStudent")
    public DataResponse<Student> getStudent(@PathVariable String id){
        return new DataResponse<>(studentService.getStudent(id),Response.getOkResponse("Student found successfully"));
    }
    @GetMapping(name = "findAllStudents")
    public DataResponse<List<Student>> getAllStudents(){
        return new DataResponse<>(studentService.getAllStudents(),Response.getOkResponse("Students found successfully"));
    }
    @DeleteMapping(value = "/{id}",name = "deleteStudent")
    public DataResponse<Object> deleteStudent(@PathVariable String id){
        studentService.deleteStudent(id);
        return new DataResponse<>(null, Response.getOkResponse("Student deleted successfully"));
    }
    @PutMapping(value = "/recover",name = "recoverStudent")
    public DataResponse<Student> recoverStudent(@RequestParam String email){
        return new DataResponse<>(studentService.recoverStudent(email),Response.getOkResponse("Student recovered successfully"));
    }
    @PutMapping(value = "/{id}",name = "updateStudent")
    public DataResponse<Student> updateStudent(@PathVariable String id,@RequestBody StudentDto studentDTO){
        return new DataResponse<>(studentService.updateStudent(id,studentDTO),Response.getOkResponse("Student updated successfully"));
    }
    @PutMapping(value = "/reset-password/{id}",name = "resetPassword")
    public DataResponse<Object> resetPassword(@PathVariable String id, @RequestBody PasswordDto passwordDTO){
        studentService.resetPassword(id,passwordDTO);
        return new DataResponse<>(null,Response.getOkResponse("Password changed successfully"));
    }
    @GetMapping(value = "/forget-password",name = "sendOTP")
    public DataResponse<Object> sendOTP(@RequestParam String email){
        studentService.sendOTP(email);
        return new DataResponse<>(null,Response.getOkResponse("OTP sent to email successfully"));
    }
    @PutMapping(value = "/forget-password/otp",name = "verifyOTP")
    public DataResponse<Object> verifyOTP(@RequestBody ForgetPasswordDto forgetPasswordDTO){
        studentService.verifyOTP(forgetPasswordDTO.getEmail(), forgetPasswordDTO.getNewPassword(), forgetPasswordDTO.getOtp());
        return new DataResponse<>(null,Response.getOkResponse("Password changed successfully"));
    }

    @GetMapping(value = "/date",name = "setRandomDate")
    public DataResponse<Object> setRandomDate(){
        studentService.setRandomDate(LocalDate.now().minusYears(1),LocalDate.now());
        return new DataResponse<>(null,Response.getOkResponse("Date set successfully"));
    }
}
