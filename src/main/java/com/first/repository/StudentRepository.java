package com.first.repository;

import com.first.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> , StudentRepositoryCustom {
    Optional<Student> findByEmailAndSoftDeleteFalse(String email);
    Optional<Student> findByIdAndSoftDeleteFalse(String id);
    Optional<List<Student>> findBySoftDeleteFalse();
    Optional<Student> findTopByOrderByIdDesc(String email);
}
