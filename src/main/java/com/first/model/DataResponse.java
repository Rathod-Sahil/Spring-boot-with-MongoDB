package com.first.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse<T> {

    private T data;
    private Response response;

}
