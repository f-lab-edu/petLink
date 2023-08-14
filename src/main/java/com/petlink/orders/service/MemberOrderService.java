package com.petlink.orders.service;

import com.petlink.common.domain.Address;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.item.service.ItemFacadeService;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.member.domain.Member;
import com.petlink.member.repository.MemberRepository;
import com.petlink.orders.domain.Orders;
import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderResponseDto;
import com.petlink.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.petlink.funding.exception.FundingExceptionCode.FUNDING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberOrderService {

    private final OrderRepository orderRepository;
    private final FundingRepository fundingRepository;
    private final MemberRepository memberRepository;
    private final ItemFacadeService itemFacadeService;
    private final OrderNumbersGenerator generator;  // 결제 번호 생성기


    //주문을 생성하는 기능. ( 비회원 구매 )
    public OrderResponseDto createOrderByMember(OrderRequest orderRequest, Long memberId) throws InterruptedException {

        // step 1 : 리워드 재고 감소
        itemFacadeService.decrease(orderRequest.getFundingItems());

        // step 2 , 3 : 결제 번호 채번  결제 생성
        Long fundingId = orderRequest.getFundingId();
        Funding funding = fundingRepository.findById(fundingId).orElseThrow(() -> new FundingException(FUNDING_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        Orders orders = orderRepository.saveAndFlush(Orders.builder()
                .funding(funding)
                .member(member)
                .paymentNumber("M-" + generator.generateOrderNumber())  // step 2 : 결제 번호 생성
                .payMethod(orderRequest.getPayMethod())
                .nameOpen(orderRequest.isNameOpen())
                .priceOpen(orderRequest.isAmountOpen())
                .recipient(orderRequest.getRecipient())
                .address(Address.of(orderRequest.getZipCode(), orderRequest.getAddress(), orderRequest.getDetailAddress()))
                .mobilePhone(orderRequest.getPhone())
                .subPhone(orderRequest.getSubPhone())
                .build());

        return OrderResponseDto.builder()
                .orderNumber(orders.getPaymentNumber())
                .orderId(orders.getId())
                .fundingId(fundingId)
                .recipientInfo(OrderResponseDto.RecipientInfo.of(orders.getRecipient(), orders.getAddress(), orders.getMobilePhone(), orders.getSubPhone()))
                .orderedRewards(orders.getFundingItemOrders().stream().map(fio -> fio.getFundingItem().getTitle()).toList())
                .isAmountOpen(orders.getPriceOpen())
                .isNameOpen(orders.getNameOpen())
                .build();
    }
}
