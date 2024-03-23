package com.first.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("students")
@Setter
@Getter
public class Student {

   @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private long phoneNo;
    @JsonIgnore
    private boolean softDelete;
}
