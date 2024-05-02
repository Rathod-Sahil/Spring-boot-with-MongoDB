package com.first.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.first.enums.Roles;
import com.first.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Document("students")
public class Student{

   @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Set<String> oldPasswordSet;
    private String city;
    private String phoneNo;
    @JsonIgnore
    private boolean softDelete;
    private List<Roles> role;
    private Status status;
    @JsonIgnore
    private int otp;
    @JsonIgnore
    private int failedLoginAttempts;
    @JsonIgnore
    private LocalDate registrationDate;
}
