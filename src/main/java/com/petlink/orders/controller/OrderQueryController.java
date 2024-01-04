package com.petlink.orders.controller;

import com.petlink.orders.dto.response.OrderDetailInfoResponse;
import com.petlink.orders.service.MemberOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders/query")
public class OrderQueryController {

    private final MemberOrderService memberOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailInfoResponse> getOrderInfo(@PathVariable Long id) {
        // 결제 정보 조회 코드
        return ResponseEntity.ok(memberOrderService.getOrderDetailInfo(id));
    }

    // todo 모든 결제 정보를 조회 하는 기능 이는 주로 GET 요청을 처리.
    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        // 모든 결제 정보 조회 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }
}
