package com.first.service;

import com.first.model.StudentDTO;
import com.first.model.Roles;
import com.first.model.Status;
import com.first.model.Student;
import com.first.exception.StudentIsAlreadyExisted;
import com.first.exception.StudentIsNotExisted;
import com.first.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    // Create a single student
    public Student createStudent(StudentDTO sd) {

        Student byEmail = studentRepository.findByEmailAndSoftDeleteFalse(sd.getEmail());
        if(byEmail == null){
            Student student = modelMapper.map(sd,Student.class);

            if (student.getRole().contains(Roles.ADMIN)) {
                student.setStatus(Status.APPROVE);
            }else {
                student.setStatus(Status.DRAFT);
            }
            return  studentRepository.save(student);
        }
        throw new StudentIsAlreadyExisted();
    }

    // Find a single student by giving specific id
    public Student getStudent(String id) {
        return studentRepository.findBySoftDeleteFalseAndId(id).orElseThrow(StudentIsNotExisted::new);
    }

    // Get the details of all the students
    public List<Student> getAllStudents() {
        return studentRepository.findBySoftDeleteFalse();
    }

    // Delete a student of specific id
    public void deleteStudent(String id) {
        Student student = studentRepository.findBySoftDeleteFalseAndId(id).orElseThrow(StudentIsNotExisted::new);
        student.setSoftDelete(true);
        studentRepository.save(student);
    }

    // Update a student of specific id
    public Student updateStudent(StudentDTO studentDTO, String id){
        // Fetch the student which is existed on database or not
        Student student1 = modelMapper.map(getStudent(id),Student.class);

        //  Now student is existed in database, so we will check if the email id given is existed in database or not
        Student student2 = null;
        if(studentDTO.getEmail()!=null){
            student2 = studentRepository.findByEmailAndSoftDeleteFalse(studentDTO.getEmail());
            student1.setEmail(studentDTO.getEmail());
        }
        // Check if the student is empty or already deleted or the given student is same as our user
        if(student2 == null || student1.equals(student2)){

        if(studentDTO.getFirstName()!=null){
            student1.setFirstName(studentDTO.getFirstName());
        }
        if(studentDTO.getLastName()!=null){
            student1.setLastName(studentDTO.getLastName());
        }
        if(studentDTO.getPassword()!=null){
            student1.setPassword(studentDTO.getPassword());
        }
        if(studentDTO.getCity()!=null){
            student1.setCity(studentDTO.getCity());
        }
        if(studentDTO.getPhoneNo()!=null){
            student1.setPhoneNo(studentDTO.getPhoneNo());
        }

        return studentRepository.save(student1);
        }
        throw new StudentIsAlreadyExisted();
    }
}
