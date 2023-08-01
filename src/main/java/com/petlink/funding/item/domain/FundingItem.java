package com.petlink.funding.item.domain;

import com.petlink.common.domain.base.BaseEntity;
import com.petlink.funding.domain.Funding;
import com.petlink.order.domain.FundingItemOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

}