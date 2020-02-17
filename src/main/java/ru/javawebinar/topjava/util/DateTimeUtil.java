package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isTimeBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        if (startTime == null) {
            startTime = LocalTime.MIN;
        }
        if (endTime == null) {
            endTime = LocalTime.MAX;
        }
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isDateBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.of(1900, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }

    public static LocalDateTime stringToStartDate(String dateFrom, String timeFrom) {
        if ("".equals(dateFrom) || dateFrom == null) {
            dateFrom = "1900-01-01";
        }
        if ("".equals(timeFrom) || timeFrom == null) {
            timeFrom = LocalTime.MIN.withSecond(0).withNano(0).toString();
        }
        String dateTimeFrom = dateFrom + " " + timeFrom;

        return LocalDateTime.parse(dateTimeFrom, DATE_TIME_FORMATTER);
    }

    public static LocalDateTime stringToEndDate(String dateTo, String timeTo) {
        if ("".equals(dateTo) || dateTo == null) {
            dateTo = LocalDate.now().toString();
        }
        if ("".equals(timeTo) || timeTo == null) {
            timeTo = LocalTime.MAX.withSecond(0).withNano(0).toString();
        }
        String dateTimeTo = dateTo + " " + timeTo;

        return LocalDateTime.parse(dateTimeTo, DATE_TIME_FORMATTER);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

