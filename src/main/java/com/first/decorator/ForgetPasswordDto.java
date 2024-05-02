package com.first.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordDto {
    private String email;
    private String newPassword;
    private int otp;
}
