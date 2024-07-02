package com.first.decorator;

import com.first.helper.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse<T> {
    private T data;
    private String token;
    private Response response;
}
