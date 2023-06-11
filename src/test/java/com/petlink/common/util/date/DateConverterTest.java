package com.petlink.common.util.date;

import com.petlink.common.exception.CommonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DateConverterTest {

    static final Logger log = LoggerFactory.getLogger(DateConverterTest.class);


    @Test
    @DisplayName("YYYYMM 형태의 데이터는 LocalDateTime으로 변환한다.")
    void convertYmToDateTime() {
        // given
        String date = "202101";

        // when
        LocalDateTime localDateTime = DateConverter.toLocalDateTime(date);
        // then
        assertThat(localDateTime).isNotNull();
        assertThat(localDateTime.getYear()).isEqualTo(2021);
        log.info("localDateTime: {}", localDateTime);
    }

    @Test
    @DisplayName("YYYYMMDD 형태의 데이터는 LocalDateTime으로 변환한다.")
    void convertYmdToDateTime() {
        // given
        String date = "20210101";

        // when
        LocalDateTime localDateTime = DateConverter.toLocalDateTime(date);
        // then
        assertThat(localDateTime).isNotNull();
        assertThat(localDateTime.getYear()).isEqualTo(2021);
        log.info("localDateTime: {}", localDateTime);
    }

    @Test
    @DisplayName("null은 예외를 발생시킨다.")
    void convertNullToDateTime() {
        // given
        String date = null;

        // when
        Throwable throwable = catchThrowable(() -> DateConverter.toLocalDateTime(date));

        // then
        assertThat(throwable).isInstanceOf(CommonException.class);
        log.info("throwable: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("날짜 형식에 맞지 않는 경우 예외가 발생한다")
    void convertInvalidDateToDateTime() {
        // given
        String date = "2021010101";

        // when
        Throwable throwable = catchThrowable(() -> DateConverter.toLocalDateTime(date));

        // then
        assertThat(throwable).isInstanceOf(CommonException.class);
        assertThat(throwable.getMessage()).isEqualTo("날짜 형식이 올바르지 않습니다.");
        log.info("throwable: {}", throwable.getMessage());
    }
}
