package com.first.repository;

import com.first.decorator.PaginationApi;
import com.first.enums.Direction;
import com.first.enums.Fields;
import com.first.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CustomStudentRepositoryImpl implements CustomStudentRepository{

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Student> searchByLetter(String letter){
        Query search = search(letter, new Query(softDelete()));
        return mongoTemplate.find(search,Student.class);
    }

    @Override
    public Page<Student> findAllByPageNo(int pageNo, int pageSize){
        return pagination(pageNo, pageSize,new Query(softDelete()));
    }

    @Override
    public List<Student> filterByRole(List<String> roles){
        Query filter = filter(roles,new Query(softDelete()));
        return mongoTemplate.find(filter,Student.class);
    }

    @Override
    public List<Student> sortByField(Fields field,Fields field2, Direction direction) {
        Query sorted = sort(field, field2, direction,new Query(softDelete()));
        return mongoTemplate.find(sorted,Student.class);
    }

    @Override
    public Page<Student> filterSortAndPagination(PaginationApi paginationApi){
        Query search = search(paginationApi.getFilter().getSearch(),new Query(softDelete()));
        Query filter = filter(paginationApi.getFilter().getRoles(), search);
        Query sorted = sort(paginationApi.getSort().getField(), paginationApi.getSort().getField2(), paginationApi.getSort().getDirection(), filter);
        return pagination(paginationApi.getPagination().getPageNo(), paginationApi.getPagination().getPageSize(), sorted);
    }

    public Criteria softDelete(){
       return Criteria.where("softDelete").is(false);
    }

    public Query search(String letter,Query query){
        if(letter==null){
            return query;
        }
        return  query.addCriteria(new Criteria().orOperator(
                Criteria.where("firstName").regex(".*"+letter+".*","i"),
                Criteria.where("lastName").regex(".*"+letter+".*","i"),
                Criteria.where("email").regex(".*"+letter+".*","i"),
                Criteria.where("city").regex(".*"+letter+".*","i")));
    }

    public Query filter(List<String> roles,Query query){
        if(roles==null){
            return query;
        }
        return query.addCriteria(Criteria.where("role").in(roles));
    }

    public Query sort(Fields field,Fields field2, Direction direction,Query query){
        if(field==null){
            return query;
        }
        query.with(Sort.by(Sort.Direction.valueOf(direction.toString()),field.getName()))
                .collation(Collation.of("en"));

        if(field2!=null){
            query.with(Sort.by(Sort.Direction.valueOf(direction.toString()),field2.getName()))
                    .collation(Collation.of("en"));
        }
        return query;
    }

    public Page<Student> pagination(int pageNo, int pageSize,Query query){
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        long totalElements = mongoTemplate.count(query, Student.class);
        query.with(pageRequest);
        List<Student> students = mongoTemplate.find(query, Student.class);
        return PageableExecutionUtils.getPage(students, pageRequest, () -> totalElements);
    }

}
