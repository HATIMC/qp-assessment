package com.hatim.qp.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class QpUtils {
    public static String encodeString(String input) {
	byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
	return new String(encodedBytes);
    }

    public static String decodeString(String encodedInput) {
	byte[] decodedBytes = Base64.getDecoder().decode(encodedInput.getBytes());
	return new String(decodedBytes);
    }

    public static Date addDaysToDate(Date date, int days) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.DATE, days);
	return calendar.getTime();
    }

    public static Timestamp getCurrentTimestamp() {
	return Timestamp.valueOf(LocalDateTime.now());
    }
}
