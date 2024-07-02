package com.first.decorator;

import com.first.helper.Response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse<T> {

    private T data;
    private Response response;

}
