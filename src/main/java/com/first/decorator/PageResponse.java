package com.first.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    Page<T> page;
    Response response;
}
