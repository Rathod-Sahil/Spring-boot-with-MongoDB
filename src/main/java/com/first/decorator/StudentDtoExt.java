package com.first.decorator;

import com.first.enums.Role;
import com.first.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDtoExt {

    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String city;
    private String phoneNo;
    private List<Role> role;
    private UserStatus userStatus;
    private Date registrationDate;
    int day;
    int month;
    int year;
    String quarter;
}
