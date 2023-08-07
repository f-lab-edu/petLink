package com.petlink.orders.service;


import org.springframework.stereotype.Component;


@Component
public class OrderNumberUtils {

    // 시퀀스 번호를 위한 비트 수
    private static final long SEQUENCE_BIT = 12;
    // 시퀀스 번호의 최대값
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // 워커 ID와 데이터 센터 ID를 위한 비트 수 (예시로 설정)
    private static final long WORKER_ID_BIT = 5;
    private static final long DATA_CENTER_ID_BIT = 5;

    // 워커 ID와 데이터 센터 ID (실제 환경에서는 설정 파일 등에서 가져올 수 있습니다.)
    private long workerId = 1L;
    private long dataCenterId = 1L;

    // 마지막으로 주문번호를 생성한 시간
    private long lastTimestamp = -1L;
    // 시퀀스 번호
    private long sequence = 0L;

    /**
     * 주문 번호를 생성하는 메서드
     * @return 고유한 주문 번호
     */
    public synchronized long generateOrderNumber() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp == lastTimestamp) {
            sequence = getNextSequenceNumber(currentTimestamp);
        } else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        // 타임스탬프, 워커 ID, 데이터 센터 ID, 시퀀스 번호를 조합하여 주문 번호 생성
        return (currentTimestamp << (SEQUENCE_BIT + WORKER_ID_BIT + DATA_CENTER_ID_BIT))
                | (dataCenterId << (SEQUENCE_BIT + WORKER_ID_BIT))
                | (workerId << SEQUENCE_BIT)
                | sequence;
    }

    /**
     * 시퀀스 번호를 증가시키거나 초기화하는 메서드
     * @param currentTimestamp 현재 시간
     * @return 증가된 또는 초기화된 시퀀스 번호
     */
    private long getNextSequenceNumber(long currentTimestamp) {
        sequence = (sequence + 1) & MAX_SEQUENCE;
        if (sequence == 0L) {
            currentTimestamp = waitForNextMillis(lastTimestamp);
        }
        return sequence;
    }

    /**
     * 다음 밀리초까지 대기하는 메서드
     * @param lastTimestamp 마지막으로 주문번호를 생성한 시간
     * @return 다음 밀리초
     */
    private long waitForNextMillis(long lastTimestamp) {
        long currentTimestamp = System.currentTimeMillis();
        while (currentTimestamp <= lastTimestamp) {
            currentTimestamp = System.currentTimeMillis();
        }
        return currentTimestamp;
    }
}