package com.petlink.payment.controller;

import com.petlink.payment.dto.request.PaymentRequest;
import com.petlink.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    // todo 결제를 생성하는 기능.
    @PostMapping
    public ResponseEntity<Object> createPayment(@RequestBody PaymentRequest paymentRequest) {
        // 결제 생성 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo  특정 결제의 상세 정보를 조회하는 기능
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPayment(@PathVariable Long id) {
        // 결제 정보 조회 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo 결제 정보를 업데이트하는 기능
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        // 결제 정보 업데이트 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo 결제를 삭제하는 기능
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelPayment(@PathVariable Long id) {
        // 결제 삭제 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }

    // todo 모든 결제 정보를 조회하는 기능 이는 주로 GET 요청을 처리합니다.
    @GetMapping
    public ResponseEntity<Object> getAllPayments() {
        // 모든 결제 정보 조회 코드
        return ResponseEntity.ok("결제가 성공적으로 생성되었습니다.");
    }
}
