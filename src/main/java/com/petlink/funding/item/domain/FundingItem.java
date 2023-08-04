package com.petlink.funding.item.domain;

import com.petlink.common.domain.base.BaseEntity;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.item.exception.ItemException;
import com.petlink.orders.domain.FundingItemOrder;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "stock", nullable = false)
    private Long stock;

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