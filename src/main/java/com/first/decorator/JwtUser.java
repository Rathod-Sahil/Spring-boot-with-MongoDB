package com.first.decorator;

import com.first.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JwtUser {

    String studentId;
    List<Role> role;
    Date issuedTime;
    Date expirationTime;

}
