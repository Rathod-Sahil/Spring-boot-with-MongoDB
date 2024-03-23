package com.first.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class DataResponse {

    private Student data;
    private Response response;

}
