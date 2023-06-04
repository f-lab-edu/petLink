package com.petlink.common.util.date;

import static com.petlink.common.exception.CommonExceptionCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.petlink.common.exception.CommonException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateConverter {
	public static LocalDateTime toLocalDateTime(String date) {
		return Optional.ofNullable(date)
			.map(d -> switch (d.length()) {
				case 8 -> parseDateWithDay(d);
				case 6 -> parseDateWithoutDay(d);
				default -> throw new CommonException(INVALID_DATE_FORMAT);
			})
			.orElseThrow(() -> new CommonException(NULL_IS_NOT_ALLOWED));
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
