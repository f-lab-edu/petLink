package com.petlink.orders.controller;

import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService OrderService;

    // todo 결제를 생성하는 기능. ( 회원 구매 )
    @PostMapping
    public ResponseEntity<Object> createOrderByNonMember(@RequestBody OrderRequest OrderRequest) {
        // 결제 생성 코드
        return ResponseEntity.ok(OrderRequest);
    }

    // todo 결제를 생성하는 기능. ( 회원 구매 )
    @PostMapping("/{memberId}")
    public ResponseEntity<Object> createOrderByMember(@RequestBody OrderRequest OrderRequest, @PathVariable Long memberId) {
        // 결제 생성 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo  특정 결제의 상세 정보를 조회하는 기능
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderInfo(@PathVariable Long id) {
        // 결제 정보 조회 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo 결제 정보를 업데이트하는 기능
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderRequest OrderRequest) {
        // 결제 정보 업데이트 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo 결제를 삭제하는 기능
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable Long id) {
        // 결제 삭제 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo 모든 결제 정보를 조회하는 기능 이는 주로 GET 요청을 처리합니다.
    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        // 모든 결제 정보 조회 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }
}
