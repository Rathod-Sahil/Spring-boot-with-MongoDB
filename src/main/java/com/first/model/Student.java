package com.first.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("students")
public class Student {

   @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String phoneNo;
    @JsonIgnore
    private boolean softDelete;
    private List<Roles> role;
    private Status status;
}
