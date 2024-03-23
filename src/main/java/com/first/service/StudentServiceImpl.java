package com.first.service;

import com.first.model.Student;
import com.first.exception.StudentIsAlreadyExisted;
import com.first.exception.StudentIsNotExisted;
import com.first.repository.StudentRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        Optional<Student> byEmail = studentRepository.findByEmail(student.getEmail());
        if(byEmail.isEmpty() || byEmail.get().isSoftDelete()){
            return studentRepository.save(student);
        }else{
            throw new StudentIsAlreadyExisted();
        }
    }

    public Optional<Student> getStudent(String id) {
        Optional<Student> student = studentRepository.findBySoftDeleteFalseAndId(id);
        if(student.isPresent()){
            return student;
        }
        throw new StudentIsNotExisted();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findBySoftDeleteFalse();
    }

    public Student updateStudent(Student student){

        Optional<Student> student1 = studentRepository.findBySoftDeleteFalseAndId(student.getId());

        if(student1.isEmpty()) {
            throw new StudentIsNotExisted();
        }

        Optional<Student> student2 = studentRepository.findByEmail(student.getEmail());

        if(student2.isEmpty() || student2.get().isSoftDelete()){
            return studentRepository.save(student);
        }

        if(student1.equals(student2)){
            return studentRepository.save(student);
        }else {
            throw new StudentIsAlreadyExisted();
        }
    }
    public void deleteStudent(String id) {

        Optional<Student> student = studentRepository.findBySoftDeleteFalseAndId(id);
        if(student.isEmpty()){
            throw new StudentIsNotExisted();
        }
        String firstName = student.get().getFirstName();
        String lastName = student.get().getLastName();
        String email = student.get().getEmail();
        String password = student.get().getPassword();
        String city = student.get().getCity();
        long phoneNo = student.get().getPhoneNo();

        Student st = new Student(id,firstName,lastName,email,password,city,phoneNo,true);
        studentRepository.save(st);
    }
}
