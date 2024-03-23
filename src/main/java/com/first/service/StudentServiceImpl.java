package com.first.service;

import com.first.model.Student;
import com.first.exception.StudentIsAlreadyExisted;
import com.first.exception.StudentIsNotExisted;
import com.first.repository.StudentRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final MongoTemplate mongoTemplate;

    public StudentServiceImpl(StudentRepository studentRepository,MongoTemplate mongoTemplate) {
        this.studentRepository = studentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Student createStudent(Student student) {
        String email = student.getEmail();
        Optional<Student> byEmail = studentRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            return studentRepository.save(student);
        }else{
            throw new StudentIsAlreadyExisted();
        }
    }

    public Student getStudent(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("softDelete").is(false));
        Student st = mongoTemplate.findOne(query, Student.class);
        if(st!=null){
            return st;
        }
        throw new StudentIsNotExisted();
    }

    public List<Student> getAllStudents() {
        Query query = new Query();
        query.addCriteria(Criteria.where("softDelete").is(false));
        return mongoTemplate.find(query, Student.class);
    }

    public Student updateStudent(Student student){

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(student.getId()).and("softDelete").is(false));
        Student student1 = mongoTemplate.findOne(query, Student.class);

        if(student1 == null) {
            throw new StudentIsNotExisted();
        }

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("email").is(student.getEmail()));

        Student student2 = mongoTemplate.findOne(query1,Student.class);

        if(student2 == null){
            student.setSoftDelete(false);
            return studentRepository.save(student);
        }

        if(student1.equals(student2)){
            student.setSoftDelete(false);
            return studentRepository.save(student);
        }else {
            throw new StudentIsAlreadyExisted();
        }
    }
    public void deleteStudent(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.updateFirst(query, Update.update("softDelete", true),Student.class);
    }
}
