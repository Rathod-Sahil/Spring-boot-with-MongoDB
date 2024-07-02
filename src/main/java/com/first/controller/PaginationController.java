package com.first.controller;

import com.first.annotations.Access;
import com.first.constant.ResponseConstants;
import com.first.decorator.*;
import com.first.enums.Fields;
import com.first.enums.Role;
import org.springframework.data.domain.Sort.Direction;
import com.first.helper.Response;
import com.first.model.*;
import com.first.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/pagination")
@RestController
public class PaginationController {
    private final StudentService studentService;

    @Access(role = Role.ADMIN)
    @GetMapping(value = "/search", name = "searchStudentsByLetter")
    public ListResponse<Student> searchStudentsByLetter(@RequestParam String letter) {
        return new ListResponse<>(studentService.getStudentsByLetter(letter), Response.getOkResponse(ResponseConstants.FIND_STUDENTS));
    }

    @Access(role = Role.ADMIN)
    @PostMapping(value = "/page", name = "findStudentsByPageNo")
    public PageResponse<Student> findStudentsByPageNo(@RequestBody PaginationDto pagination) {
        return new PageResponse<>(studentService.getStudentsByPageNo(pagination), Response.getOkResponse(ResponseConstants.FIND_STUDENTS));
    }

    @Access(role = Role.ADMIN)
    @PostMapping(value = "/filter", name = "filterStudentsByRole")
    public ListResponse<Student> filterStudentsByRole(@RequestBody FilterDto filter) {
        return new ListResponse<>(studentService.filterByRole(filter), Response.getOkResponse(ResponseConstants.FIND_STUDENTS));
    }

    @Access(role = Role.ADMIN)
    @GetMapping(value = "/sort", name = "sortStudentsByField")
    public ListResponse<Student> sortStudentsByField(@RequestParam Fields field, @RequestParam(required = false) Fields field2, @RequestParam(required = false) Direction direction) {
        return new ListResponse<>(studentService.sortByField(field, field2, direction), Response.getOkResponse(ResponseConstants.FIND_STUDENTS));
    }

    @Access(role = Role.ADMIN)
    @PostMapping(value = "/all", name = "filterSortAndPagination")
    public PageResponse<Student> filterSortAndPagination(@RequestBody SortFilterPagination sortFilterPagination) {
        return new PageResponse<>(studentService.filterSortAndPagination(sortFilterPagination), Response.getOkResponse(ResponseConstants.FIND_STUDENTS));
    }

    @Access(role = Role.ADMIN)
    @PostMapping(value = "/filter/registrationDate", name = "getStudentByRegistrationDate")
    public PageResponse<StudentDtoExt> getStudentByRegistrationDate(@RequestBody PaginationApi2 paginationApi2) throws IOException {
        return new PageResponse<>(studentService.findByRegistrationDate(paginationApi2), Response.getOkResponse(ResponseConstants.GET_STUDENT_BY_REGISTRATION_DATE));
    }
}
