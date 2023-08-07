package com.petlink.orders.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderNumberUtils {

    // 시퀀스 번호를 위한 비트 수. 비트는 컴퓨터에서 데이터를 표현하는 최소 단위.
    // 여기서는 12비트를 사용하여 시퀀스 번호를 표현.
    private static final long SEQUENCE_BIT = 12;

    // 시퀀스 번호의 최대값. 12비트로 표현할 수 있는 최대 숫자.
    // 이 값에 도달하면 시퀀스 번호는 0으로 초기화.
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // 워커 ID를 위한 비트 수. 워커는 서버나 애플리케이션 인스턴스를 의미.
    // 여기서는 5비트를 사용하여 워커 ID를 표현.
    private static final long WORKER_ID_BIT = 5;

    // 데이터 센터 ID를 위한 비트 수. 데이터 센터는 서버가 위치한 물리적인 장소를 의미.
    // 여기서는 5비트를 사용하여 데이터 센터 ID를 표현.
    private static final long DATA_CENTER_ID_BIT = 5;

    // 데이터 센터의 고유 번호.
    @Value("${data.center.id}")
    private long dataCenterId;

    // 워커(서버나 애플리케이션 인스턴스)의 고유 번호.
    @Value("${worker.id}")
    private long workerId;

    // 마지막으로 주문번호를 생성한 시간. 주문번호 생성 간의 시간 차이를 계산하기 위해 사용.
    private long lastTimestamp = -1L;

    // 시퀀스 번호. 주문번호 생성 시 사용되는 일련번호.
    // 주문번호가 연속적으로 생성될 때마다 1씩 증가하며, MAX_SEQUENCE에 도달하면 0으로 초기화됩니다.
    private long sequence = 0L;


    /**
     * 주문 번호를 생성하는 메서드.
     * 주문 번호는 현재 시간, 워커 ID, 데이터 센터 ID, 시퀀스 번호를 조합하여 생성.
     *
     * @return 고유한 주문 번호 문자열
     */
    public synchronized String generateOrderNumber() {
        long currentTimestamp = System.currentTimeMillis();

        // 현재 시간이 마지막으로 주문번호를 생성한 시간과 같다면 시퀀스 번호를 증가시킵니다.
        if (currentTimestamp == lastTimestamp) {
            sequence = getNextSequenceNumber(currentTimestamp);
        } else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        // 현재 시간, 워커 ID, 데이터 센터 ID, 시퀀스 번호를 조합하여 주문 번호를 생성.
        return String.valueOf((currentTimestamp << (SEQUENCE_BIT + WORKER_ID_BIT + DATA_CENTER_ID_BIT))
                | (dataCenterId << (SEQUENCE_BIT + WORKER_ID_BIT))
                | (workerId << SEQUENCE_BIT)
                | sequence);
    }

    /**
     * 시퀀스 번호를 증가시키거나 초기화하는 메서드.
     * 시퀀스 번호가 최대값을 넘으면 0으로 초기화.
     *
     * @param currentTimestamp 현재 시간
     * @return 증가된 또는 초기화된 시퀀스 번호
     */
    private long getNextSequenceNumber(long currentTimestamp) {
        sequence = (sequence + 1) & MAX_SEQUENCE;
        if (sequence == 0L) currentTimestamp = waitForNextMillis(lastTimestamp);
        return sequence;
    }

    /**
     * 다음 밀리초(1/1000초)까지 대기하는 메서드.
     * 주문 번호 생성 시 같은 밀리초 내에서 여러 번호를 생성할 경우 사용.
     *
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
