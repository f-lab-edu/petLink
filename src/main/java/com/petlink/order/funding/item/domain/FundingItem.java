package com.petlink.order.funding.item.domain;

import com.petlink.common.domain.base.BaseEntity;
import com.petlink.order.funding.domain.Funding;
import com.petlink.order.funding.item.exception.ItemException;
import com.petlink.order.orders.domain.FundingItemOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static com.petlink.order.funding.item.exception.ItemExceptionCode.ITEM_MAX_BUY_COUNT_EXCEEDED;
import static com.petlink.order.funding.item.exception.ItemExceptionCode.ITEM_NOT_ENOUGH;

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