package com.first.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationApi2{
    SortDto2 sort;
    PaginationDto pagination;
    DateFilterDto dateFilterDto;
}
