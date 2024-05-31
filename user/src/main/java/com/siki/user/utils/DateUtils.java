package com.siki.user.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static LocalDate convertToLocalDateTime(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate target = LocalDate.parse(value, formatter);
        return target;
    }
}
