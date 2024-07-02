package com.first.utils;

import com.first.decorator.CountDto;
import com.first.decorator.PaginationApi2;
import com.first.decorator.PaginationDto;
import com.first.enums.Fields;
import com.first.helper.MustacheHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAggregationOperation{

    private final MongoTemplate mongoTemplate;

    @SafeVarargs
    public static List<AggregationOperation> getAggregationOperationList(String jsonFile, Pair<String, Object>... pairs) {
        List<AggregationOperation> pipelineStages = new ArrayList<>();
        for (Pair<String, Object> pair : pairs) {
            String jsonData = MustacheHelper.setTemplateContent(jsonFile, pair.getRight());
            log.info("jsonData : {}", jsonData);
            Document document = Document.parse(jsonData);
            pipelineStages.add(Aggregation.stage((Bson) document.get(pair.getLeft())));
        }
        return pipelineStages;
    }

    public <T> List<T> getListOfElements(Class<T> className, String collectionName, List<AggregationOperation> aggregationOperations,PaginationDto paginationDto) {
        pagination(aggregationOperations,paginationDto);
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        return mongoTemplate.aggregate(aggregation, collectionName, className).getMappedResults();
    }

    public <T> Page<T> getPageOfElements(Class<T> className, String collectionName, List<AggregationOperation> aggregationOperations, PaginationApi2 paginationApi2) {
        sorting(paginationApi2,aggregationOperations);
        List<AggregationOperation> aggregationOperations2 = new ArrayList<>(aggregationOperations);
        aggregationOperations2.add(Aggregation.count().as("count"));
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations2);

        List<CountDto> studentsCount = mongoTemplate.aggregate(aggregation, collectionName, CountDto.class).getMappedResults();
        int count = CollectionUtils.isEmpty(studentsCount) ? 0 : studentsCount.get(0).getCount();
        PageRequest pageRequest = PageRequest.of(paginationApi2.getPagination().getPageNo(), paginationApi2.getPagination().getPageSize());

        List<T> listOfElements = getListOfElements(className, collectionName, aggregationOperations,paginationApi2.getPagination());
        return PageableExecutionUtils.getPage(listOfElements, pageRequest, () -> count);
    }

    public void sorting(PaginationApi2 paginationApi2, List<AggregationOperation> aggregationOperations){
        Fields sortBy = paginationApi2.getSort().getSortBy();
        Sort.Direction orderBy = paginationApi2.getSort().getOrderBy();

        if (sortBy != null) {
            if (orderBy == null) {
                orderBy = Sort.Direction.ASC;
            }

            if (sortBy.getValues()==null) {
                aggregationOperations.add(Aggregation.sort(orderBy, sortBy.getValue()));
            } else {
                List<String> fieldsList = paginationApi2.getSort().getSortBy().getValues();
                aggregationOperations.add(Aggregation.sort(orderBy, String.join(",", fieldsList).split(",")));
            }
        }
    }

    public void pagination(List<AggregationOperation> aggregationOperations,PaginationDto paginationDto){
        aggregationOperations.add(Aggregation.skip((long) paginationDto.getPageNo() * paginationDto.getPageSize()));
        aggregationOperations.add(Aggregation.limit(paginationDto.getPageSize()));
    }
}
