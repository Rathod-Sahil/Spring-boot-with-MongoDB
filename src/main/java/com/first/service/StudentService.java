package com.first.service;

import com.first.model.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(String id);

    List<Student> getAllStudents();

    void deleteStudent(String id);

    Student updateStudent(Student student);
}
