package com.first.controller;

import com.first.annotations.Access;
import com.first.constant.ResponseConstants;
import com.first.decorator.*;
import com.first.enums.Role;
import com.first.helper.Response;
import com.first.model.Student;
import com.first.utils.JwtUtils;
import com.first.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/login")
@RestController
public class LoginController {
    private final LoginService loginService;
    private final JwtUtils jwtUtils;

    @Access(role = Role.ANONYMOUS)
    @PostMapping
    public JwtResponse<Student> login(@RequestBody LoginDto loginDTO) {
        Student student = loginService.login(loginDTO);
        String token = jwtUtils.generateToken(student);
        return new JwtResponse<>(student, token, Response.getOkResponse(ResponseConstants.LOGIN));
    }

    @Access(role = Role.ADMIN)
    @GetMapping("/unBlock/{id}")
    public DataResponse<Object> unBlock(@PathVariable String id) {
        loginService.unBlock(id);
        return new DataResponse<>(null, Response.getOkResponse(ResponseConstants.UNBLOCK));
    }
}
