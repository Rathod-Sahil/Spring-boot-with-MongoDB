package com.first.repository;

import com.first.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> {
    Student findByEmailAndSoftDeleteFalse(String email);
    Optional<Student> findBySoftDeleteFalseAndId(String id);
    List<Student> findBySoftDeleteFalse();
}
