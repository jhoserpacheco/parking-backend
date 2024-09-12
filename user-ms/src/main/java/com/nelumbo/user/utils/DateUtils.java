package com.nelumbo.user.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public LocalDateTime stringToLocalDateTime(String date) {
        return date != null ? LocalDateTime.parse(date, FORMATTER) : null;
    }

    public String localDateTimeToString(LocalDateTime date) {
        return date != null ? date.format(FORMATTER) : null;
    }
}
