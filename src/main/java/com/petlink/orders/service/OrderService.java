package com.petlink.orders.service;

import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderResponseDto;
import com.petlink.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private String creatOrderNumber() {
        // todo 결제 번호 생성 코드
        return "123456789";
    }

    // todo 주문을 생성하는 기능. ( 비회원 구매 )
    public OrderResponseDto createOrderByGuest(OrderRequest orderRequest) {

        // step 1 : 결제 번호 생성
        String orderNumber = creatOrderNumber();

        // step 2 : 결제 생성

        // step 3 : 리워드 재고 감소

        
        // todo 결제 생성 코드
        return OrderResponseDto.builder().build();
    }
}
