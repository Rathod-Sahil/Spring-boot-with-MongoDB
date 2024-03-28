package com.first.model;

import com.first.model.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    String firstName;
    String lastName;
    String email;
    String password;
    String city;
    String phoneNo;
    List<Roles> role;
}
