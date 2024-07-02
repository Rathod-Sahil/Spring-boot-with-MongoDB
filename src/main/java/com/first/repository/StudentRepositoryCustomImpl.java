package com.first.repository;

import com.first.decorator.PaginationApi2;
import com.first.decorator.SortFilterPagination;
import com.first.decorator.StudentDtoExt;
import com.first.enums.Fields;
import com.first.helper.AdminConfigAggregationTemplateDataHelper;
import com.first.model.AdminConfig;
import com.first.model.Student;
import com.first.utils.AdminConfigUtils;
import com.first.utils.CustomAggregationOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {

    private final MongoTemplate mongoTemplate;
    private final AdminConfigUtils adminConfigUtils;
    private final CustomAggregationOperation customAggregationOperation;

    @Override
    public List<Student> searchByLetter(String letter) {
        Query search = search(letter, new Query(softDelete()));
        return mongoTemplate.find(search, Student.class);
    }

    @Override
    public Page<Student> findAllByPageNo(int pageNo, int pageSize) {
        return pagination(pageNo, pageSize, new Query(softDelete()));
    }

    @Override
    public List<Student> filterByRole(List<String> roles) {
        Query filter = filter(roles, new Query(softDelete()));
        return mongoTemplate.find(filter, Student.class);
    }

    @Override
    public List<Student> sortByField(Fields field, Fields field2, Direction direction) {
        Query sorted = sort(field, field2, direction, new Query(softDelete()));
        return mongoTemplate.find(sorted, Student.class);
    }

    @Override
    public Page<Student> filterSortAndPagination(SortFilterPagination sortFilterPagination) {
        Query search = search(sortFilterPagination.getFilter().getSearch(), new Query(softDelete()));
        Query filter = filter(sortFilterPagination.getFilter().getRoles(), search);
        Query sorted = sort(sortFilterPagination.getSort().getField(), sortFilterPagination.getSort().getField2(),
                sortFilterPagination.getSort().getDirection(), filter);
        return pagination(sortFilterPagination.getPagination().getPageNo(), sortFilterPagination.getPagination().getPageSize(),
                sorted);
    }

    public Criteria softDelete() {
        return Criteria.where("softDelete").is(false);
    }

    public Query search(String letter, Query query) {
        if (letter == null) {
            return query;
        }
        return query.addCriteria(new Criteria().orOperator(
                Criteria.where("firstName").regex(".*" + letter + ".*", "i"),
                Criteria.where("lastName").regex(".*" + letter + ".*", "i"),
                Criteria.where("email").regex(".*" + letter + ".*", "i"),
                Criteria.where("city").regex(".*" + letter + ".*", "i")));
    }

    public Query filter(List<String> roles, Query query) {
        if (roles == null) {
            return query;
        }
        return query.addCriteria(Criteria.where("role").in(roles));
    }

    public Query sort(Fields field, Fields field2, Direction direction, Query query) {
        if (field == null) {
            return query;
        }
        query.with(Sort.by(direction, field.getName()))
                .collation(Collation.of("en"));

        if (field2 != null) {
            query.with(Sort.by(direction, field2.getName()))
                    .collation(Collation.of("en"));
        }
        return query;
    }

    public Page<Student> pagination(int pageNo, int pageSize, Query query) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        long totalElements = mongoTemplate.count(query, Student.class);
        query.with(pageRequest);
        List<Student> students = mongoTemplate.find(query, Student.class);
        return new PageImpl<>(students, pageRequest, totalElements);
//        return PageableExecutionUtils.getPage(students, pageRequest, () -> totalElements);
    }

    @Override
    public Page<StudentDtoExt> findByRegistrationDate(PaginationApi2 paginationApi2) {
        AdminConfig adminConfig = adminConfigUtils.getAdminConfig();
        String filterRegistrationDate = AdminConfigAggregationTemplateDataHelper.getFilterRegistrationDate(adminConfig);

        List<AggregationOperation> aggregationOperations = CustomAggregationOperation.getAggregationOperationList(filterRegistrationDate,
                Pair.of("addField", Object.class),
                Pair.of("set", Object.class),
                Pair.of("match", paginationApi2.getDateFilterDto()));

        return customAggregationOperation.getPageOfElements(StudentDtoExt.class, "students", aggregationOperations, paginationApi2);
    }
}
