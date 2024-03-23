package com.first.service;

import com.first.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student createStudent(Student student);

    Optional<Student> getStudent(String id);

    List<Student> getAllStudents();

    void deleteStudent(String id);

    Student updateStudent(Student student);
}
