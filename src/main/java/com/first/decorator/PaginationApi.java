package com.first.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationApi {
    SortDto sort;
    PaginationDto pagination;
    FilterDto filter;
}
