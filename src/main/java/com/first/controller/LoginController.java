package com.first.controller;

import com.first.decorator.*;
import com.first.model.Student;
import com.first.model.AdminConfig;
import com.first.service.JwtUtils;
import com.first.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/login")
@RestController
public class LoginController {
    private final LoginService loginService;
    private final JwtUtils jwtUtils;

    @PostMapping
    public JwtResponse<Student> login(@RequestBody LoginDto loginDTO){
        Student student = loginService.login(loginDTO);
        String token = jwtUtils.generateToken(student);
        return new JwtResponse<>(student,token,Response.getOkResponse("Student logged in successfully"));
    }

    @GetMapping("/unBlock/{id}")
    public DataResponse<Object> unBlock(@PathVariable String id){
        loginService.unBlock(id);
        return new DataResponse<>(null,Response.getOkResponse("User unblocked successfully"));
    }
}
