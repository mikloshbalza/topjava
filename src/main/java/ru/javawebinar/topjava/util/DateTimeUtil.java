package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static LocalDateTime atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : LocalDateTime.of(LocalDate.MIN, LocalTime.MIN);
    }

    public static LocalDateTime atStartOfDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : LocalDateTime.of(LocalDate.MAX, LocalTime.MAX);
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str) {
        return !StringUtils.hasLength(str) ? null : LocalDate.parse(str);
    }

    public static @Nullable LocalTime parseLocalTime(@Nullable String str) {
        return !StringUtils.hasLength(str) ? null : LocalTime.parse(str);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

