package com.first.controller;

import com.first.decorator.*;
import com.first.enums.Direction;
import com.first.enums.Fields;
import com.first.model.*;
import com.first.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/pagination")
@RestController
public class PaginationController {
    private final StudentService studentService;

    @GetMapping(value ="/search" ,name = "searchStudentsByLetter")
    public ListResponse<Student> searchStudentsByLetter(@RequestParam String letter){
        return new ListResponse<>(studentService.getStudentsByLetter(letter),Response.getOkResponse("Students found successfully"));
    }
    @PostMapping(value ="/page" ,name = "findStudentsByPageNo")
    public PageResponse<Student> findStudentsByPageNo(@RequestBody PaginationDto pagination){
        return new PageResponse<>(studentService.getStudentsByPageNo(pagination),Response.getOkResponse("Students found successfully"));
    }
    @PostMapping(value ="/filter" ,name = "filterStudentsByRole")
    public ListResponse<Student> filterStudentsByRole(@RequestBody FilterDto filter){
        return new ListResponse<>(studentService.filterByRole(filter),Response.getOkResponse("Students found successfully"));
    }
    @GetMapping(value ="/sort" ,name = "sortStudentsByField")
    public ListResponse<Student> sortStudentsByField(@RequestParam Fields field,@RequestParam(required = false) Fields field2, @RequestParam(required = false) Direction direction){
        return new ListResponse<>(studentService.sortByField(field,field2,direction),Response.getOkResponse("Students found successfully"));
    }
    @PostMapping(value = "/all",name = "filterSortAndPagination")
    public PageResponse<Student> filterSortAndPagination(@RequestBody PaginationApi paginationApi){
        return new PageResponse<>(studentService.filterSortAndPagination(paginationApi),Response.getOkResponse("Students found successfully"));
    }

}
