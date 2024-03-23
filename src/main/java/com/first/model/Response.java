package com.first.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class Response {

    private String description;
    private HttpStatus httpStatus;
    private int httpStatusCode;
}
