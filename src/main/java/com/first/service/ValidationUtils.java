package com.first.service;

import com.first.exception.StudentValidationException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationUtils {

    //Password validation
    public void passwordValidation(String password) {
        if (!Pattern.matches("^.{8,15}$", password)) {
            throw new StudentValidationException("Invalid Password, Length must be between 8 characters and 15 characters");
        }else if (!Pattern.matches(".*[a-z].*", password)) {
            throw new StudentValidationException("Invalid Password, Must contain at least 1 lowercase letter");
        }else if (!Pattern.matches(".*[A-Z].*", password)) {
            throw new StudentValidationException("Invalid Password, Must contain at least 1 uppercase letter");
        }else if (!Pattern.matches(".*[0-9].*", password)) {
            throw new StudentValidationException("Invalid Password, Must contain at least 1 digit");
        }else if (!Pattern.matches(".*\\W.*", password)) {
            throw new StudentValidationException("Invalid Password, Must contain at least 1 special character");
        }
    }

    //Email validation
    public void emailValidate(String email) {
        if (!Pattern.matches("[a-z0-9_\\-.]+@[a-z_\\-]+[.][a-z]{2,3}[.]?[a-z]{0,3}", email)) {
            throw new StudentValidationException("Provide Valid Email Address");
        }
    }

    //PhoneNo validation
    public void phoneNoValidate(String phoneNO) {
        if (!Pattern.matches("[0-9]{10}", phoneNO)) {
            throw new StudentValidationException("Provide 10 digit Phone Number");
        }
    }

    //Validation for null fields
    public void nullFieldValidation(String email,String password,String phoneNo){
        if (email == null || password == null || phoneNo == null) {
            throw new StudentValidationException("One or more fields are empty");
        }
    }
}
