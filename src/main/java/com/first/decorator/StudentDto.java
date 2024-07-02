package com.first.decorator;

import com.first.enums.Role;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    String firstName;
    String lastName;
    String email;
    String password;
    String city;
    String phoneNo;
    List<Role> role;
}
