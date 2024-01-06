package com.petlink.funding.item.domain;

import com.petlink.common.domain.base.BaseEntity;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.item.exception.ItemException;
import com.petlink.orders.domain.FundingItemOrder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static com.petlink.funding.item.exception.ItemExceptionCode.ITEM_MAX_BUY_COUNT_EXCEEDED;
import static com.petlink.funding.item.exception.ItemExceptionCode.ITEM_NOT_ENOUGH;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "funding_item")
public class FundingItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Comment("해당 펀딩: 어느 펀딩의 아이템인지")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @Comment("아이템 제목")
    @Column(name = "title", nullable = false)
    private String title;

    @Comment("아이템 내용")
    @Column(name = "content", nullable = false)
    private String content;

    @Comment("아이템 가격")
    @Column(name = "stock", nullable = false)
    private Long stock;

    @Comment("아이템 최대 구매 수량")
    @Column(name = "max_buy_count", nullable = false)
    private Long maxBuyCount;

    @OneToMany(mappedBy = "fundingItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FundingItemOrder> fundingItemOrders = new ArrayList<>();

    public void decrease(Long quantity) throws ItemException {
        if (quantity > this.maxBuyCount) throw new ItemException(ITEM_MAX_BUY_COUNT_EXCEEDED);
        if ((this.stock - quantity) < 0) throw new ItemException(ITEM_NOT_ENOUGH);
        this.stock -= quantity;
    }
}