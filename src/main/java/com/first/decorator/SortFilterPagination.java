package com.first.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortFilterPagination {
    SortDto sort;
    FilterDto filter;
    PaginationDto pagination;
}
