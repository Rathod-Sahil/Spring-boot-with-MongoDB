package com.first.service;

import com.first.decorator.*;
import org.springframework.data.domain.Sort.Direction;
import com.first.enums.Fields;
import com.first.model.*;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface StudentService {
    Student createStudent(StudentDto sd);

    Student getStudent(String id);

    List<Student> getAllStudents();

    Student updateStudent(String id, StudentDto studentDTO);

    void resetPassword(String id, PasswordDto passwordDTO);

    void sendOTP(String email);

    void verifyOTP(String email, String password, int otp);

    void deleteStudent(String id);

    Student recoverStudent(String email);

    List<Student> getStudentsByLetter(String letter);

    Page<Student> getStudentsByPageNo(PaginationDto pagination);

    List<Student> filterByRole(FilterDto filter);

    List<Student> sortByField(Fields field, Fields field2, Direction direction);

    Page<Student> filterSortAndPagination(SortFilterPagination sortFilterPagination);

    void setRandomDate(LocalDate startDate, LocalDate endDate);

    Page<StudentDtoExt> findByRegistrationDate(PaginationApi2 paginationApi2) throws IOException;

}
