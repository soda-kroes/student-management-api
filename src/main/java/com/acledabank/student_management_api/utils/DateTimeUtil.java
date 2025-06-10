package com.acledabank.student_management_api.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
public class DateTimeUtil {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "hh:mm a";
    private static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            log.warn("Input dateTime string is null or empty");
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            log.error("Error parsing dateTime string '{}': {}", dateTimeStr, e.getMessage());
        }
        return null;
    }

    public static Date convertStringToDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            log.warn("Input date string is null or empty");
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException ex) {
            log.error("Error parsing date string '{}': {}", dateString, ex.getMessage());
        }
        return null;
    }

    public static LocalTime convertStringTimeToLocalTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            log.warn("Input time string is null or empty");
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        try {
            return LocalTime.parse(timeStr.toUpperCase(), formatter);
        } catch (DateTimeParseException ex) {
            log.error("Error parsing time string '{}': {}", timeStr, ex.getMessage());
        }
        return null;
    }

    public static String convertDateToString(Date date) {
        String pattern = "yyyy-MM-dd";  // default pattern
        if (date == null) {
            log.warn("Input Date is null");
            return null;
        }
        if (pattern == null || pattern.trim().isEmpty()) {
            pattern = "yyyy-MM-dd";  // default pattern
        }
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

}
