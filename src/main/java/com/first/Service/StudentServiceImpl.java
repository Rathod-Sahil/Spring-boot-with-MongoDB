package com.first.Service;

import com.first.Entity.Student;
import com.first.Exception.StudentIsAlreadyExisted;
import com.first.Exception.StudentIsNotExisted;
import com.first.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        String email = student.getEmail();
        Optional<Student> byEmail = studentRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            return studentRepository.save(student);
        }else{
            throw new RuntimeException(new StudentIsAlreadyExisted("Student is already existed"));
        }
    }

    public Optional<Student> getStudent(String id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            if(student.get().isSoftDelete()){
                return student;
            }
            throw new StudentIsNotExisted();
        }
        throw  new StudentIsNotExisted();
    }

    public List<Student> getAllStudents() {
        List<Student> all = studentRepository.findAll();
        List<Student> allStudents = new ArrayList<>();
        for(Student st:all){
            if(st.isSoftDelete()){
                allStudents.add(st);
            }
        }
        return allStudents;
    }

    public Student updateStudent(Student student){
        String id = student.getId();
        Optional<Student> student1 = studentRepository.findById(id);
        if(student1.isPresent()){
            String email = student.getEmail();
            Optional<Student> byEmail = studentRepository.findByEmail(email);
            if(byEmail.isPresent()){
                if(student1.equals(byEmail)){
                    return studentRepository.save(student);
                }else {
                    throw new StudentIsAlreadyExisted();
                }
            }
            return studentRepository.save(student);
        }else{
            throw new StudentIsNotExisted("Student is not existed with given id");
        }
    }
    public void deleteStudent(String id) {
        Optional<Student> student1 = studentRepository.findById(id);

        if(student1.isPresent()) {
            String firstName = student1.get().getFirstName();
            String lastName = student1.get().getLastName();
            String email = student1.get().getEmail();
            String password = student1.get().getPassword();
            String city = student1.get().getCity();
            long phoneNo = student1.get().getPhoneNo();

            Student student = new Student(id, firstName, lastName, email, password, city, phoneNo, false);
            studentRepository.save(student);
        }else {
            throw new StudentIsNotExisted("Student is not existed with given id");
        }
    }
}
