package com.first.repository;

import com.first.decorator.SortFilterPagination;
import com.first.decorator.PaginationApi2;
import com.first.decorator.StudentDtoExt;
import com.first.enums.Fields;
import com.first.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import java.io.IOException;
import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> searchByLetter(String letter);

    Page<Student> findAllByPageNo(int pageNo, int pageSize);

    List<Student> filterByRole(List<String> roles);

    List<Student> sortByField(Fields field, Fields field2, Direction direction);

    Page<Student> filterSortAndPagination(SortFilterPagination sortFilterPagination);

    Page<StudentDtoExt> findByRegistrationDate(PaginationApi2 paginationApi2) throws IOException ;
}
