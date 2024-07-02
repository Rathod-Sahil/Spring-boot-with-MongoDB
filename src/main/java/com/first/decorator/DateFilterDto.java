package com.first.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateFilterDto {

    private Integer day;
    private Integer month;
    private Integer year;
    private String quarter;
}
