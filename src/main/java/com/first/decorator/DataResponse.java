package com.first.decorator;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse<T> {

    private T data;
    private Response response;

}
