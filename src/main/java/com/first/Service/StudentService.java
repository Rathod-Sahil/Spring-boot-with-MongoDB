package com.first.Service;

import com.first.Entity.Student;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student createStudent(Student student);

    Optional<Student> getStudent(String id);

    List<Student> getAllStudents();

    void deleteStudent(String id);

    Student updateStudent(Student student);
}
