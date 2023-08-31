package com.petlink.orders.service;

import com.petlink.common.domain.Address;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.item.service.ItemFacadeService;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.member.repository.MemberRepository;
import com.petlink.orders.domain.Orders;
import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderResponseDto;
import com.petlink.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.petlink.funding.exception.FundingExceptionCode.FUNDING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final FundingRepository fundingRepository;
    private final MemberRepository memberRepository;
    private final ItemFacadeService itemFacadeService;

    private String creatOrderNumber() {
        // todo 결제 번호 생성 코드 규칙성 필요
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    // todo 주문을 생성하는 기능. ( 비회원 구매 )
    public OrderResponseDto createOrderByGuest(OrderRequest orderRequest) throws Exception {

        // step 1 : 리워드 재고 감소
        itemFacadeService.decrease(orderRequest.getFundingItems());

        // step 2 , 3 : 결제 번호 채번  결제 생성
        Funding funding = fundingRepository.findById(orderRequest.getFundingId())
                .orElseThrow(() -> new FundingException(FUNDING_NOT_FOUND));

        Orders orders = Orders.builder()
                .funding(funding)
                .paymentNumber(creatOrderNumber())  // step 2 : 결제 번호 생성
                .payMethod(orderRequest.getPayMethod())
                .nameOpen(orderRequest.isNameOpen())
                .priceOpen(orderRequest.isAmountOpen())
                .recipient(orderRequest.getRecipient())
                .address(Address.of(orderRequest.getZipCode(), orderRequest.getAddress(), orderRequest.getDetailAddress()))
                .mobilePhone(orderRequest.getPhone())
                .subPhone(orderRequest.getSubPhone())
                .build();

        orderRepository.saveAndFlush(orders);

        return OrderResponseDto.builder().build();
    }
}
