package com.petlink.orders.controller;

import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderResponseDto;
import com.petlink.orders.service.MemberOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final MemberOrderService memberOrderService;

    @Deprecated
    @PostMapping("/guest")
    public ResponseEntity<String> createOrderByGuest(@RequestBody @Valid OrderRequest orderRequest) throws Exception {
        // Deprecated 된 기능입니다. 메시지 반환
        return ResponseEntity.ok("Deprecated 된 기능입니다.");
    }

    @PostMapping()
    public ResponseEntity<OrderResponseDto> createOrderByMember(@RequestBody @Valid OrderRequest orderRequest) throws Exception {
        return ResponseEntity.ok(memberOrderService.createOrder(orderRequest));
    }

    // todo 결제 정보를 업데이트 하는 기능
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody OrderRequest OrderRequest) {
        // 결제 정보 업데이트 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo 결제를 삭제하는 기능
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        // 결제 삭제 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

}
