package com.first.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum Fields {
    ALL("ALL", "all", List.of("quarter", "day", "month", "year", "fullName")),
    FIRST_NAME("FIRST_NAME", "firstName"),
    LAST_NAME("LAST_NAME", "lastName"),
    EMAIL("EMAIL", "email"),
    CITY("CITY", "city"),
    PHONE_NO("PHONE_NO", "phoneNo"),
    DAY("DAY", "day"),
    MONTH("MONTH", "month"),
    YEAR("YEAR", "year"),
    QUARTER("QUARTER", "quarter");

    private final String name;
    private final String value;
    private List<String> values;

    Fields(String name, String value) {
        this.name = name;
        this.value = value;
    }

    Fields(String name, String value, List<String> values) {
        this.name = name;
        this.value = value;
        this.values = values;
    }
}
