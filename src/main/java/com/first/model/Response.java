package com.first.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String description;
    private HttpStatus httpStatus;
    private String httpStatusCode;
}
