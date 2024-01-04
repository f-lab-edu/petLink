package com.petlink.orders.service;

import com.petlink.common.domain.Address;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.item.domain.FundingItem;
import com.petlink.funding.item.exception.ItemException;
import com.petlink.funding.item.exception.ItemExceptionCode;
import com.petlink.funding.item.repository.ItemRepository;
import com.petlink.funding.item.service.ItemFacadeService;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.member.domain.Member;
import com.petlink.member.exception.MemberException;
import com.petlink.member.exception.MemberExceptionCode;
import com.petlink.member.repository.MemberRepository;
import com.petlink.orders.domain.FundingItemOrder;
import com.petlink.orders.domain.Orders;
import com.petlink.orders.dto.request.FundingItemDto;
import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderDetailInfoResponse;
import com.petlink.orders.dto.response.OrderResponseDto;
import com.petlink.orders.repository.ItemOrdersRepository;
import com.petlink.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.petlink.funding.exception.FundingExceptionCode.FUNDING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final FundingRepository fundingRepository;
    private final MemberRepository memberRepository;
    private final ItemFacadeService itemFacadeService;
    private final ItemRepository itemRepository;
    private final ItemOrdersRepository itemOrdersRepository;
    private final OrderNumbersGenerator generator;  // 결제 번호 생성기

    @Override
    public OrderResponseDto createOrder(OrderRequest orderRequest) throws Exception {

        // step 1 : 리워드 재고 감소
        List<FundingItemDto> fundingItems = orderRequest.getFundingItems();
        decreaseStock(fundingItems, itemFacadeService);

        // step 2 , 3 : 결제 번호 채번  결제 생성
        Long memberId = orderRequest.getMemberId();
        Long fundingId = orderRequest.getFundingId();
        Funding funding = fundingRepository.findById(fundingId).orElseThrow(() -> new FundingException(FUNDING_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND_MEMBER_EXCEPTION));

        // 결제 번호 생성
        String paymentNumber = generator.generateOrderNumber();

        //step 4 : 결제 정보 저장
        Orders orders = saveOrder(orderRequest, member, funding, paymentNumber);
        saveOrderItems(orders, fundingItems);
        return buildCreateResponse(orders, fundingId);
    }

    private void saveOrderItems(Orders orders, List<FundingItemDto> fundingItems) {
        // FundingItem ID들을 한 번에 조회
        List<Long> itemIds = fundingItems.stream()
                .map(FundingItemDto::getFundingItemId)
                .collect(Collectors.toList());
        List<FundingItem> items = itemRepository.findAllById(itemIds);

        // FundingItemOrder 객체 생성
        List<FundingItemOrder> itemOrders = fundingItems.stream().map(fundingItemDto -> {
            FundingItem item = items.stream()
                    .filter(i -> i.getId().equals(fundingItemDto.getFundingItemId()))
                    .findFirst()
                    .orElseThrow(() -> new ItemException(ItemExceptionCode.ITEM_NOT_FOUND));
            return FundingItemOrder.builder()
                    .fundingItem(item)
                    .orders(orders)
                    .quantity(fundingItemDto.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        // 일괄 저장
        itemOrdersRepository.saveAll(itemOrders);

        // Orders 객체에 추가
        itemOrders.forEach(orders::addFundingItemOrder);
    }


    private Orders saveOrder(OrderRequest orderRequest, Member member, Funding funding, String paymentNumber) {
        return orderRepository.saveAndFlush(Orders.builder()
                .funding(funding)
                .member(member)
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

    @Override
    public OrderDetailInfoResponse getOrderDetailInfo(Long id) {
        Orders orders = orderRepository.findById(id).orElseThrow(() -> new FundingException(FUNDING_NOT_FOUND));
        List<String> itmeList = orders.getFundingItemOrders().stream().map(fundingItemOrder -> fundingItemOrder.getFundingItem().getTitle()).toList();
        return buildDetailResponse(orders, itmeList);
    }
}
