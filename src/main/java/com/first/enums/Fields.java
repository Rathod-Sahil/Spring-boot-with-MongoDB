package com.first.enums;

import lombok.Getter;

@Getter
public enum Fields {
    FIRST_NAME("FIRST_NAME","firstName"),
    LAST_NAME("LAST_NAME","lastName"),
    EMAIL("EMAIL","email"),
    CITY("CITY","city"),
    PHONE_NO("PHONE_NO","phoneNo");

    private final String name;
    private final String value;

    Fields(String name,String value) {
        this.name = name;
        this.value = value;
    }
}
