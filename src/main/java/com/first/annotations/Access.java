package com.first.annotations;

import com.first.enums.Role;

import java.lang.annotation.*;

/**
 * Access annotation is use for do role base authentication to every api separately
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Access {
    Role[] role();
}
