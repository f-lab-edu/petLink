package com.petlink.orders.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderNumberUtilsTest {
    private final OrderNumberUtils orderNumberUtils = new OrderNumberUtils();
    private final int THREAD_COUNT = 100_000;

    @Test
    @DisplayName("동시에 100개의 요청이 올 수 있다.")
    void testGenerateOrderNumberConcurrently() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);// 100개의 스레드를 가진 스레드 풀 생성
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT); // 100개의 스레드가 모두 작업을 완료할 때까지 대기하기 위한 CountDownLatch
        Set<Long> orderNumbers = ConcurrentHashMap.newKeySet(); // 주문번호를 저장할 Set

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> { // 100개의 스레드가 병렬로 주문번호를 생성
                try {
                    long orderNumber = orderNumberUtils.generateOrderNumber();
                    orderNumbers.add(orderNumber);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        assertEquals(THREAD_COUNT, orderNumbers.size());
        executorService.shutdown();
    }
}