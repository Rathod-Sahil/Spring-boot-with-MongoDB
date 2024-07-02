package com.first.controller;

import com.first.annotations.Access;
import com.first.constant.ResponseConstants;
import com.first.decorator.*;
import com.first.enums.Role;
import com.first.helper.Response;
import com.first.model.Student;
import com.first.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {
    private final StudentService studentService;

    @Access(role = Role.ANONYMOUS)
    @PostMapping(value = "/register", name = "createStudent")
    public DataResponse<Student> createStudent(@RequestBody StudentDto studentDTO) {
        return new DataResponse<>(studentService.createStudent(studentDTO), Response.getOkResponse(ResponseConstants.CREATE_STUDENT));
    }

    @Access(role = Role.ADMIN)
    @GetMapping(value = "/{id}", name = "findStudent")
    public DataResponse<Student> getStudent(@PathVariable String id) {
        return new DataResponse<>(studentService.getStudent(id), Response.getOkResponse(ResponseConstants.GET_STUDENT));
    }

    @Access(role = Role.ADMIN)
    @GetMapping(name = "findAllStudents")
    public ListResponse<Student> getAllStudents() {
        return new ListResponse<>(studentService.getAllStudents(), Response.getOkResponse(ResponseConstants.GET_ALL_STUDENTS));
    }

    @Access(role = Role.USER)
    @DeleteMapping(value = "/{id}", name = "deleteStudent")
    public DataResponse<Object> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return new DataResponse<>(null, Response.getOkResponse(ResponseConstants.DELETE_STUDENT));
    }

    @Access(role = Role.USER)
    @PutMapping(value = "/recover", name = "recoverStudent")
    public DataResponse<Student> recoverStudent(@RequestParam String email) {
        return new DataResponse<>(studentService.recoverStudent(email), Response.getOkResponse(ResponseConstants.RECOVER_STUDENT));
    }

    @Access(role = Role.USER)
    @PutMapping(value = "/{id}", name = "updateStudent")
    public DataResponse<Student> updateStudent(@PathVariable String id, @RequestBody StudentDto studentDTO) {
        return new DataResponse<>(studentService.updateStudent(id, studentDTO), Response.getOkResponse(ResponseConstants.UPDATE_STUDENT));
    }

    @Access(role = Role.USER)
    @PutMapping(value = "/reset-password/{id}", name = "resetPassword")
    public DataResponse<Object> resetPassword(@PathVariable String id, @RequestBody PasswordDto passwordDTO) {
        studentService.resetPassword(id, passwordDTO);
        return new DataResponse<>(null, Response.getOkResponse(ResponseConstants.RESET_PASSWORD));
    }

    @Access(role = Role.USER)
    @GetMapping(value = "/forget-password", name = "sendOTP")
    public DataResponse<Object> sendOTP(@RequestParam String email) {
        studentService.sendOTP(email);
        return new DataResponse<>(null, Response.getOkResponse(ResponseConstants.SEND_OTP));
    }

    @Access(role = Role.USER)
    @PutMapping(value = "/forget-password/otp", name = "verifyOTP")
    public DataResponse<Object> verifyOTP(@RequestBody ForgetPasswordDto forgetPasswordDTO) {
        studentService.verifyOTP(forgetPasswordDTO.getEmail(), forgetPasswordDTO.getNewPassword(), forgetPasswordDTO.getOtp());
        return new DataResponse<>(null, Response.getOkResponse(ResponseConstants.VERIFY_OTP));
    }

    @Access(role = Role.ADMIN)
    @GetMapping(value = "/date", name = "setRandomDate")
    public DataResponse<Object> setRandomDate() {
        studentService.setRandomDate(LocalDate.now().minusYears(1), LocalDate.now());
        return new DataResponse<>(null, Response.getOkResponse(ResponseConstants.SET_RANDOM_DATE));
    }
}
