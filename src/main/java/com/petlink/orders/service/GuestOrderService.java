package com.petlink.orders.service;

import com.petlink.common.domain.Address;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.item.service.ItemFacadeService;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.orders.domain.Orders;
import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderResponseDto;
import com.petlink.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.petlink.funding.exception.FundingExceptionCode.FUNDING_NOT_FOUND;


@Service
@Deprecated
@RequiredArgsConstructor
public class GuestOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final FundingRepository fundingRepository;
    private final ItemFacadeService itemFacadeService;
    private final OrderNumbersGenerator generator;  // 결제 번호 생성기

    @Override
    public OrderResponseDto createOrder(OrderRequest orderRequest) throws Exception {

        // step 1 : 리워드 재고 감소
        decreaseStock(orderRequest.getFundingItems(), itemFacadeService);

        // step 2 , 3 : 결제 번호 채번  결제 생성
        Long fundingId = orderRequest.getFundingId();
        Funding funding = fundingRepository.findById(fundingId).orElseThrow(() -> new FundingException(FUNDING_NOT_FOUND));

        String paymentNumber = generatePaymentNumber("G-", generator);

        // 주문 정보 저장
        Orders orders = saveOrder(orderRequest, funding, paymentNumber);

        // 응답 생성 (디폴트 메서드 사용)
        return buildOrderResponse(orders, fundingId);
    }

    private Orders saveOrder(OrderRequest orderRequest, Funding funding, String paymentNumber) {
        return orderRepository.saveAndFlush(Orders.builder()
                .funding(funding)
                .paymentNumber(paymentNumber)
                .payMethod(orderRequest.getPayMethod())
                .nameOpen(orderRequest.isNameOpen())
                .priceOpen(orderRequest.isAmountOpen())
                .recipient(orderRequest.getRecipient())
                .address(Address.of(orderRequest.getZipCode(), orderRequest.getAddress(), orderRequest.getDetailAddress()))
                .mobilePhone(orderRequest.getPhone())
                .subPhone(orderRequest.getSubPhone())
                .build());
    }
}
