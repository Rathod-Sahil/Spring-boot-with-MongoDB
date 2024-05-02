package com.first.repository;

import com.first.decorator.PaginationApi;
import com.first.enums.Direction;
import com.first.enums.Fields;
import com.first.enums.Roles;
import com.first.model.Student;
import org.springframework.data.domain.Page;


import java.util.List;

public interface CustomStudentRepository {
    List<Student> searchByLetter(String letter);
    Page<Student> findAllByPageNo(int pageNo, int pageSize);
    List<Student> filterByRole(List<String> roles);
    List<Student> sortByField(Fields field,Fields field2, Direction direction);
    Page<Student> filterSortAndPagination(PaginationApi paginationApi);
    }
