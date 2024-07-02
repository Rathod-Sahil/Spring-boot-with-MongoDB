package com.first.service;

import com.first.decorator.LoginDto;
import com.first.model.Student;


public interface LoginService {

    Student login(LoginDto loginDTO);
    void unBlock(String id);
}
