package com.first.decorator;

import lombok.Data;

@Data
public class PasswordDto {
    private String oldPassword;
    private String newPassword;
}
