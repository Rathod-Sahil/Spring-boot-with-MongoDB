package com.first.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JwtUser {

    String studentId;
    Object role;
    Date issuedTime;
    Date expirationTime;

}
