package com.petlink.common.util.date;

import com.petlink.common.exception.CommonException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.petlink.common.exception.ExceptionCode.INVALID_DATE_FORMAT;
import static com.petlink.common.exception.ExceptionCode.NULL_IS_NOT_ALLOWED;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateConverter {
    public static LocalDateTime toLocalDateTime(String date) {
        if (date == null) throw new CommonException(NULL_IS_NOT_ALLOWED);
        return switch (date.length()) {
            case 8 -> parseDateWithDay(date);
            case 6 -> parseDateWithoutDay(date);
            default -> throw new CommonException(INVALID_DATE_FORMAT);
        };
    }

    private static LocalDateTime parseDateWithDay(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"))
                .atTime(LocalTime.MIN);
    }

    private static LocalDateTime parseDateWithoutDay(String date) {
        return LocalDate.parse(date + "01", DateTimeFormatter.ofPattern("yyyyMMdd"))
                .atTime(LocalTime.MIN);
    }
}
