package com.petlink.orders.service;

import com.petlink.funding.item.service.ItemFacadeService;
import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderResponseDto;
import com.petlink.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemFacadeService itemFacadeService;

    private String creatOrderNumber() {
        // todo 결제 번호 생성 코드
        return "123456789";
    }

    // todo 주문을 생성하는 기능. ( 비회원 구매 )
    public OrderResponseDto createOrderByGuest(OrderRequest orderRequest) throws Exception {

        // step 1 : 리워드 재고 감소
        itemFacadeService.decrease(orderRequest.getFundingItems());

        // step 2 : 결제 번호 생성
        String orderNumber = creatOrderNumber();


        // step 3 : 결제 생성


        // todo 결제 생성 코드
        return OrderResponseDto.builder().build();
    }
}
